package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.GetUserListUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.SearchUserView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/11/10.
 */

public class SearchUserPresenter implements Presenter {

    GetUserListUseCase mGetUserListUseCase;

    SearchUserView mSearchUserView;

    Subscription mSubscription;

    @Inject
    public SearchUserPresenter(GetUserListUseCase getUserListUseCase) {
        mGetUserListUseCase = getUserListUseCase;
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
        mSearchUserView = (SearchUserView) v;
    }

    @Override
    public void onCreate() {
        getUserList();
    }

    public void getUserList() {
        mSearchUserView.showLoadingView();

        mSubscription = mGetUserListUseCase.execute()
                .subscribe(this::onGetUsersReceived, this::manager);
    }

    public void getUserList(String keyword) {
        mGetUserListUseCase.setData(keyword);
        getUserList();
    }


    private void manager(Throwable throwable) {
        mSearchUserView.hideLoadingView();
    }

    private void onGetUsersReceived(Result<ListPage<User>> userResult) {
        mSearchUserView.hideLoadingView();

        if (userResult.isSucceed()) {
            mSearchUserView.onSearchSuccess(userResult.getData().getRows());
        }

    }
}
