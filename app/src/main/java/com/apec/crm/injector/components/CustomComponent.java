package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.CustomModule;
import com.apec.crm.views.fragments.CustomListFragment;

import dagger.Component;

/**
 * Created by duanlei on 16/9/27.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {CustomModule.class, ActivityModule.class})
public interface CustomComponent extends ActivityComponent {

    void inject(CustomListFragment customListFragment);

    //获取客户列表
    GetCustomListUseCase getCustomListUseCase();

}
