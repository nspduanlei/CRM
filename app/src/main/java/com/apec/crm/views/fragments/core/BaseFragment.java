package com.apec.crm.views.fragments.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.apec.crm.app.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/10.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        initDependencyInjector(myApplication);
        initPresenter();
        initUI(view);
        return view;
    }

    protected abstract void initUI(View view);
    protected abstract int getFragmentLayout();
    protected abstract void initDependencyInjector(MyApplication myApplication);
    protected abstract void initPresenter();


}
