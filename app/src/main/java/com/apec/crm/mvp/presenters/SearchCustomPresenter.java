package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
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

    SearchCustomView mSearchCustomView;

    FilterCustomBean mFilter;

    Subscription mSubscription;

    public void setType(int type) {
        mFilter.setType(type);
    }

    @Inject
    public SearchCustomPresenter(GetCustomListUseCase getCustomListUseCase) {
        mGetCustomListUseCase = getCustomListUseCase;
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
}
