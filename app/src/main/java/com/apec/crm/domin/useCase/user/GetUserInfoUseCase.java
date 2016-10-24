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
 * 获取我当月的统计数据
 */

public class GetUserInfoUseCase extends UseCase<Result<User>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public GetUserInfoUseCase(GoodsRepository repository,
                              @Named("ui_thread") Scheduler uiThread,
                              @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<Result<User>> buildObservable() {

        return mRepository.getUserInfo()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
