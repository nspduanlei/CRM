package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.GetUserInfoUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.UserInfoView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/19.
 */

public class UserInfoPresenter implements Presenter {

    UserInfoView mUserInfoView;
    GetUserInfoUseCase mGetUserInfoUseCase;

    @Inject
    public UserInfoPresenter(GetUserInfoUseCase getUserInfoUseCase) {
        mGetUserInfoUseCase = getUserInfoUseCase;
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
        mUserInfoView = (UserInfoView) v;
    }

    @Override
    public void onCreate() {
        getUserInfo();
    }

    public void getUserInfo() {
        mUserInfoView.showLoadingView();

        mGetUserInfoUseCase.execute().subscribe(this::onGetUserReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {
        mUserInfoView.hideLoadingView();
    }

    private void onGetUserReceived(Result<User> userResult) {
        mUserInfoView.hideLoadingView();

        if (userResult.isSucceed()) {
            mUserInfoView.getUserInfoSuccess(userResult.getData());
        }

    }
}
