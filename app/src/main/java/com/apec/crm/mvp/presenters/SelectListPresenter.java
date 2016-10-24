package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.useCase.custom.GetContactUseCase;
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
    GetContactUseCase mGetContactUseCase;

    SelectListView mSelectListView;

    @Inject
    public SelectListPresenter(GetCustomAttributeUseCase getCustomAttributeUseCase,
                               GetContactUseCase getContactUseCase) {
        mGetCustomAttributeUseCase = getCustomAttributeUseCase;
        mGetContactUseCase = getContactUseCase;
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
    public void getCustomTypeList(String type) {
        mSelectListView.showLoadingView();

        mGetCustomAttributeUseCase.setData(type);
        mGetCustomAttributeUseCase.execute()
                .subscribe(this::onGetDataReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mSelectListView.hideLoadingView();

    }

    private void onGetDataReceived(ListResult<SelectContent> selectContentListResult) {
        mSelectListView.hideLoadingView();

        if (selectContentListResult.isSucceed()) {
            mSelectListView.onGetListSuccess(selectContentListResult.getData());
        }
    }

    public void getContacts(String customId) {
        mSelectListView.hideLoadingView();

        mGetContactUseCase.setData(customId);
        mGetContactUseCase.execute().subscribe(this::onGetContactReceived, this::manageError);

    }

    private void onGetContactReceived(ListResult<Contact> contactListResult) {

    }
}
