package com.apec.crm.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.utils.MyUtils;
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

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_more_data, R.string.custom_more_data);
        setMenuText("保存", v -> {

        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

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
            mTvBussTime.setText(menuEntities.get(position).getName());

            dialog.dismiss();
        });
    }
}
