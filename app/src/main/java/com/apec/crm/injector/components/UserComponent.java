package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.user.GetMyCountUseCase;
import com.apec.crm.domin.useCase.user.LoginUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.UserModule;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.fragments.WorkPlaceFragment;

import dagger.Component;

/**
 * Created by duanlei on 16/9/27.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {UserModule.class, ActivityModule.class})
public interface UserComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);
    void inject(WorkPlaceFragment workPlaceFragment);

    //登录
    LoginUseCase loginUseCase();
    //获取我本月的统计数据
    GetMyCountUseCase getMyCountUseCase();

}
