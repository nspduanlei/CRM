package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.Version;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.sys.GetNewVersionUseCase;
import com.apec.crm.domin.useCase.user.GetUserInfoUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.ProfileView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/11/4.
 */

public class ProfilePresenter implements Presenter {

    GetNewVersionUseCase mGetNewVersionUseCase;
    GetUserInfoUseCase mGetUserInfoUseCase;

    ProfileView mProfileView;

    Subscription mVersionSubscription, mUserInfoSubscription;

    @Inject
    public ProfilePresenter(GetNewVersionUseCase getNewVersionUseCase,
                            GetUserInfoUseCase getUserInfoUseCase) {
        mGetNewVersionUseCase = getNewVersionUseCase;
        mGetUserInfoUseCase = getUserInfoUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mVersionSubscription, mUserInfoSubscription);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mProfileView = (ProfileView) v;
    }

    @Override
    public void onCreate() {
        getUserInfo();
        getVersion();
    }

    /**
     * 获取版本号
     */
    public void getVersion() {
        mProfileView.showLoadingView();
        mVersionSubscription = mGetNewVersionUseCase.execute().
                subscribe(this::onVersionReceived, this::manageError);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        mProfileView.showLoadingView();
        mUserInfoSubscription = mGetUserInfoUseCase.execute().
                subscribe(this::onUserInfoReceived, this::manageError);
    }

    private void onUserInfoReceived(Result<User> userResult) {
        mProfileView.hideLoadingView();
        if (userResult.isSucceed()) {
            mProfileView.getUserInfoSuccess(userResult.getData());
        }
    }

    private void manageError(Throwable throwable) {
        mProfileView.hideLoadingView();
    }

    private void onVersionReceived(Result<Version> versionResult) {
        mProfileView.hideLoadingView();

        if (versionResult.isSucceed()) {
            mProfileView.getVersionSuccess(versionResult.getData());
        }
    }
}
