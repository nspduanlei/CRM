package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;

/**
 * Created by duanlei on 16/9/12.
 * 根据条件筛选客户
 */
public class FilterCustomActivity extends BaseActivity {
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_filter_custom, R.string.filter_custom_title);
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
