package com.apec.crm.injector.modules;

import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.sys.GetNewVersionUseCase;
import com.apec.crm.domin.useCase.user.GetMyCountUseCase;
import com.apec.crm.domin.useCase.user.GetUserInfoUseCase;
import com.apec.crm.domin.useCase.user.GetUserListUseCase;
import com.apec.crm.domin.useCase.user.LoginUseCase;
import com.apec.crm.domin.useCase.user.ModifyPasswordUseCase;
import com.apec.crm.domin.useCase.user.UploadHeaderUseCase;
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
public class UserModule {
    @Provides
    @Activity
    LoginUseCase provideLoginUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new LoginUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetMyCountUseCase provideGetMyCountUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetMyCountUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetUserInfoUseCase provideGetUserInfoUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetUserInfoUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    ModifyPasswordUseCase provideModifyPasswordUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new ModifyPasswordUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    GetNewVersionUseCase provideGetNewVersionUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new GetNewVersionUseCase(repository, uiThread, executorThread);
    }

    @Provides
    @Activity
    GetUserListUseCase provideGetUserListUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread,
            @Named("gson") Gson gson) {
        return new GetUserListUseCase(repository, uiThread, executorThread, gson);
    }

    @Provides
    @Activity
    UploadHeaderUseCase provideUploadHeaderUseCase(
            GoodsRepository repository,
            @Named("ui_thread") Scheduler uiThread,
            @Named("executor_thread") Scheduler executorThread) {
        return new UploadHeaderUseCase(repository, uiThread, executorThread);
    }


}
