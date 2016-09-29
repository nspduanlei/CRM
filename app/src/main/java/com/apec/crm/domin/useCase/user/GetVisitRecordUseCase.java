package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.RecordFilter;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 */

public class GetVisitRecordUseCase extends UseCase<Result<ListPage<VisitRecord>>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final Gson mGson;

    String mJsonString;

    @Inject
    public GetVisitRecordUseCase(GoodsRepository repository,
                                 @Named("ui_thread") Scheduler uiThread,
                                 @Named("executor_thread") Scheduler executorThread,
                                 @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(RecordFilter filter) {
        mJsonString = mGson.toJson(filter);

    }

    @Override
    public Observable<Result<ListPage<VisitRecord>>> buildObservable() {

        return mRepository.getVisitRecord(mJsonString)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
