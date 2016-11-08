package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.views.activities.core.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/14.
 */

public class CustomMoreDataActivity extends BaseActivity {

    @BindView(R.id.tv_custom_level)
    TextView mTvCustomLevel;
    @BindView(R.id.tv_custom_state)
    TextView mTvCustomState;

    @BindView(R.id.tv_custom_source)
    TextView mTvCustomSource;

    @BindView(R.id.tv_area)
    TextView mTvArea;

    @BindView(R.id.tv_car)
    TextView mTvCar;
    @BindView(R.id.tv_buss_time)
    TextView mTvBussTime;
    @BindView(R.id.tv_class)
    TextView mTvClass;

    @BindView(R.id.fl_custom_area)
    FrameLayout mFLCustomArea;
    @BindView(R.id.area_line)
    View mAreaLine;

    public static final String ARG_AREA = "arg_area";
    public static final String ARG_RESULT = "arg_result";
    public static final String ARG_CUSTOM = "arg_custom";
    private String mAreaId;

    private CustomDetail mCustomDetail;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_more_data, R.string.custom_more_data);
        setMenuText("保存", v -> {
            setResult(Constants.RESULT_CODE_MORE_DATA, getIntent().putExtra(ARG_RESULT,
                    mCustomDetail));
            this.finish();
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mAreaId = getIntent().getStringExtra(ARG_AREA);
        if (mAreaId == null) {
            mFLCustomArea.setVisibility(View.GONE);
            mAreaLine.setVisibility(View.GONE);
        }

        mCustomDetail = getIntent().getParcelableExtra(ARG_CUSTOM);

        if (mCustomDetail == null) {
            this.finish();
        }

        if (!StringUtils.isNullOrEmpty(mCustomDetail.getCustomerLevelName())) {
            mTvCustomLevel.setText(mCustomDetail.getCustomerLevelName());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getCustomerStateName())) {
            mTvCustomState.setText(mCustomDetail.getCustomerStateName());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getSource())) {
            mTvCustomSource.setText(mCustomDetail.getSourceName());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getAreaName())) {
            mTvArea.setText(mCustomDetail.getAreaName());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getIsSpeedInto())) {
            mTvCar.setText(mCustomDetail.getIsSpeedInto());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getBusinessHours())) {
            mTvBussTime.setText(mCustomDetail.getBusinessHours());
        }
        if (!StringUtils.isNullOrEmpty(mCustomDetail.getClassName())) {
            mTvClass.setText(mCustomDetail.getClassName());
        }
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    /**
     * 选择车辆是否能进
     *
     * @param view
     */
    @OnClick(R.id.fl_car)
    void onCarClicked(View view) {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(0, "是"));
        menuEntities.add(new MenuEntity(1, "否"));

        MyUtils.showListDialog(this, menuEntities, (dialog, item, view1, position) -> {
            mCustomDetail.setIsSpeedInto(menuEntities.get(position).getName());
            mTvCar.setText(menuEntities.get(position).getName());

            dialog.dismiss();
        });
    }

    /**
     * 选择营业时间
     *
     * @param view
     */
    @OnClick(R.id.fl_business)
    void onBussTimeClicked(View view) {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(0, "全天"));
        menuEntities.add(new MenuEntity(1, "早上"));
        menuEntities.add(new MenuEntity(2, "中午"));
        menuEntities.add(new MenuEntity(3, "晚上"));

        MyUtils.showListDialog(this, menuEntities, (dialog, item, view1, position) -> {

            mCustomDetail.setBusinessHours(menuEntities.get(position).getName());
            mTvBussTime.setText(menuEntities.get(position).getName());

            dialog.dismiss();
        });
    }


    @OnClick(R.id.fl_custom_level)
    void onLevelClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_LEVEL);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_custom_state)
    void onStateClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_STATE);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }


    @OnClick(R.id.fl_custom_source)
    void onSourceClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_SOURCE);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }


    @OnClick(R.id.fl_custom_area)
    void onAreaClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.AREA_ID, mAreaId);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_OPEN_SEA);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @OnClick(R.id.fl_custom_class)
    void onClassClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_CLASS);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
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
                case Constants.RESULT_CODE_SELECT_CUSTOM_LEVEL:

                    mCustomDetail.setCustomerLevel(selectContent.getId());
                    mCustomDetail.setCustomerLevelName(selectContent.getName());
                    mTvCustomLevel.setText(selectContent.getName());

                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_STATE:

                    mCustomDetail.setCustomerState(selectContent.getId());
                    mCustomDetail.setCustomerStateName(selectContent.getName());
                    mTvCustomState.setText(selectContent.getName());

                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_SOURCE:

                    mCustomDetail.setSource(selectContent.getId());
                    mCustomDetail.setSourceName(selectContent.getName());
                    mTvCustomSource.setText(selectContent.getName());

                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_OPEN_SEA:

                    mCustomDetail.setAreaNo(selectContent.getId());
                    mCustomDetail.setAreaName(selectContent.getName());
                    mTvArea.setText(selectContent.getName());

                    break;

                case Constants.RESULT_CODE_SELECT_CUSTOM_CLASS:

                    mCustomDetail.setClassId(selectContent.getId());
                    mCustomDetail.setClassName(selectContent.getName());
                    mTvClass.setText(selectContent.getName());

                    break;
            }

        }
    }

}
