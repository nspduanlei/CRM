package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.RecordFilter;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.mvp.presenters.core.ListPresenter;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.CustomListView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 16/9/28.
 */

public class CustomListPresenter extends ListPresenter implements Presenter {

    CustomListView mCustomListView;

    GetCustomListUseCase mGetCustomListUseCase;

    @Inject
    public CustomListPresenter(GetCustomListUseCase getCustomListUseCase) {
        mGetCustomListUseCase = getCustomListUseCase;
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
        mCustomListView = (CustomListView) v;
    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void doRefresh() {
        RecordFilter recordFilter = new RecordFilter();
        recordFilter.setPageSize(LIST_ITEM_COUNT);
        recordFilter.setPageNumber(String.valueOf(mCurrentPage));

        mGetCustomListUseCase.setData(recordFilter);
        mGetCustomListUseCase.execute()
                .subscribe(this::onRefreshReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void onRefreshReceived(Result<ListPage<Custom>> listPageResult) {
        if (listPageResult.isSucceed()) {
            totalPage = listPageResult.getData().getTotalPages();
            totalNumber = listPageResult.getData().getTotalElements();
            mCustomListView.onRefreshSuccess(listPageResult.getData().getRows());
        } else {
            mCustomListView.onError(listPageResult.getErrorCode(),
                    listPageResult.getErrorMsg());
        }
    }

    @Override
    protected void doLoadMore() {
        RecordFilter recordFilter = new RecordFilter();
        recordFilter.setPageSize(LIST_ITEM_COUNT);
        recordFilter.setPageNumber(String.valueOf(mCurrentPage));

        mGetCustomListUseCase.setData(recordFilter);
        mGetCustomListUseCase.execute()
                .subscribe(this::onLoadMoreSuccess, this::manageError);
    }

    @Override
    protected void onNoMore() {
        mCustomListView.onNoMore();
    }

    private void onLoadMoreSuccess(Result<ListPage<Custom>> listPageResult) {
        if (listPageResult.isSucceed()) {
            mCustomListView.onLoadMoreSuccess(listPageResult.getData().getRows());
        } else {
            mCustomListView.onError(listPageResult.getErrorCode(),
                    listPageResult.getErrorMsg());
        }
    }

    public int getTotalNumber() {
        return totalNumber;
    }


}
