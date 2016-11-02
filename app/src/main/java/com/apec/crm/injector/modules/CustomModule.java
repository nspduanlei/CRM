package com.apec.crm.injector.modules;

import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.custom.AddContactUseCase;
import com.apec.crm.domin.useCase.custom.AddCustomUseCase;
import com.apec.crm.domin.useCase.custom.DelContactUseCase;
import com.apec.crm.domin.useCase.custom.DeleteCustomUseCase;
import com.apec.crm.domin.useCase.custom.GetContactUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomDetailUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.custom.GetOpenSeaUseCase;
import com.apec.crm.domin.useCase.custom.ReturnPoolUseCase;
import com.apec.crm.domin.useCase.custom.UpdateContactUseCase;
import com.apec.crm.domin.useCase.custom.UpdateCustomUseCase;
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

    @Provides
    @Activity
    GetOpenSeaUseCase provideGetOpenSeaUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetOpenSeaUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetCustomDetailUseCase provideGetCustomDetailUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetCustomDetailUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    AddContactUseCase provideAddContactUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new AddContactUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    DelContactUseCase provideDelContactUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new DelContactUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    UpdateContactUseCase provideUpdateContactUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new UpdateContactUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    UpdateCustomUseCase provideUpdateCustomUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new UpdateCustomUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    ReturnPoolUseCase provideReturnPoolUseCase(
                    GoodsRepository repository,
                    @Named("ui_thread") Scheduler uiThread,
                    @Named("executor_thread") Scheduler executorThread,
                    @Named("gson") Gson gson) {
        return new ReturnPoolUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    DeleteCustomUseCase provideDeleteCustomUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new DeleteCustomUseCase(repository, uiThread, executorThread, gson);
    }

}
