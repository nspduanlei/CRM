package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.user.GetMyCountUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.WorkPlaceView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/10/14.
 */

public class WorkPlacePresenter implements Presenter {

    WorkPlaceView mWorkPlaceView;
    GetMyCountUseCase mGetMyCountUseCase;
    Subscription mGetMyCountSub;

    @Inject
    public WorkPlacePresenter(GetMyCountUseCase getMyCountUseCase) {
        mGetMyCountUseCase = getMyCountUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mGetMyCountSub);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mWorkPlaceView = (WorkPlaceView) v;
    }

    @Override
    public void onCreate() {

    }

    /**
     * 获取我本月的统计数量
     */
    public void getMyMonthCount() {
        mWorkPlaceView.showLoadingView();
        mGetMyCountSub = mGetMyCountUseCase.execute().
                subscribe(this::onMyCountReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mWorkPlaceView.hideLoadingView();

    }

    private void onMyCountReceived(Result<MyCount> myCountResult) {
        mWorkPlaceView.hideLoadingView();
        if (myCountResult.isSucceed()) {
            mWorkPlaceView.onMyCountSuccess(myCountResult.getData());
        }
    }

}
