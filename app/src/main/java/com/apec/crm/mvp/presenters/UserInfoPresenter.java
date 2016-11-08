package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.GetUserInfoUseCase;
import com.apec.crm.domin.useCase.user.UploadHeaderUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.UserInfoView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import java.io.File;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/10/19.
 */

public class UserInfoPresenter implements Presenter {

    UserInfoView mUserInfoView;

    GetUserInfoUseCase mGetUserInfoUseCase;

    UploadHeaderUseCase mUploadHeaderUseCase;

    Subscription mSubscription, mUploadSubscription;

    @Inject
    public UserInfoPresenter(GetUserInfoUseCase getUserInfoUseCase, UploadHeaderUseCase
            uploadHeaderUseCase) {
        mGetUserInfoUseCase = getUserInfoUseCase;
        mUploadHeaderUseCase = uploadHeaderUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mSubscription, mUploadSubscription);
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
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        mUserInfoView.showLoadingView();
        mSubscription = mGetUserInfoUseCase.execute().
                subscribe(this::onGetUserReceived, this::managerError);
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

    /**
     * 上传用户头像
     * @param file
     */
    public void uploadUserHead(File file) {
        mUserInfoView.showLoadingView();

        mUploadHeaderUseCase.setData(file);
        mUploadSubscription = mUploadHeaderUseCase.execute().
                subscribe(this::onUploadHeadReceived, this::managerError);
    }

    private void onUploadHeadReceived(Result<String> result) {
        mUserInfoView.hideLoadingView();
        if (result.isSucceed()) {
            mUserInfoView.onUploadHeadSuccess(result.getData());
        }
    }

}
