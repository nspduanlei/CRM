package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.DeleteCustomUseCase;
import com.apec.crm.domin.useCase.custom.ReturnPoolUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.CustomView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/11/1.
 */

public class CustomPresenter implements Presenter {

    ReturnPoolUseCase mReturnPoolUseCase;
    DeleteCustomUseCase mDeleteCustomUseCase;

    CustomView mCustomView;

    Subscription mReturnSubscription, mDeleteSubscription;

    @Inject
    public CustomPresenter(ReturnPoolUseCase returnPoolUseCase,
                           DeleteCustomUseCase deleteCustomUseCase) {
        mReturnPoolUseCase = returnPoolUseCase;
        mDeleteCustomUseCase = deleteCustomUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mReturnSubscription, mDeleteSubscription);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mCustomView = (CustomView) v;
    }

    @Override
    public void onCreate() {

    }

    /**
     * 退回公海
     * @param id
     */
    public void returnPool(String id) {
        mCustomView.showLoadingView();

        mReturnPoolUseCase.setData(id);
        mReturnSubscription = mReturnPoolUseCase.execute().
                subscribe(this::onReturnReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {

    }

    private void onReturnReceived(Result result) {
        mCustomView.hideLoadingView();

        if (result.isSucceed()) {
            mCustomView.onRetPoolSuccess();
        }
    }

    /**
     * 删除客户
     * @param id
     */
    public void deleteCustom(String id) {
        mCustomView.showLoadingView();

        mDeleteCustomUseCase.setData(id);
        mDeleteSubscription = mDeleteCustomUseCase.execute().
                subscribe(this::onDelReceived, this::manageError);
    }

    private void onDelReceived(Result result) {
        mCustomView.hideLoadingView();

        if (result.isSucceed()) {
            mCustomView.onDelCustomSuccess();
        }
    }


}
