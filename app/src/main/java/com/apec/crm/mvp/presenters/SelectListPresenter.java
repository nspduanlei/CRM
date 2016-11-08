package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.OpenSea;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.useCase.custom.GetContactUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.domin.useCase.custom.GetOpenSeaUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.SelectListView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/10/17.
 */

public class SelectListPresenter implements Presenter {

    GetCustomAttributeUseCase mGetCustomAttributeUseCase;
    GetContactUseCase mGetContactUseCase;
    GetOpenSeaUseCase mGetOpenSeaUseCase;

    SelectListView mSelectListView;

    Subscription mCustomAttrSubscription, mContactSubscription, mSeaSubscription;

    @Inject
    public SelectListPresenter(GetCustomAttributeUseCase getCustomAttributeUseCase,
                               GetContactUseCase getContactUseCase,
                               GetOpenSeaUseCase getOpenSeaUseCase) {
        mGetCustomAttributeUseCase = getCustomAttributeUseCase;
        mGetContactUseCase = getContactUseCase;
        mGetOpenSeaUseCase = getOpenSeaUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mCustomAttrSubscription, mContactSubscription,
                mSeaSubscription);
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
        mCustomAttrSubscription = mGetCustomAttributeUseCase.execute()
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

    /**
     * 获取客户联系人列表
     * @param customId
     */
    public void getContacts(String customId) {
        mSelectListView.showLoadingView();

        mGetContactUseCase.setData(customId);
        mContactSubscription = mGetContactUseCase.execute().
                subscribe(this::onGetContactReceived, this::manageError);

    }

    private void onGetContactReceived(ListResult<Contact> contactListResult) {
        mSelectListView.hideLoadingView();
        if (contactListResult.isSucceed()) {
            List<Contact> contacts = contactListResult.getData();
            ArrayList<SelectContent> selectContents = new ArrayList<>();
            for (int i = 0; i < contacts.size(); i++) {
                selectContents.add(new SelectContent(contacts.get(i).getId(),
                        contacts.get(i).getContactName(), contacts.get(i).getContactPhone()));
            }
            mSelectListView.onGetListSuccess(selectContents);
        }
    }

    /**
     * 选择片区
     */
    public void getOpenSeas(String id) {
        mSelectListView.showLoadingView();

        mGetOpenSeaUseCase.setData(id);
        mSeaSubscription = mGetOpenSeaUseCase.execute().
                subscribe(this::onGetOpenSeaReceived, this::manageError);
    }

    private void onGetOpenSeaReceived(ListResult<OpenSea> openSeaListResult) {
        mSelectListView.hideLoadingView();
        if (openSeaListResult.isSucceed()) {
            List<OpenSea> openSeas = openSeaListResult.getData();
            ArrayList<SelectContent> selectContents = new ArrayList<>();
            for (int i = 0; i < openSeas.size(); i++) {
                selectContents.add(new SelectContent(openSeas.get(i).getOpenSeaNo(),
                        openSeas.get(i).getOpenSeaName(), null));
            }
            mSelectListView.onGetListSuccess(selectContents);
        }
    }
}
