package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Address;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.AddCustomPresenter;
import com.apec.crm.mvp.views.AddCustomView;
import com.apec.crm.utils.KeyBoardUtils;
import com.apec.crm.utils.SelectCityUtil;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.NoScrollListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 * 添加客户
 */
public class AddCustomActivity extends BaseActivity implements SelectCityUtil.SelectArea,
        AddCustomView {

    @Inject
    SelectCityUtil mSelectCityUtil;

    @BindView(R.id.et_custom_name)
    EditText mEtCustomName;
    @BindView(R.id.tv_custom_type)
    TextView mTvCustomType;
    @BindView(R.id.tv_address_pca)
    TextView mTvAddressPca;
    @BindView(R.id.et_road)
    EditText mEtRoad;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.lv_contact_list)
    NoScrollListView mLvContactList;

    private Address mSelectAddress;

    @Inject
    AddCustomPresenter mAddCustomPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_custom, R.string.add_custom_title);
        setMenuText("保存", v -> {

        });
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
        mAddCustomPresenter.attachView(this);
    }

    /**
     * 添加联系人
     *
     * @param view
     */
    @OnClick(R.id.tv_add_contact)
    void onAddContactClicked(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CONTACT);
    }

    @OnClick(R.id.tv_location)
    void onSelectLocationClicked(View view) {
        Intent intent = new Intent(this, MapLocationActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MARK_MAP);
    }

    @OnClick(R.id.fl_more_data)
    void onMoreDataClicked(View view) {
        Intent intent = new Intent(this, CustomMoreDataActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
    }

    /**
     * 选择地区
     *
     * @param view
     */
    @OnClick(R.id.tv_address)
    void onSelectAreaClicked(View view) {
        mSelectCityUtil.showDialog();

        //关闭键盘
        KeyBoardUtils.closeKeybord(mEtCustomName, this);
        KeyBoardUtils.closeKeybord(mEtRoad, this);
    }

    /**
     * 选择客户类型
     */
    @OnClick(R.id.fl_custom_type)
    void onCustomTypeClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra("listType", SelectListActivity.CUSTOM_TYPE);
        startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
    }

    @Override
    public void selectCityFinish(Address address) {
        mSelectAddress = address;

        mTvAddressPca.setText(String.format("%s/%s/%s", address.getProvinceName(),
                address.getCityName(), address.getAreaName()));
    }

    @Override
    public void onAddCustomSuccess() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }
}
