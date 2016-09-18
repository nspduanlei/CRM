package com.apec.crm.views.fragments;

import android.app.Fragment;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.fragments.core.BaseFragment;

/**
 * Created by duanlei on 16/9/12.
 */
public class ProfileFragment extends BaseFragment {
    @Override
    protected void initUI(View view) {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }
}
