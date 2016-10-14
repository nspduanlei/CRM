package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;


/**
 * Created by duanlei on 2016/10/13.
 * 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity {

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_modify_password, R.string.modify_password);
        setMenuText("保存", v -> {
            //TODO 保存数据
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
}
