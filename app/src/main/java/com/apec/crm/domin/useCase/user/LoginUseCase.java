package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 */

public class LoginUseCase extends UseCase<Result<User>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private String mUserName;
    private String mPassword;

    @Inject
    public LoginUseCase(GoodsRepository repository,
                        @Named("ui_thread") Scheduler uiThread,
                        @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;

    }

    public void setData(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    @Override
    public Observable<Result<User>> buildObservable() {
        return mRepository.login(mUserName, mPassword)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
