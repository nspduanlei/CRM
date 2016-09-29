package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.apec.crm.app.MyApplication;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 16/9/27.
 */

public class LaunchActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(-1, -1, MODE_NONE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        //隐藏状态栏 和 导航栏
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);

        new Handler().postDelayed(() -> {

            if (StringUtils.isNullOrEmpty((String) SPUtils.get(this, SPUtils.TOKEN, ""))) {
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
            }


            this.finish();
        }, 2000);

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
