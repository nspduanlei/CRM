package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.FilterCustomBean;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.SearchCustomView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

import static android.R.attr.filter;

/**
 * Created by duanlei on 2016/10/18.
 */

public class SearchCustomPresenter implements Presenter {

    GetCustomListUseCase mGetCustomListUseCase;

    SearchCustomView mSearchCustomView;

    FilterCustomBean mFilter;

    @Inject
    public SearchCustomPresenter(GetCustomListUseCase getCustomListUseCase) {
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
        mSearchCustomView = (SearchCustomView) v;
    }

    @Override
    public void onCreate() {
        mFilter = new FilterCustomBean();
    }

    public void searchCustom(String searchKey) {
        mFilter.setKeywords(searchKey);
        mGetCustomListUseCase.setData(mFilter);

        mSearchCustomView.showLoadingView();
        mGetCustomListUseCase.execute().subscribe(this::onSearchReceived,
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
