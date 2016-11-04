package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.CustomFragment;

/**
 * Created by duanlei on 2016/10/27.
 * 公海池
 */
public class OpenSeaActivity extends BaseActivity {

    CustomFragment mCustomFragment;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_open_sea, R.string.title_open_sea);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCustomFragment = CustomFragment.newInstance(CustomFragment.TYPE_PUBLIC);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mCustomFragment, "custom")
                .commit();
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
