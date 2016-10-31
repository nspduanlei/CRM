package com.apec.crm.views.fragments;

import android.os.Bundle;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.injector.components.DaggerVisitComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.VisitRecordPresenter;
import com.apec.crm.mvp.views.VisitRecordView;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.T;
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

    private static final String ARG_FILTER = "arg_filter";

    @Override
    protected CommonRecyclerAdapter getAdapter() {
        //隐藏头部
        hideHead();

        CommonRecyclerAdapter adapter = new CommonRecyclerAdapter<VisitRecord>(getActivity(),
                R.layout.item_visit_record,
                new ArrayList<>()) {
            @Override
            public void convert(MyViewHolder holder, VisitRecord visitRecord, int position) {
                holder.setText(R.id.tv_custom_name, visitRecord.getCustomerName())
                        .setText(R.id.tv_visit_des, visitRecord.getVisitRemarks())
                        .setText(R.id.tv_contact_name, visitRecord.getContactName())
                        .setText(R.id.tv_visit_time,
                                DateUtil.getNearTime(visitRecord.getCreateDate()))
                        .setText(R.id.tv_address, visitRecord.getCustomerAddress());

                //填充图片
                switch (visitRecord.getImageItems().size()) {
                    case 0:
                        holder.setVisibility(R.id.iv_visit_1, View.GONE).
                                setVisibility(R.id.iv_visit_2, View.GONE).
                                setVisibility(R.id.iv_visit_3, View.GONE);
                        break;
                    case 1:
                        holder.setVisibility(R.id.iv_visit_2, View.GONE)
                                .setVisibility(R.id.iv_visit_3, View.GONE)
                                .setVisibility(R.id.iv_visit_1, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_1,
                                        visitRecord.getImageItems().get(0).getThumbnailUrl());

                        break;
                    case 2:
                        holder.setVisibility(R.id.iv_visit_1, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_1,
                                        visitRecord.getImageItems().get(0).getThumbnailUrl())
                                .setVisibility(R.id.iv_visit_2, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_2,
                                        visitRecord.getImageItems().get(1).getThumbnailUrl())
                                .setVisibility(R.id.iv_visit_3, View.GONE);
                        break;
                    case 3:
                        holder.setVisibility(R.id.iv_visit_1, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_1,
                                        visitRecord.getImageItems().get(0).getThumbnailUrl())
                                .setVisibility(R.id.iv_visit_2, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_2,
                                        visitRecord.getImageItems().get(1).getThumbnailUrl())
                                .setVisibility(R.id.iv_visit_3, View.VISIBLE)
                                .setImageUrl(R.id.iv_visit_3,
                                        visitRecord.getImageItems().get(2).getThumbnailUrl());
                        break;
                }

                //设置图片点击
                ArrayList<String> urls = visitRecord.getOriginalUrlList();
                holder.setOnClickLister(R.id.iv_visit_1, v -> {
                    ImageDialogFragment.newInstance(urls, 0)
                            .show(getActivity().getFragmentManager(), "ImageDialogFragment");
                });

                holder.setOnClickLister(R.id.iv_visit_2, v -> {
                    ImageDialogFragment.newInstance(urls, 1)
                            .show(getActivity().getFragmentManager(), "ImageDialogFragment");
                });

                holder.setOnClickLister(R.id.iv_visit_3, v -> {
                    ImageDialogFragment.newInstance(urls, 2)
                            .show(getActivity().getFragmentManager(), "ImageDialogFragment");
                });
            }
        };
        return adapter;
    }

    public static VisitRecordFragment newInstance(VisitRecordFilter visitRecordFilter) {
        VisitRecordFragment newFragment = new VisitRecordFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_FILTER, visitRecordFilter);

        newFragment.setArguments(bundle);
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
        VisitRecordFilter visitFilter = getArguments().getParcelable(ARG_FILTER);
        if (visitFilter == null) {
            return;
        }
        mVisitRecordPresenter.setVisitRecordFilter(visitFilter);
    }

    @Override
    protected void loadFirstPage() {
        mVisitRecordPresenter.refresh();
    }

    @Override
    protected void loadOtherPage() {
        mVisitRecordPresenter.loadMore();
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

    }

    /**
     * 根据过滤条件刷新页面
     */
    public void updateForFilter(VisitRecordFilter visitFilter) {
        if (visitFilter != null) {
            mVisitRecordPresenter.setVisitRecordFilter(visitFilter);
        }
        initiateRefresh();
    }
}
