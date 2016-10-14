package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.User;
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

public class LoginUseCase extends UseCase<Result<User>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final Gson mGson;
    RequestBody mRequestBody;

    @Inject
    public LoginUseCase(GoodsRepository repository,
                        @Named("ui_thread") Scheduler uiThread,
                        @Named("executor_thread") Scheduler executorThread,
                        @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        mRequestBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),mGson.toJson(user));
    }

    @Override
    public Observable<Result<User>> buildObservable() {
        return mRepository.login(mRequestBody)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
