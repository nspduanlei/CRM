package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 2016/10/24.
 */

public class CustomEditActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_edit);
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
}
