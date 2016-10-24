package com.apec.crm.injector.modules;

import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.custom.AddCustomUseCase;
import com.apec.crm.domin.useCase.custom.GetContactUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.sys.GetAreaUseCase;
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
public class CustomModule {

    @Provides
    @Activity
    GetCustomListUseCase provideGetCustomListUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetCustomListUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetAreaUseCase provideGetAreaUseCase(
                    GoodsRepository repository,
                    @Named("ui_thread") Scheduler uiThread,
                    @Named("executor_thread") Scheduler executorThread,
                    @Named("gson") Gson gson) {
        return new GetAreaUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetCustomAttributeUseCase provideGetCustomAttributeUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetCustomAttributeUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    AddCustomUseCase provideAddCustomUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new AddCustomUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetContactUseCase provideGetContactUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetContactUseCase(repository, uiThread, executorThread, gson);
    }



}
