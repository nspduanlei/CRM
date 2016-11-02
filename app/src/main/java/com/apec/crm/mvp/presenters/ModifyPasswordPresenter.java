package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.ModifyPasswordUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.ModifyPasswordView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/11/1.
 */

public class ModifyPasswordPresenter implements Presenter {

    ModifyPasswordView mModifyPasswordView;
    ModifyPasswordUseCase mModifyPasswordUseCase;

    @Inject
    public ModifyPasswordPresenter(ModifyPasswordUseCase modifyPasswordUseCase) {
        mModifyPasswordUseCase = modifyPasswordUseCase;
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
        mModifyPasswordView = (ModifyPasswordView) v;
    }

    @Override
    public void onCreate() {

    }

    public void modifyPassword(String currPassword, String newPassword) {
        mModifyPasswordView.showLoadingView();

        mModifyPasswordUseCase.setData(currPassword, newPassword);
        mModifyPasswordUseCase.execute().subscribe(this::onModifyReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mModifyPasswordView.hideLoadingView();
    }

    private void onModifyReceived(Result result) {
        mModifyPasswordView.hideLoadingView();
        if (result.isSucceed()) {
            mModifyPasswordView.onModifySuccess();
        }
    }
}
