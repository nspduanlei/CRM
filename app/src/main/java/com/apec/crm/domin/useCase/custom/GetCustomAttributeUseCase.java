package com.apec.crm.domin.useCase.custom;

import com.apec.crm.domin.entities.SelectContent;
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
 * 获取客户属性列表
 */
public class GetCustomAttributeUseCase extends UseCase<ListResult<SelectContent>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final Gson mGson;

    RequestBody mRequestBody;

    @Inject
    public GetCustomAttributeUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread,
                                @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(String paramType) {
        Map param =  new HashMap<String, String>();
        param.put("paramType", paramType);

        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                mGson.toJson(param));
    }

    @Override
    public Observable<ListResult<SelectContent>> buildObservable() {
        return mRepository.getCustomAttribute(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
