package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.views.activities.core.BaseActivity;
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

    public static final int FROM_CUSTOM = 0;  //客户页面搜索
    public static final int SEARCH_CUSTOM = 1; //选择客户

    public static final String CUSTOM = "custom";
    public static final String FROM_TYPE = "fromType";

    private int mType;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_custom, -1, MODE_NONE);

        mType = getIntent().getIntExtra(FROM_TYPE, 0);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mEtContent.addTextChangedListener(new EditChangeListener());

        mCommonAdapter = new CommonAdapter<Custom>(this, new ArrayList<>(),
                R.layout.item_custom_list, mLvSearch) {
            @Override
            public void convert(MyViewHolder holder, Custom custom) {
                holder.setTextRound(R.id.roundTextView, MyUtils.getColor(custom.getIcon()))
                        .setText(R.id.tv_custom_name, custom.getCustomerName())
                        .setText(R.id.tv_address, custom.getCustomerAddress())
                        .setText(R.id.tv_time, DateUtil.getDateFormatStr(Long.valueOf(custom.getTime()),
                                getString(R.string.date_format_custom)));

                MyUtils.setHeadText(holder.getView(R.id.tv_head), custom.getCustomerName());

            }
        };

        mLvSearch.setAdapter(mCommonAdapter);

        mCommonAdapter.setOnItemClickListener(data -> {

            if (mType == SEARCH_CUSTOM) {
                setResult(Constants.RESULT_CODE_SELECT_CUSTOM,
                        getIntent().putExtra(CUSTOM, data));
                SearchCustomActivity.this.finish();
            } else {
                Intent intent = new Intent(SearchCustomActivity.this, CustomActivity.class);
                intent.putExtra(CustomActivity.ARG_CUSTOM, data);
                startActivity(intent);
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
                mSearchCustomPresenter.searchCustom(s.toString());
            }
        }
    }

    @OnClick(R.id.iv_clear_text)
    void onClearTextClicked(View view) {
        mEtContent.setText("");
    }
}
