package com.apec.crm.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.FilterCustomNameBean;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.FilterCustomActivity;
import com.apec.crm.views.activities.SearchCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;
import com.melnykov.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 */
public class CustomFragment extends BaseFragment {

    CustomListFragment mCustomListFragment;

    FilterCustomBean mFilterCustomBean;
    FilterCustomNameBean mFilterCustomNameBean;

    public static final String ARG_TYPE = "arg_type";
    public static final int TYPE_PRIVATE = 1; //私海客户
    public static final int TYPE_PUBLIC = 2; //公海客户
    private int mType;

    @BindView(R.id.rl_custom_head)
    RelativeLayout mRlCustomHead;
    
    @BindView(R.id.btn_flash)
    FloatingActionButton mBtnFlash;

    @Override
    protected void initUI(View view) {
        mType = getArguments().getInt(ARG_TYPE, TYPE_PRIVATE);

        if (mType == TYPE_PUBLIC) {
            mRlCustomHead.setVisibility(View.GONE);
            mBtnFlash.setVisibility(View.GONE);
        }

        mCustomListFragment = CustomListFragment.newInstance(mType);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mCustomListFragment, "customList")
                .commit();
    }

    public static CustomFragment newInstance(int type) {
        CustomFragment newFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        newFragment.setArguments(bundle);
        return newFragment;
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
     *
     * @param view
     */
    @OnClick(R.id.tv_search)
    void onSearchClicked(View view) {
        Intent intent = new Intent(getActivity(), SearchCustomActivity.class);
        intent.putExtra(SearchCustomActivity.FROM_TYPE, SearchCustomActivity.FROM_CUSTOM);
        intent.putExtra(SearchCustomActivity.ARG_TYPE, mType);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_CUSTOM);
    }

    /**
     * 筛选客户
     *
     * @param view
     */
    @OnClick(R.id.tv_filter)
    void onFilterClicked(View view) {
        Intent intent = new Intent(getActivity(), FilterCustomActivity.class);
        intent.putExtra(FilterCustomActivity.ARG_FILTER_BEAN, mFilterCustomBean);
        intent.putExtra(FilterCustomActivity.ARG_FILTER_BEAN_NAME, mFilterCustomNameBean);
        intent.putExtra(FilterCustomActivity.ARG_TYPE, mType);
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

        if (requestCode == Constants.REQUEST_CODE_FILTER_CUSTOM) {  //选择筛选条件
            if (resultCode == Constants.RESULT_CODE_FILTER_CUSTOM) {

                mFilterCustomBean =
                        data.getParcelableExtra(FilterCustomActivity.ARG_RESULT);
                mFilterCustomNameBean =
                        data.getParcelableExtra(FilterCustomActivity.ARG_RESULT_NAME);

                updateCustomList();
            }
        } else if (requestCode == Constants.REQUEST_CODE_ADD_CUSTOM) { //添加客户
            if (resultCode == Constants.RESULT_CODE_ADD_CUSTOM) {
                updateCustomList();
            }
        } else if (requestCode == Constants.REQUEST_CODE_SELECT_CUSTOM) { //搜索成功
            if (resultCode == Constants.RESULT_CODE_PICK_CUSTOM) { //拾取客户成功
                updateCustomList();
            }
        }
    }

    public void updateCustomList() {
        mCustomListFragment.updateForFilter(mFilterCustomBean);
    }
}
