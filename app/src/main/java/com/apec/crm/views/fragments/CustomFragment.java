package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.FilterCustomNameBean;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.FilterCustomActivity;
import com.apec.crm.views.activities.SearchCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;

import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 */
public class CustomFragment extends BaseFragment {

    CustomListFragment customListFragment;

    FilterCustomBean mFilterCustomBean;
    FilterCustomNameBean mFilterCustomNameBean;

    @Override
    protected void initUI(View view) {
        customListFragment = new CustomListFragment();
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

    /**
     * 搜索客户
     * @param view
     */
    @OnClick(R.id.tv_search)
    void onSearchClicked(View view) {
        Intent intent = new Intent(getActivity(), SearchCustomActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_CUSTOM);
    }

    /**
     * 筛选客户
     * @param view
     */
    @OnClick(R.id.tv_filter)
    void onFilterClicked(View view) {
        Intent intent = new Intent(getActivity(), FilterCustomActivity.class);
        intent.putExtra(FilterCustomActivity.ARG_FILTER_BEAN, mFilterCustomBean);
        intent.putExtra(FilterCustomActivity.ARG_FILTER_BEAN_NAME, mFilterCustomNameBean);
        startActivityForResult(intent, Constants.REQUEST_CODE_FILTER_CUSTOM);
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
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CUSTOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_FILTER_CUSTOM) {
            if (resultCode == Constants.RESULT_CODE_FILTER_CUSTOM) {

                mFilterCustomBean =
                        data.getParcelableExtra(FilterCustomActivity.ARG_RESULT);
                mFilterCustomNameBean =
                        data.getParcelableExtra(FilterCustomActivity.ARG_RESULT_NAME);

                customListFragment.updateForFilter(mFilterCustomBean);
            }
        } else if (requestCode == Constants.REQUEST_CODE_ADD_CUSTOM) {
            if (resultCode == Constants.RESULT_CODE_ADD_CUSTOM) {
                customListFragment.updateForFilter(null);
            }
        }

    }
}
