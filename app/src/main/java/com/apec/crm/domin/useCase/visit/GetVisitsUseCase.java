package com.apec.crm.domin.useCase.visit;

import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.domin.entities.VisitRecord;
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
 * 获取用户拜访记录 更具用户id
 */

public class GetVisitsUseCase extends UseCase<Result<ListPage<VisitRecord>>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final Gson mGson;

    RequestBody mRequestBody;
    /**
     * 0: 根据用户拜访记录
     * 1: 获取客户拜访记录
     */
    int mType;

    @Inject
    public GetVisitsUseCase(GoodsRepository repository,
                            @Named("ui_thread") Scheduler uiThread,
                            @Named("executor_thread") Scheduler executorThread,
                            @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(VisitRecordFilter filter) {

        if (filter.getCustomerNo() == null) {
            mType = 0;
        } else {
            mType = 1;
        }

        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(filter));
    }

    @Override
    public Observable<Result<ListPage<VisitRecord>>> buildObservable() {
        return mRepository.getVisits(mRequestBody, mType)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
