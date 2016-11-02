package com.apec.crm.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.ErrorCode;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.CustomListPresenter;
import com.apec.crm.mvp.views.CustomListView;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.CustomActivity;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.fragments.core.BaseListFragment;
import com.apec.crm.views.widget.recyclerView.CommonRecyclerAdapter;
import com.apec.crm.views.widget.recyclerView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duanlei on 16/9/28.
 */

public class CustomListFragment extends BaseListFragment implements CustomListView {

    @Inject
    CustomListPresenter mCustomListPresenter;

    public static final String ACTION_UPDATE = "更新列表";

    //定义广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_UPDATE:
                    initiateRefresh();
                    break;
            }
        }
    };

    //注册广播
    public void registerBroadcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_UPDATE);
        // 注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
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
                        .setText(R.id.tv_address, custom.getCustomerAddress())
                        .setText(R.id.tv_time, DateUtil.getDateFormatStr(Long.valueOf(custom.getTime()),
                                getString(R.string.date_format_custom)));

                MyUtils.setHeadText(holder.getView(R.id.tv_head), custom.getCustomerName());

                holder.setOnItemClickListener(v -> {
                    Intent intent = new Intent(getActivity(), CustomActivity.class);
                    intent.putExtra(CustomActivity.ARG_CUSTOM, custom);
                    getActivity().startActivity(intent);
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
        registerBroadcastReceiver();
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
        if (errorCode.equals(ErrorCode.SESSION_OUT)) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    /**
     * 根据过滤条件刷新页面
     * @param filterCustomBean
     */
    public void updateForFilter(FilterCustomBean filterCustomBean) {
        if (filterCustomBean != null) {
            mCustomListPresenter.setFilter(filterCustomBean);
        }
        initiateRefresh();
    }
}
