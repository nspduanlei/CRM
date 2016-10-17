package com.apec.crm.domin.useCase.sys;

import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 * 获取我当月的统计数据
 */

public class GetAreaUseCase extends UseCase<ListResult<Area>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final Gson mGson;
    private RequestBody mRequestBody;

    @Inject
    public GetAreaUseCase(GoodsRepository repository,
                          @Named("ui_thread") Scheduler uiThread,
                          @Named("executor_thread") Scheduler executorThread,
                          @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(int id) {
        Map param =  new HashMap<String, Object>();
        param.put("id", id);

        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(param));
    }

    @Override
    public Observable<ListResult<Area>> buildObservable() {

        return mRepository.getArea(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
