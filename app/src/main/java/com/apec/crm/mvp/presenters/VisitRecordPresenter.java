package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.visit.GetVisitByUUseCase;
import com.apec.crm.mvp.presenters.core.ListPresenter;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.VisitRecordView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 16/9/28.
 */

public class VisitRecordPresenter extends ListPresenter implements Presenter {

    VisitRecordView mVisitRecordView;
    GetVisitByUUseCase mGetVisitRecordUseCase;

    @Inject
    public VisitRecordPresenter(GetVisitByUUseCase getVisitRecordUseCase) {
        mGetVisitRecordUseCase = getVisitRecordUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

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

    }

    @Override
    protected void doRefresh() {
        VisitRecordFilter visitRecordFilter = new VisitRecordFilter();
        visitRecordFilter.setStartDate("2016-09-14");
        visitRecordFilter.setEndDate("2016-09-27");

        mGetVisitRecordUseCase.setData(visitRecordFilter);
        mGetVisitRecordUseCase.execute()
                .subscribe(this::onRefreshReceived, this::manageError);

    }

    private void manageError(Throwable throwable) {

    }

    private void onRefreshReceived(Result<ListPage<VisitRecord>> listPageResult) {
        if (listPageResult.isSucceed()) {
            mVisitRecordView.onRefreshSuccess(listPageResult.getData().getRows());
        }
    }

    @Override
    protected void doLoadMore() {

    }

    @Override
    protected void onNoMore() {

    }


}
