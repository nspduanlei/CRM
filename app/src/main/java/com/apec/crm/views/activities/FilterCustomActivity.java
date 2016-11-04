package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Address;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.FilterCustomNameBean;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.utils.SelectCityUtil;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.CustomFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 * 根据条件筛选客户
 */
public class FilterCustomActivity extends BaseActivity implements SelectCityUtil.SelectArea {
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.tv_custom_type)
    TextView mTvCustomType;
    @BindView(R.id.tv_custom_level)
    TextView mTvCustomLevel;
    @BindView(R.id.tv_custom_state)
    TextView mTvCustomState;
    @BindView(R.id.tv_custom_class)
    TextView mTvCustomClass;
    @BindView(R.id.tv_user)
    TextView mTvUser;

    @BindView(R.id.fl_user)
    FrameLayout mFlUser;
    @BindView(R.id.user_line)
    View mUserLine;

    boolean isSelect = false;
    boolean hasData = false;

    @Inject
    SelectCityUtil mSelectCityUtil;

    FilterCustomBean mFilterCustomBean;
    FilterCustomNameBean mFilterCustomNameBean;

    public static final String ARG_RESULT = "arg_result";
    public static final String ARG_RESULT_NAME = "arg_result_name";
    public static final String ARG_FILTER_BEAN = "arg_filter_bean";
    public static final String ARG_FILTER_BEAN_NAME = "arg_filter_bean_name";

    //公海或私海
    public static final String ARG_TYPE = "arg_type";
    private int mType;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_filter_custom, R.string.filter_custom_title);
        mType = getIntent().getIntExtra(ARG_TYPE, CustomFragment.TYPE_PRIVATE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

        //如果是公海列表，没有所属人选择
        if (mType == CustomFragment.TYPE_PUBLIC) {
            mFlUser.setVisibility(View.GONE);
            mUserLine.setVisibility(View.GONE);
        }

        mSelectCityUtil.init(this);
        mFilterCustomNameBean = getIntent().getParcelableExtra(ARG_FILTER_BEAN_NAME);
        mFilterCustomBean = getIntent().getParcelableExtra(ARG_FILTER_BEAN);

        if (mFilterCustomNameBean != null) {
            hasData = true;

            mTvArea.setText(mFilterCustomNameBean.getAreaName());
            mTvCustomType.setText(mFilterCustomNameBean.getCustomerTypeName());
            mTvCustomLevel.setText(mFilterCustomNameBean.getCustomerLevelName());
            mTvCustomState.setText(mFilterCustomNameBean.getCustomerStateName());
            mTvCustomClass.setText(mFilterCustomNameBean.getClassName());
            mTvUser.setText(mFilterCustomNameBean.getUserName());
        } else {
            mFilterCustomNameBean = new FilterCustomNameBean();
        }

        if (mFilterCustomBean == null) {
            mFilterCustomBean = new FilterCustomBean();
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

    }

    /**
     * 确定
     *
     * @param view
     */
    @OnClick(R.id.btn_sure)
    void onSureClicked(View view) {
        if (isSelect) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_RESULT, mFilterCustomBean);
            bundle.putParcelable(ARG_RESULT_NAME, mFilterCustomNameBean);
            setResult(Constants.RESULT_CODE_FILTER_CUSTOM,
                    getIntent().putExtras(bundle));
        } else {
            setResult(RESULT_CANCELED);
        }
        this.finish();
    }

    /**
     * 重置
     *
     * @param view
     */
    @OnClick(R.id.btn_reset)
    void onReSetClicked(View view) {

        if (hasData || isSelect) {
            mTvArea.setText("");
            mTvCustomType.setText("");
            mTvCustomLevel.setText("");
            mTvCustomState.setText("");
            mTvCustomClass.setText("");
            mTvUser.setText("");

            mFilterCustomBean = new FilterCustomBean();
            mFilterCustomNameBean = new FilterCustomNameBean();

            isSelect = true;
        }
    }

    @OnClick(R.id.fl_area)
    void onAreaClicked(View view) {
        mSelectCityUtil.showDialog();
    }

    @OnClick(R.id.fl_type)
    void onTypeClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_TYPE);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_level)
    void onLevelClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_LEVEL);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_state)
    void onStateClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_STATE);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_class)
    void onClassClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_CLASS);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_user)
    void onUserClicked(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_USER);
    }

    @Override
    public void selectCityFinish(Address address) {
        isSelect = true;
        mFilterCustomBean.setAreaId(address.getAreaId());

        String areaString = String.format("%s/%s/%s", address.getProvinceName(),
                address.getCityName(), address.getAreaName());

        mFilterCustomNameBean.setAreaName(areaString);

        mTvArea.setText(areaString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_SELECT_ATTR) {

            SelectContent selectContent = null;

            if (resultCode != RESULT_CANCELED) {
                selectContent = data.getParcelableExtra(SelectListActivity.RESULT);
            }

            switch (resultCode) {
                case Constants.RESULT_CODE_SELECT_CUSTOM_TYPE:

                    mFilterCustomBean.setCustomerType(selectContent.getId());
                    mFilterCustomNameBean.setCustomerTypeName(selectContent.getName());

                    mTvCustomType.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_LEVEL:

                    mFilterCustomBean.setCustomerLevel(selectContent.getId());
                    mFilterCustomNameBean.setCustomerLevelName(selectContent.getName());

                    mTvCustomLevel.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_STATE:

                    mFilterCustomBean.setCustomerState(selectContent.getId());
                    mFilterCustomNameBean.setCustomerStateName(selectContent.getName());

                    mTvCustomState.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_CLASS:

                    mFilterCustomBean.setClassId(selectContent.getId());
                    mFilterCustomNameBean.setClassName(selectContent.getName());

                    mTvCustomClass.setText(selectContent.getName());
                    isSelect = true;
                    break;
            }


        } else if (requestCode == Constants.REQUEST_CODE_SELECT_USER) { //选择所属人
        }
    }


}
