package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.user.GetMyCountUseCase;
import com.apec.crm.domin.useCase.user.GetUserInfoUseCase;
import com.apec.crm.domin.useCase.user.LoginUseCase;
import com.apec.crm.domin.useCase.user.ModifyPasswordUseCase;
import com.apec.crm.domin.useCase.visit.GetVisitsUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.UserModule;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.activities.ModifyPasswordActivity;
import com.apec.crm.views.activities.UserInfoActivity;
import com.apec.crm.views.fragments.ProfileFragment;
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
    void inject(UserInfoActivity userInfoActivity);
    void inject(ModifyPasswordActivity modifyPasswordActivity);
    void inject(ProfileFragment profileFragment);

    //登录
    LoginUseCase loginUseCase();
    //获取我本月的统计数据
    GetMyCountUseCase getMyCountUseCase();
    //获取用户数据
    GetUserInfoUseCase getUserInfoUserCase();
    //修改密码
    ModifyPasswordUseCase modifyPasswordUeCase();
    //获取版本信息
    GetVisitsUseCase getVisitsUseCase();

}
