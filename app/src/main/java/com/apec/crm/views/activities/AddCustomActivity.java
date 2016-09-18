package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by duanlei on 16/9/12.
 * 添加客户
 */
public class AddCustomActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_custom, R.string.add_custom_title);
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

    //添加联系人
    @OnClick(R.id.fl_add_contact)
    void onAddContactClicked(View view) {

    }

    @OnClick(R.id.tv_location)
    void onSelectLocationClicked(View view) {
        Intent intent = new Intent(this, MapMarkActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MARK_MAP);
    }
}
