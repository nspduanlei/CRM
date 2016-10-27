package com.apec.crm.domin.useCase.custom;

import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 * 更新联系人信息
 */

public class UpdateContactUseCase extends UseCase<Result> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final Gson mGson;

    RequestBody mRequestBody;

    @Inject
    public UpdateContactUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread,
                                @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(Contact contact) {
        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(contact));
    }

    @Override
    public Observable<Result> buildObservable() {
        return mRepository.updateContact(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
