package com.apec.crm.domin.useCase.custom;

import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.domin.entities.func.ListPage;
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
 */
public class GetCustomListUseCase extends UseCase<Result<ListPage<Custom>>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final Gson mGson;

    RequestBody mRequestBody;

    @Inject
    public GetCustomListUseCase(GoodsRepository repository,
                                     @Named("ui_thread") Scheduler uiThread,
                                     @Named("executor_thread") Scheduler executorThread,
                                     @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(VisitRecordFilter filter) {
        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(filter));
    }

    @Override
    public Observable<Result<ListPage<Custom>>> buildObservable() {
        return mRepository.getCustomerList(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
