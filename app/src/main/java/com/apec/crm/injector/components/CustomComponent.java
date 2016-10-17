package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.sys.GetAreaUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.CustomModule;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.SelectListActivity;
import com.apec.crm.views.fragments.CustomListFragment;

import dagger.Component;

/**
 * Created by duanlei on 16/9/27.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {CustomModule.class, ActivityModule.class})
public interface CustomComponent extends ActivityComponent {

    void inject(CustomListFragment customListFragment);
    void inject(AddCustomActivity addCustomActivity);
    void inject(SelectListActivity selectListActivity);

    //获取客户列表
    GetCustomListUseCase getCustomListUseCase();

    //添加客户
    AddCustomActivity addCustomActivity();

    //选择行政区域
    GetAreaUseCase getAreaUseCase();

    //选择客户属性
    GetCustomAttributeUseCase getCustomAttributeUseCase();

}
