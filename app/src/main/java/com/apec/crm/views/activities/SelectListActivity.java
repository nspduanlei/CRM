package com.apec.crm.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.SelectListPresenter;
import com.apec.crm.mvp.views.SelectListView;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/10/17.
 */
public class SelectListActivity extends BaseActivity implements SelectListView {


    public static final String LIST_TYPE = "listType";
    public static final String CUSTOM_ID = "customId";
    public static final String AREA_ID = "areaId";

    public static final int CUSTOM_TYPE = 0; //客户类型
    public static final int CUSTOM_CLASS = 1; //销售品类
    public static final int CUSTOM_LEVEL = 2; //客户分类
    public static final int CUSTOM_STATE = 3; //客户状态
    public static final int CUSTOM_SOURCE = 4; //客户来源
    public static final int CUSTOM_OPEN_SEA = 5; //片区，先选择行政区域
    public static final int CUSTOM_CONTACT = 6; //联系人，先选择客户

    public static final String RESULT = "result"; //返回参数名

    private String mCustomId; //客户id
    private String mAreaId; //区id

    private int mListType;

    @BindView(R.id.lv_content)
    ListView mLVContent;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Inject
    SelectListPresenter mSelectListPresenter;

    private CommonAdapter mAdapter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_select_list);

        mListType = getIntent().getIntExtra(LIST_TYPE, 0);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mAdapter = new CommonAdapter<SelectContent>(this, new ArrayList<>(),
                R.layout.select_list_item, mLVContent) {
            @Override
            public void convert(MyViewHolder holder, SelectContent selectContent) {
                holder.setText(R.id.tv_content, selectContent.getName());
            }
        };
        mLVContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<SelectContent>() {
            @Override
            public void onItemClick(SelectContent data) {

                switch (mListType) {
                    case CUSTOM_TYPE:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_TYPE,
                                getIntent().putExtra(RESULT, data));
                        break;

                    case CUSTOM_CLASS:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_CLASS,
                                getIntent().putExtra(RESULT, data));
                        break;
                    case CUSTOM_LEVEL:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_LEVEL,
                                getIntent().putExtra(RESULT, data));
                        break;

                    case CUSTOM_STATE:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_STATE,
                                getIntent().putExtra(RESULT, data));
                        break;

                    case CUSTOM_SOURCE:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_SOURCE,
                                getIntent().putExtra(RESULT, data));

                        break;

                    case CUSTOM_OPEN_SEA:
                        setResult(Constants.RESULT_CODE_SELECT_CUSTOM_OPEN_SEA,
                                getIntent().putExtra(RESULT, data));
                        break;

                    case CUSTOM_CONTACT:
                        setResult(Constants.RESULT_CODE_SELECT_CONTACT,
                                getIntent().putExtra(RESULT, data));
                        break;
                }
                SelectListActivity.this.finish();
            }
        });

        switch (mListType) {
            case CUSTOM_TYPE:
                setUpTitle("选择客户类型");
                mSelectListPresenter.getCustomTypeList(Constants.CUSTOMER_TYPE);
                break;
            case CUSTOM_CLASS:
                setUpTitle("选择销售品类");
                mSelectListPresenter.getCustomTypeList(Constants.CUSTOMER_CLASS);
                break;
            case CUSTOM_LEVEL:
                setUpTitle("选择客户分类");
                mSelectListPresenter.getCustomTypeList(Constants.CUSTOMER_LEVEL);
                break;
            case CUSTOM_STATE:
                setUpTitle("选择客户状态");
                mSelectListPresenter.getCustomTypeList(Constants.CUSTOMER_STATE);
                break;
            case CUSTOM_SOURCE:
                setUpTitle("选择客户来源");
                mSelectListPresenter.getCustomTypeList(Constants.CUSTOMER_SOURCE);
                break;

            case CUSTOM_OPEN_SEA:
                setUpTitle("选择片区");

                mAreaId = getIntent().getStringExtra(AREA_ID);
                if (mAreaId != null) {
                    mSelectListPresenter.getOpenSeas(mAreaId);
                }

                break;

            case CUSTOM_CONTACT:
                setUpTitle("选择联系人");

                mCustomId = getIntent().getStringExtra(CUSTOM_ID);
                if (mCustomId != null) {
                    mSelectListPresenter.getContacts(mCustomId);
                }
                break;
        }
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
        mSelectListPresenter.attachView(this);
        mSelectListPresenter.onCreate();
    }

    @Override
    public void onGetListSuccess(ArrayList<SelectContent> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
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
}
