package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.MyCount;
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

public class GetMyCountUseCase extends UseCase<Result<MyCount>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public GetMyCountUseCase(GoodsRepository repository,
                             @Named("ui_thread") Scheduler uiThread,
                             @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<Result<MyCount>> buildObservable() {

        return mRepository.getMyCount()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
