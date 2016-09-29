package com.apec.crm.mvp.presenters;


import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.LoginUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.LoginView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 16/9/27.
 */

public class LoginPresenter implements Presenter {

    LoginUseCase mLoginUseCase;
    Subscription mLoginSubscription;

    LoginView mLoginView;

    @Inject
    public LoginPresenter(LoginUseCase loginUseCase) {
        mLoginUseCase = loginUseCase;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mLoginSubscription != null) {
            mLoginSubscription.unsubscribe();
        }

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mLoginView = (LoginView) v;
    }

    @Override
    public void onCreate() {

    }

    public void login(String userName, String password) {
        mLoginView.showLoadingView();

        mLoginUseCase.setData(userName, password);
        mLoginSubscription = mLoginUseCase.execute()
                .subscribe(this::onLoginReceived, this::manageError);

    }

    private void manageError(Throwable throwable) {
        mLoginView.hideLoadingView();

    }

    private void onLoginReceived(Result<User> result) {
        mLoginView.hideLoadingView();

        if (result.isSucceed()) {
            mLoginView.onLoginSuccess(result.getData());
        }


    }
}
