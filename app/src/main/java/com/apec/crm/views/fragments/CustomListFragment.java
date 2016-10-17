package com.apec.crm.views.fragments;

import android.content.Intent;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.ErrorCode;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.CustomListPresenter;
import com.apec.crm.mvp.views.CustomListView;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.T;
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

    @Override
    protected CommonRecyclerAdapter getAdapter() {
        CommonRecyclerAdapter adapter = new CommonRecyclerAdapter<Custom>(getActivity(),
                R.layout.item_custom_list,
                new ArrayList<>()) {
            @Override
            public void convert(MyViewHolder holder, Custom custom, int position) {

                holder.setTextRound(R.id.roundTextView, MyUtils.getRandomColor())
                        .setText(R.id.tv_head, custom.getCustomerName().substring(0, 2))
                        .setText(R.id.tv_custom_name, custom.getCustomerName())
                        .setText(R.id.tv_address, custom.getCustomerAddress())
                        .setText(R.id.tv_time, DateUtil.getDateFormatStr(Long.valueOf(custom.getTime()),
                                getString(R.string.date_format_custom)));
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
    }

    @Override
    protected void loadFirstPage() {
        mCustomListPresenter.refresh();
        setListCount(String.format("共%s个客户", mCustomListPresenter.getTotalNumber()));
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
        if (errorCode.equals(ErrorCode.SESSION_OUT)) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }
}
