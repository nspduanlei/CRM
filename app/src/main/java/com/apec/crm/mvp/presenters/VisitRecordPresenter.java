package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.visit.GetVisitsUseCase;
import com.apec.crm.mvp.presenters.core.ListPresenter;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.VisitRecordView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 16/9/28.
 */
public class VisitRecordPresenter extends ListPresenter implements Presenter {

    VisitRecordFilter mVisitRecordFilter;

    VisitRecordView mVisitRecordView;

    GetVisitsUseCase mGetVisitsUseCase;

    Subscription mRefreshSubscription, mMoreSubscription;

    @Inject
    public VisitRecordPresenter(GetVisitsUseCase getVisitsUseCase) {
        mGetVisitsUseCase = getVisitsUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mRefreshSubscription, mMoreSubscription);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mVisitRecordView = (VisitRecordView) v;
    }

    @Override
    public void onCreate() {
        mVisitRecordFilter = new VisitRecordFilter();
    }

    @Override
    protected void doRefresh() {
        mVisitRecordFilter.setPageNumber(String.valueOf(mCurrentPage));
        mVisitRecordFilter.setPageSize(LIST_ITEM_COUNT);

        mGetVisitsUseCase.setData(mVisitRecordFilter);
        mRefreshSubscription = mGetVisitsUseCase.execute()
                .subscribe(this::onRefreshReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mVisitRecordView.onRefreshSuccess(new ArrayList());
    }

    private void onRefreshReceived(Result<ListPage<VisitRecord>> listPageResult) {
        if (listPageResult.isSucceed()) {
            mVisitRecordView.onRefreshSuccess(listPageResult.getData().getRows());
        }
    }

    @Override
    protected void doLoadMore() {
        mVisitRecordFilter.setPageNumber(String.valueOf(mCurrentPage));
        mVisitRecordFilter.setPageSize(LIST_ITEM_COUNT);

        mGetVisitsUseCase.setData(mVisitRecordFilter);
        mMoreSubscription = mGetVisitsUseCase.execute()
                .subscribe(this::onLoadMoreReceived, this::manageError);
    }

    private void onLoadMoreReceived(Result<ListPage<VisitRecord>> listPageResult) {
        if (listPageResult.isSucceed()) {
            mVisitRecordView.onLoadMoreSuccess(listPageResult.getData().getRows());
        } else {
            mVisitRecordView.onError(listPageResult.getErrorCode(),
                    listPageResult.getErrorMsg());
        }
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    @Override
    protected void onNoMore() {
        mVisitRecordView.onNoMore();
    }

    public void setVisitRecordFilter(VisitRecordFilter visitRecordFilter) {
        mVisitRecordFilter = visitRecordFilter;
    }
}
