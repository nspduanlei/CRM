package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.RecordFilter;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.visit.GetVisitRecordUseCase;
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
    GetVisitRecordUseCase mGetVisitRecordUseCase;

    @Inject
    public VisitRecordPresenter(GetVisitRecordUseCase getVisitRecordUseCase) {
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
        RecordFilter recordFilter = new RecordFilter();
        recordFilter.setStartDate("2016-09-14");
        recordFilter.setEndDate("2016-09-27");

        mGetVisitRecordUseCase.setData(recordFilter);
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
