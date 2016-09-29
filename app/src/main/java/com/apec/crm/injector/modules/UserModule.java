package com.apec.crm.injector.modules;

import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.user.LoginUseCase;
import com.apec.crm.injector.Activity;

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
            @Named("executor_thread") Scheduler executorThread) {
        return new LoginUseCase(repository, uiThread, executorThread);
    }


}
