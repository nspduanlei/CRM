package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.FilterCustomActivity;
import com.apec.crm.views.activities.SearchCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;

import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 */
public class CustomFragment extends BaseFragment {

    @Override
    protected void initUI(View view) {

        CustomListFragment customListFragment = new CustomListFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, customListFragment, "customList")
                .commit();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.tv_search)
    void onSearchClicked(View view) {
        Intent intent = new Intent(getActivity(), SearchCustomActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_filter)
    void onFilterClicked(View view) {
        Intent intent = new Intent(getActivity(), FilterCustomActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_flash)
    void onFlashClicked(View view) {
        FlashDialogFragment flashDialogFragment = new FlashDialogFragment();
        flashDialogFragment.show(getActivity().getFragmentManager(), "FlashDialogFragment");
    }

    /**
     * 添加客户
     */
    @OnClick(R.id.tv_add_custom)
    void onAddClicked() {
        Intent intent = new Intent(getActivity(), AddCustomActivity.class);
        startActivity(intent);
    }
}
