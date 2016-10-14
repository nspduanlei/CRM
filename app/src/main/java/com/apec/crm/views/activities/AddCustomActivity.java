package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 * 添加客户
 */
public class AddCustomActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_custom, R.string.add_custom_title);
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
     * 添加联系人
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
     * @param view
     */
    @OnClick(R.id.tv_address)
    void onSelectAreaClicked(View view) {

    }

}
