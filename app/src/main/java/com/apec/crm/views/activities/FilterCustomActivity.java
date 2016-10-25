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
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.utils.SelectCityUtil;
import com.apec.crm.views.activities.core.BaseActivity;

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

    @Inject
    SelectCityUtil mSelectCityUtil;

    FilterCustomBean mFilterCustomBean = new FilterCustomBean();

    public static final String ARG_RESULT = "arg_result";

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_filter_custom, R.string.filter_custom_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mSelectCityUtil.init(this);
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
            setResult(Constants.RESULT_CODE_FILTER_CUSTOM,
                    getIntent().putExtra(ARG_RESULT, mFilterCustomBean));
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

        isSelect = false;

        mTvArea.setText("");
        mTvCustomType.setText("");
        mTvCustomLevel.setText("");
        mTvCustomState.setText("");
        mTvCustomClass.setText("");
        mTvUser.setText("");
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
        mTvArea.setText(String.format("%s/%s/%s", address.getProvinceName(),
                address.getCityName(), address.getAreaName()));
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
                    mTvCustomType.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_LEVEL:
                    mFilterCustomBean.setCustomerLevel(selectContent.getId());
                    mTvCustomLevel.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_STATE:
                    mFilterCustomBean.setCustomerState(selectContent.getId());
                    mTvCustomState.setText(selectContent.getName());
                    isSelect = true;
                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_CLASS:
                    mFilterCustomBean.setClassId(selectContent.getId());
                    mTvCustomClass.setText(selectContent.getName());
                    isSelect = true;
                    break;
            }


        } else if (requestCode == Constants.REQUEST_CODE_SELECT_USER) { //选择所属人
        }
    }


}
