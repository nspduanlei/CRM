package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.custom.PickCustomUseCase;
import com.apec.crm.mvp.presenters.core.ListPresenter;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.CustomListView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 16/9/28.
 */

public class CustomListPresenter extends ListPresenter implements Presenter {

    GetCustomListUseCase mGetCustomListUseCase;
    PickCustomUseCase mPickCustomUseCase;

    CustomListView mCustomListView;
    FilterCustomBean mFilterCustomBean;

    Subscription mRefreshSubscription, mMoreSubscription, mPickSubscription;


    public void setType(int type) {
        mFilterCustomBean.setType(type);
    }

    @Inject
    public CustomListPresenter(GetCustomListUseCase getCustomListUseCase,
                               PickCustomUseCase pickCustomUseCase) {
        mGetCustomListUseCase = getCustomListUseCase;
        mPickCustomUseCase = pickCustomUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mRefreshSubscription, mMoreSubscription,
                mPickSubscription);
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
        mFilterCustomBean = new FilterCustomBean();
        mFilterCustomBean.setPageSize(LIST_ITEM_COUNT);
    }

    @Override
    protected void doRefresh() {
        mFilterCustomBean.setPageNumber(String.valueOf(mCurrentPage));

        mGetCustomListUseCase.setData(mFilterCustomBean);
        mRefreshSubscription = mGetCustomListUseCase.execute()
                .subscribe(this::onRefreshReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mCustomListView.hideLoadingView();

        throwable.printStackTrace();
        mCustomListView.onError("0", "");
    }

    private void onRefreshReceived(Result<ListPage<Custom>> listPageResult) {
        if (listPageResult.isSucceed()) {
            totalPage = listPageResult.getData().getPageCount();
            totalNumber = listPageResult.getData().getTotalElements();
            mCustomListView.onRefreshSuccess(listPageResult.getData().getRows());
        } else {
            mCustomListView.onError(listPageResult.getErrorCode(),
                    listPageResult.getErrorMsg());
        }
    }

    @Override
    protected void doLoadMore() {
        mFilterCustomBean.setPageNumber(String.valueOf(mCurrentPage));

        mGetCustomListUseCase.setData(mFilterCustomBean);
        mMoreSubscription = mGetCustomListUseCase.execute()
                .subscribe(this::onLoadMoreReceived, this::manageError);
    }

    @Override
    protected void onNoMore() {
        mCustomListView.onNoMore();
    }

    private void onLoadMoreReceived(Result<ListPage<Custom>> listPageResult) {
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

    public void setFilter(FilterCustomBean filterCustomBean) {
        mFilterCustomBean.setFilterData(filterCustomBean);
    }

    /**
     * 拾取客户
     * @param id
     */
    public void pickCustom(String id) {
        mCustomListView.showLoadingView();

        mPickCustomUseCase.setData(id);
        mPickSubscription = mPickCustomUseCase.execute().
                subscribe(this::onPickReceived, this::manageError);
    }

    private void onPickReceived(Result result) {
        mCustomListView.hideLoadingView();

        if (result.isSucceed()) {
            mCustomListView.onPickSuccess();
        }
    }
}
