package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.AddVisitBean;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.visit.AddVisitUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.AddVisitView;
import com.apec.crm.mvp.views.core.View;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/26.
 */

public class AddVisitPresenter implements Presenter {

    AddVisitUseCase mAddVisitUseCase;
    AddVisitView mAddVisitView;


    @Inject
    public AddVisitPresenter(AddVisitUseCase addVisitUseCase) {
        mAddVisitUseCase = addVisitUseCase;
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
        mAddVisitView = (AddVisitView) v;
    }

    @Override
    public void onCreate() {

    }

    public void addVisit(AddVisitBean addVisitBean, ArrayList<File> files) {

        mAddVisitView.showLoadingView();
        mAddVisitUseCase.setData(addVisitBean, files);
        mAddVisitUseCase.execute().subscribe(this::onAddVisitReceived, this::manageError);

    }

    private void manageError(Throwable throwable) {
        mAddVisitView.hideLoadingView();
    }

    private void onAddVisitReceived(Result result) {
        mAddVisitView.hideLoadingView();
        if (result.isSucceed()) {

            mAddVisitView.addVisitSuccess();
        }
    }
}
