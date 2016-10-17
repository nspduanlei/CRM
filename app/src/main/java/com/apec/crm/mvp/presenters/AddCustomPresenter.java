package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.AddCustomUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.AddCustomView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/17.
 */

public class AddCustomPresenter implements Presenter {


    AddCustomUseCase mAddCustomUseCase;

    AddCustomView mAddCustomView;

    @Inject
    public AddCustomPresenter(AddCustomUseCase addCustomUseCase) {
        mAddCustomUseCase = addCustomUseCase;
    }


    public void addCustom(CustomDetail customDetail) {
        mAddCustomView.showLoadingView();

        mAddCustomUseCase.setData(customDetail);
        mAddCustomUseCase.execute().subscribe(this::onAddReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mAddCustomView.hideLoadingView();
    }

    private void onAddReceived(Result result) {
        mAddCustomView.hideLoadingView();

        if (result.isSucceed()) {
            mAddCustomView.onAddCustomSuccess();
        }

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
        mAddCustomView = (AddCustomView) v;
    }

    @Override
    public void onCreate() {

    }
}
