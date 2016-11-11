package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;
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
 * 获取下属用户
 */

public class GetUserListUseCase extends UseCase<Result<ListPage<User>>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final Gson mGson;
    RequestBody mRequestBody;

    @Inject
    public GetUserListUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread,
                              @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(String keywords) {
        Map param =  new HashMap<String, Object>();
        param.put("keywords", keywords);
        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),mGson.toJson(param));
    }

    @Override
    public Observable<Result<ListPage<User>>> buildObservable() {
        return mRepository.getUserList(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
