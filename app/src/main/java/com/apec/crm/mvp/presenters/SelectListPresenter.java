package com.apec.crm.mvp.presenters;

import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.SelectListView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/17.
 */

public class SelectListPresenter implements Presenter {

    GetCustomAttributeUseCase mGetCustomAttributeUseCase;

    SelectListView mSelectListView;

    @Inject
    public SelectListPresenter(GetCustomAttributeUseCase getCustomAttributeUseCase) {
        mGetCustomAttributeUseCase = getCustomAttributeUseCase;
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
        mSelectListView = (SelectListView) v;
    }

    @Override
    public void onCreate() {

    }

    /**
     * 获取客户类型选择项
     */
    public void getCustomTypeList() {
        mSelectListView.showLoadingView();

        mGetCustomAttributeUseCase.setData(Constants.CUSTOMER_TYPE);
        mGetCustomAttributeUseCase.execute()
                .subscribe(this::onGetDataReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mSelectListView.hideLoadingView();

    }

    private void onGetDataReceived(ListResult<SelectContent> selectContentListResult) {
        if (selectContentListResult.isSucceed()) {
            mSelectListView.onGetListSuccess(selectContentListResult.getData());
        }
    }
}
