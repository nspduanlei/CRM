package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.custom.PickCustomUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.SearchCustomView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/10/18.
 */

public class SearchCustomPresenter implements Presenter {

    GetCustomListUseCase mGetCustomListUseCase;
    PickCustomUseCase mPickCustomUseCase;

    SearchCustomView mSearchCustomView;

    FilterCustomBean mFilter;

    Subscription mSubscription, mPickSubscription;

    public void setType(int type) {
        mFilter.setType(type);
    }

    @Inject
    public SearchCustomPresenter(GetCustomListUseCase getCustomListUseCase,
                                 PickCustomUseCase pickCustomUseCase) {
        mGetCustomListUseCase = getCustomListUseCase;
        mPickCustomUseCase = pickCustomUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mSubscription);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mSearchCustomView = (SearchCustomView) v;
    }

    @Override
    public void onCreate() {
        mFilter = new FilterCustomBean();
    }

    /**
     * 搜索客户
     * @param searchKey
     */
    public void searchCustom(String searchKey) {
        mFilter.setKeywords(searchKey);
        mGetCustomListUseCase.setData(mFilter);

        mSearchCustomView.showLoadingView();
        mSubscription = mGetCustomListUseCase.execute().
                subscribe(this::onSearchReceived,
                this::manageError);
    }

    private void manageError(Throwable throwable) {
        mSearchCustomView.hideLoadingView();
    }

    private void onSearchReceived(Result<ListPage<Custom>> listPageResult) {
        mSearchCustomView.hideLoadingView();

        if (listPageResult.isSucceed()) {
            mSearchCustomView.onSearchSuccess(listPageResult.getData().getRows());
        }
    }

    /**
     * 拾取客户
     * @param id
     */
    public void pickCustom(String id) {
        mSearchCustomView.showLoadingView();

        mPickCustomUseCase.setData(id);
        mPickSubscription = mPickCustomUseCase.execute().
                subscribe(this::onPickReceived, this::manageError);
    }

    private void onPickReceived(Result result) {
        mSearchCustomView.hideLoadingView();

        if (result.isSucceed()) {
            mSearchCustomView.onPickSuccess();
        }
    }
}
