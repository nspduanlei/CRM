package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.SearchCustomPresenter;
import com.apec.crm.mvp.views.SearchCustomView;
import com.apec.crm.support.eventBus.RxBus;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.CustomFragment;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 * 搜索客户
 */
public class SearchCustomActivity extends BaseActivity implements SearchCustomView {

    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.iv_clear_text)
    ImageView mIvClearText;

    @BindView(R.id.lv_search)
    ListView mLvSearch;

    CommonAdapter<Custom> mCommonAdapter;

    @Inject
    SearchCustomPresenter mSearchCustomPresenter;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @BindView(R.id.fl_empty)
    FrameLayout mEmpty;

    public static final String ARG_CUSTOM = "arg_custom";

    public static final String FROM_TYPE = "fromType";
    public static final int FROM_CUSTOM = 0;  //客户页面搜索
    public static final int SEARCH_CUSTOM = 1; //选择客户
    private int mType;

    public static final String ARG_TYPE = "arg_type";
    private int mListType;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_custom, -1, MODE_NONE);
        mType = getIntent().getIntExtra(FROM_TYPE, 0);
        mListType = getIntent().getIntExtra(ARG_TYPE, CustomFragment.TYPE_PRIVATE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mEtContent.addTextChangedListener(new EditChangeListener());

        mEtContent.setOnEditorActionListener((v, actionId, event) -> {
            String text = mEtContent.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !StringUtils.isNullOrEmpty(text)) {
                mSearchCustomPresenter.searchCustom(text);
            }
            return false;
        });

        mCommonAdapter = new CommonAdapter<Custom>(this, new ArrayList<>(),
                R.layout.item_custom_list, mLvSearch) {
            @Override
            public void convert(MyViewHolder holder, Custom custom) {

                if (mListType == CustomFragment.TYPE_PUBLIC) {
                    holder.setVisibility(R.id.tv_time, View.GONE)
                            .setVisibility(R.id.tv_pick, View.VISIBLE)
                            .setOnClickLister(R.id.tv_pick, v -> {
                                //拾取客户
                                mSearchCustomPresenter.pickCustom(custom.getId());
                            });
                } else {
                    holder.setVisibility(R.id.tv_time, View.VISIBLE)
                            .setVisibility(R.id.tv_pick, View.GONE)
                            .setText(R.id.tv_time, DateUtil.getDateFormatStr(Long.valueOf(custom.getTime()),
                                    getString(R.string.date_format_custom)));
                }

                MyUtils.setHeadText(holder.getView(R.id.tv_head), custom.getCustomerName());
            }
        };

        mLvSearch.setAdapter(mCommonAdapter);

        mCommonAdapter.setOnItemClickListener(data -> {
            if (mType == SEARCH_CUSTOM) {
                setResult(Constants.RESULT_CODE_SELECT_CUSTOM,
                        getIntent().putExtra(ARG_CUSTOM, data));
                SearchCustomActivity.this.finish();
            } else {
                if (mListType == CustomFragment.TYPE_PRIVATE) {
                    Intent intent = new Intent(SearchCustomActivity.this, CustomActivity.class);
                    intent.putExtra(CustomActivity.ARG_CUSTOM, data);
                    startActivity(intent);
                } else if (mListType == CustomFragment.TYPE_PUBLIC) {
                    Intent intentDetail = new Intent(SearchCustomActivity.this, CustomDetailActivity.class);
                    intentDetail.putExtra(CustomDetailActivity.ARG_CUSTOM_ID, data.getId());
                    intentDetail.putExtra(CustomDetailActivity.ARG_TYPE, mType);
                    startActivity(intentDetail);
                }
            }
        });
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerCustomComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mSearchCustomPresenter.attachView(this);
        mSearchCustomPresenter.onCreate();
        mSearchCustomPresenter.setType(mListType);
    }

    @OnClick(R.id.tv_exit_search)
    void onExitSearchClicked(View view) {
        this.finish();
    }

    @Override
    public void onSearchSuccess(ArrayList<Custom> customs) {
        if (customs.size() == 0) {
            mCommonAdapter.clearData();
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.GONE);

            mCommonAdapter.clear();
            mCommonAdapter.addAll(customs);
        }
    }

    @Override
    public void onPickSuccess() {
        T.showShort(this, "拾取客户成功");

        setResult(Constants.RESULT_CODE_PICK_CUSTOM);

        String text = mEtContent.getText().toString();
        if (!StringUtils.isNullOrEmpty(text)) {
            mSearchCustomPresenter.searchCustom(text);
        }

        RxBus.getDefault().post(MainActivity.ACTION_UPDATE_CUSTOMS);
    }

    @Override
    public void showLoadingView() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mIvClearText.setVisibility(View.GONE);
            } else {
                mIvClearText.setVisibility(View.VISIBLE);
                //搜索客户
                //mSearchCustomPresenter.searchCustom(s.toString());
            }
        }
    }

    @OnClick(R.id.iv_clear_text)
    void onClearTextClicked(View view) {
        mEtContent.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearchCustomPresenter.onStop();
    }
}
