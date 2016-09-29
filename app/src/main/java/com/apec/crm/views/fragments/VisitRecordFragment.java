package com.apec.crm.views.fragments;

import android.view.View;

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

    @Override
    protected void initUI(View view) {

    }

    @Override
    protected CommonRecyclerAdapter getAdapter() {

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
        mVisitRecordPresenter.refresh();
    }

    @Override
    protected void loadOtherPage() {

    }

    @Override
    public void onRefreshSuccess(ArrayList data) {
        onRefreshComplete(data);
    }

    @Override
    public void onLoadMoreSuccess() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError() {

    }
}
