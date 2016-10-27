package com.apec.crm.views.fragments;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.injector.components.DaggerVisitComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.VisitRecordPresenter;
import com.apec.crm.mvp.views.VisitRecordView;
import com.apec.crm.views.fragments.core.BaseListFragment;
import com.apec.crm.views.widget.recyclerView.CommonRecyclerAdapter;
import com.apec.crm.views.widget.recyclerView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duanlei on 16/9/28.
 */
public class VisitRecordFragment extends BaseListFragment implements VisitRecordView {

    @Inject
    VisitRecordPresenter mVisitRecordPresenter;

    //根据客户搜索拜访记录
    public static final String ARG_CUSTOM_ID = "arg_custom_id";
    private String mCustomId;

    //根据日期搜索拜访记录 2016-09-19
    public static final String ARG_DATE_STR = "arg_date_str";
    private String mDataStr;

    //根据用户搜索拜访记录
    public static final String ARG_USER_ID = "arg_user_id";
    private String mUserId;

    @Override
    protected CommonRecyclerAdapter getAdapter() {
        //隐藏头部
        hideHead();

        CommonRecyclerAdapter adapter = new CommonRecyclerAdapter<VisitRecord>(getActivity(),
                R.layout.item_visit_record,
                new ArrayList<>()) {
            @Override
            public void convert(MyViewHolder holder, VisitRecord visitRecord, int position) {
                holder.setText(R.id.tv_custom_name, "id=" + visitRecord.getId());
            }
        };

        return adapter;
    }

    public static VisitRecordFragment newInstance(Bundle args) {
        VisitRecordFragment newFragment = new VisitRecordFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerVisitComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mVisitRecordPresenter.attachView(this);
        mVisitRecordPresenter.onCreate();
    }

    @Override
    protected void loadFirstPage() {
        //TODO test
        ArrayList<VisitRecord> visitRecords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            visitRecords.add(new VisitRecord());
        }
        onRefreshComplete(visitRecords);

        //mVisitRecordPresenter.refresh();
    }

    public void refreshData() {
        loadFirstPage();
    }

    @Override
    protected void loadOtherPage() {

    }

    @Override
    public void onRefreshSuccess(ArrayList data) {
        onRefreshComplete(data);
    }

    @Override
    public void onLoadMoreSuccess(ArrayList data) {
        onLoadMoreComplete(data);
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }
}
