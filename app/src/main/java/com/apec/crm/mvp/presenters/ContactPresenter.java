package com.apec.crm.mvp.presenters;

import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.useCase.custom.AddContactUseCase;
import com.apec.crm.domin.useCase.custom.DelContactUseCase;
import com.apec.crm.domin.useCase.custom.UpdateContactUseCase;
import com.apec.crm.mvp.presenters.core.Presenter;
import com.apec.crm.mvp.views.ContactView;
import com.apec.crm.mvp.views.core.View;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/10/26.
 */

public class ContactPresenter implements Presenter {

    AddContactUseCase mAddContactUseCase;
    DelContactUseCase mDelContactUseCase;
    UpdateContactUseCase mUpdateContactUseCase;

    ContactView mContactView;

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

    public void addContact(Contact contact) {
        mContactView.showLoadingView();

        mAddContactUseCase.setData(contact);
        mAddContactUseCase.execute().subscribe(this::onAddReceived, this::manageError);
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

    public void delContact(String id) {
        mContactView.showLoadingView();

        mDelContactUseCase.setData(id);
        mDelContactUseCase.execute().subscribe(this::onDelReceived, this::manageError);
    }

    private void onDelReceived(Result result) {
        mContactView.hideLoadingView();

        if (result.isSucceed()) {
            mContactView.delContactSuccess();
        }
    }

    public void updateContact(Contact contact) {
        mContactView.showLoadingView();

        mUpdateContactUseCase.setData(contact);
        mUpdateContactUseCase.execute().subscribe(this::onUpdateReceived, this::manageError);

    }

    private void onUpdateReceived(Result result) {
        mContactView.hideLoadingView();

        if (result.isSucceed()) {
            mContactView.updateContactSuccess();
        }
    }
}
