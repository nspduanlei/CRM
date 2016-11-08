package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.AddContactUseCase;
import com.apec.crm.domin.useCase.custom.DelContactUseCase;
import com.apec.crm.domin.useCase.custom.UpdateContactUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.ContactView;
import com.apec.crm.mvp.views.core.View;
import com.apec.crm.utils.MyUtils;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by duanlei on 2016/10/26.
 */

public class ContactPresenter implements Presenter {

    AddContactUseCase mAddContactUseCase;
    DelContactUseCase mDelContactUseCase;
    UpdateContactUseCase mUpdateContactUseCase;

    ContactView mContactView;

    Subscription mAddSubscription, mDelSubscription, mUpdateSubscription;

    @Inject
    public ContactPresenter(AddContactUseCase addContactUseCase,
                            DelContactUseCase delContactUseCase,
                            UpdateContactUseCase updateContactUseCase) {
        mAddContactUseCase = addContactUseCase;
        mDelContactUseCase = delContactUseCase;
        mUpdateContactUseCase = updateContactUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        MyUtils.cancelSubscribe(mAddSubscription, mDelSubscription,
                mUpdateSubscription);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        mContactView = (ContactView) v;
    }

    @Override
    public void onCreate() {

    }

    /**
     * 添加联系人
     * @param contact
     */
    public void addContact(Contact contact) {
        mContactView.showLoadingView();

        mAddContactUseCase.setData(contact);
        mAddSubscription = mAddContactUseCase.execute()
                .subscribe(this::onAddReceived, this::manageError);
    }

    private void manageError(Throwable throwable) {
        mContactView.hideLoadingView();
    }

    private void onAddReceived(Result result) {
        mContactView.hideLoadingView();
        if (result.isSucceed()) {
            mContactView.addContactSuccess();
        }
    }

    /**
     * 删除联系人
     * @param id
     */
    public void delContact(String id) {
        mContactView.showLoadingView();

        mDelContactUseCase.setData(id);
        mDelSubscription = mDelContactUseCase.execute()
                .subscribe(this::onDelReceived, this::manageError);
    }

    private void onDelReceived(Result result) {
        mContactView.hideLoadingView();

        if (result.isSucceed()) {
            mContactView.delContactSuccess();
        }
    }

    /**
     * 更新联系人
     * @param contact
     */
    public void updateContact(Contact contact) {
        mContactView.showLoadingView();

        mUpdateContactUseCase.setData(contact);
        mUpdateSubscription = mUpdateContactUseCase.execute()
                .subscribe(this::onUpdateReceived, this::manageError);

    }

    private void onUpdateReceived(Result result) {
        mContactView.hideLoadingView();

        if (result.isSucceed()) {
            mContactView.updateContactSuccess();
        }
    }
}
