package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 16/9/12.
 * 搜索客户
 */
public class SearchCustomActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_custom, -1, MODE_NONE);
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
