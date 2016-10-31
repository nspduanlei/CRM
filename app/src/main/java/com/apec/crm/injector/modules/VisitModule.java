package com.apec.crm.injector.modules;

import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.visit.AddVisitUseCase;
import com.apec.crm.domin.useCase.visit.GetVisitsUseCase;
import com.apec.crm.injector.Activity;
import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 */
@Module
public class VisitModule {

    @Provides
    @Activity
    GetVisitsUseCase provideGetVisitsUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetVisitsUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    AddVisitUseCase provideAddVisitUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new AddVisitUseCase(repository, uiThread, executorThread, gson);
    }

}
