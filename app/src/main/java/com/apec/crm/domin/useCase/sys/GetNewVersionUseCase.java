package com.apec.crm.domin.useCase.sys;

import com.apec.crm.domin.entities.Version;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 * 获取最新版本信息
 */

public class GetNewVersionUseCase extends UseCase<Result<Version>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject
    public GetNewVersionUseCase(GoodsRepository repository,
                                @Named("ui_thread") Scheduler uiThread,
                                @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<Result<Version>> buildObservable() {

        return mRepository.getVersionInfo()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
