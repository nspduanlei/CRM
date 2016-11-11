package com.apec.crm.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.CustomListPresenter;
import com.apec.crm.mvp.views.CustomListView;
import com.apec.crm.support.eventBus.RxBus;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.CustomActivity;
import com.apec.crm.views.activities.CustomDetailActivity;
import com.apec.crm.views.activities.MainActivity;
import com.apec.crm.views.fragments.core.BaseListFragment;
import com.apec.crm.views.widget.recyclerView.CommonRecyclerAdapter;
import com.apec.crm.views.widget.recyclerView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.apec.crm.views.fragments.CustomFragment.TYPE_PRIVATE;

/**
 * Created by duanlei on 16/9/28.
 */

public class CustomListFragment extends BaseListFragment implements CustomListView {

    @Inject
    CustomListPresenter mCustomListPresenter;

    public static final String ARG_TYPE = "arg_type";
    private int mType;

    public static CustomListFragment newInstance(int type) {
        CustomListFragment newFragment = new CustomListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected CommonRecyclerAdapter getAdapter() {
        CommonRecyclerAdapter adapter = new CommonRecyclerAdapter<Custom>(getActivity(),
                R.layout.item_custom_list,
                new ArrayList<>()) {
            @Override
            public void convert(MyViewHolder holder, Custom custom, int position) {

                holder.setTextRound(R.id.roundTextView, MyUtils.getColor(custom.getIcon()))
                        .setText(R.id.tv_custom_name, custom.getCustomerName())
                        .setText(R.id.tv_address, custom.getCustomerAddress());

                if (mType == CustomFragment.TYPE_PUBLIC) {
                    holder.setVisibility(R.id.tv_time, View.GONE)
                            .setVisibility(R.id.tv_pick, View.VISIBLE)
                            .setOnClickLister(R.id.tv_pick, v -> {
                                //拾取客户
                                mCustomListPresenter.pickCustom(custom.getId());
                            });
                } else {
                    holder.setVisibility(R.id.tv_time, View.VISIBLE)
                            .setVisibility(R.id.tv_pick, View.GONE)
                            .setText(R.id.tv_time, DateUtil.getDateFormatStr(Long.valueOf(custom.getTime()),
                                    getString(R.string.date_format_custom)));
                }

                MyUtils.setHeadText(holder.getView(R.id.tv_head), custom.getCustomerName());

                holder.setOnItemClickListener(v -> {
                    if (mType == CustomFragment.TYPE_PRIVATE) {
                        Intent intent = new Intent(getActivity(), CustomActivity.class);
                        intent.putExtra(CustomActivity.ARG_CUSTOM, custom);
                        getActivity().startActivity(intent);
                    } else if (mType == CustomFragment.TYPE_PUBLIC) {
                        Intent intentDetail = new Intent(getActivity(), CustomDetailActivity.class);
                        intentDetail.putExtra(CustomDetailActivity.ARG_CUSTOM_ID, custom.getId());
                        intentDetail.putExtra(CustomDetailActivity.ARG_TYPE, mType);
                        startActivity(intentDetail);
                    }
                });
            }
        };

        return adapter;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerCustomComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mCustomListPresenter.attachView(this);
        mCustomListPresenter.onCreate();

        mType = getArguments().getInt(ARG_TYPE, TYPE_PRIVATE);
        mCustomListPresenter.setType(mType);
    }

    @Override
    protected void loadFirstPage() {
        mCustomListPresenter.refresh();
    }

    @Override
    protected void loadOtherPage() {
        mCustomListPresenter.loadMore();
    }

    @Override
    public void onRefreshSuccess(ArrayList data) {
        if (data.size() == 0) {
            setEmptyText(getString(R.string.custom_list_empty));
            showEmpty();
        } else {
            hideEmpty();
        }

        setListCount(String.format("共%s个客户", mCustomListPresenter.getTotalNumber()));
        onRefreshComplete(data);
    }

    @Override
    public void onLoadMoreSuccess(ArrayList data) {
        onLoadMoreComplete(data);
    }

    @Override
    public void onNoMore() {
        T.showShort(getActivity(), "没有更多数据");
        onDataEnd();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError(String errorCode, String errorMsg) {
        onLoadError();
        MyUtils.tokenTimeOut(errorCode, getActivity());
    }

    /**
     * 根据过滤条件刷新页面
     *
     * @param filterCustomBean
     */
    public void updateForFilter(FilterCustomBean filterCustomBean) {
        if (filterCustomBean != null) {
            mCustomListPresenter.setFilter(filterCustomBean);
        }
        initiateRefresh();
    }

    @Override
    public void onPickSuccess() {
        T.showShort(getContext(), "拾取客户成功");
        initiateRefresh();
        RxBus.getDefault().post(MainActivity.ACTION_UPDATE_CUSTOMS);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustomListPresenter.onStop();
    }
}
