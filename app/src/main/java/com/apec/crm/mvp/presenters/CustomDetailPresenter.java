package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.GetCustomDetailUseCase;
import com.apec.crm.domin.useCase.custom.UpdateCustomUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.CustomDetailView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/25.
 */

public class CustomDetailPresenter implements Presenter {

    GetCustomDetailUseCase mGetCustomDetailUseCase;
    UpdateCustomUseCase mUpdateCustomUseCase;

    CustomDetailView mCustomDetailView;

    @Inject
    public CustomDetailPresenter(GetCustomDetailUseCase getCustomDetailUseCase,
                                 UpdateCustomUseCase updateCustomUseCase) {
        mGetCustomDetailUseCase = getCustomDetailUseCase;
        mUpdateCustomUseCase = updateCustomUseCase;
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
        mCustomDetailView = (CustomDetailView) v;
    }

    @Override
    public void onCreate() {

    }

    public void getCustomDetail(String id) {
        mCustomDetailView.showLoadingView();

        mGetCustomDetailUseCase.setData(id);
        mGetCustomDetailUseCase.execute().subscribe(this::onCustomDetailReceived,
                this::manageError);
    }

    private void manageError(Throwable throwable) {
        mCustomDetailView.hideLoadingView();
    }


    private void onCustomDetailReceived(Result<CustomDetail> customDetailResult) {
        mCustomDetailView.hideLoadingView();
        if (customDetailResult.isSucceed()) {
            mCustomDetailView.getCustomDetailSuccess(customDetailResult.getData());
        }
    }

    public void updateCustom(CustomDetail customDetail) {
        mCustomDetailView.showLoadingView();

        mUpdateCustomUseCase.setData(customDetail);
        mUpdateCustomUseCase.execute().subscribe(this::onUpdateReceived, this::manageError);
    }

    private void onUpdateReceived(Result result) {
        mCustomDetailView.hideLoadingView();
        if (result.isSucceed()) {
            mCustomDetailView.updateCustomSuccess();
        }
    }
}