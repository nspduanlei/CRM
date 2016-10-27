package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.custom.AddContactUseCase;
import com.apec.crm.domin.useCase.custom.AddCustomUseCase;
import com.apec.crm.domin.useCase.custom.DelContactUseCase;
import com.apec.crm.domin.useCase.custom.GetContactUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomAttributeUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomDetailUseCase;
import com.apec.crm.domin.useCase.custom.GetCustomListUseCase;
import com.apec.crm.domin.useCase.custom.GetOpenSeaUseCase;
import com.apec.crm.domin.useCase.custom.UpdateContactUseCase;
import com.apec.crm.domin.useCase.custom.UpdateCustomUseCase;
import com.apec.crm.domin.useCase.sys.GetAreaUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.CustomModule;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.ContactActivity;
import com.apec.crm.views.activities.CustomDetailActivity;
import com.apec.crm.views.activities.FilterCustomActivity;
import com.apec.crm.views.activities.SearchCustomActivity;
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
    void inject(SearchCustomActivity searchCustomActivity);
    void inject(FilterCustomActivity filterCustomActivity);
    void inject(CustomDetailActivity customDetailActivity);
    void inject(ContactActivity contactActivity);

    //获取客户列表
    GetCustomListUseCase getCustomListUseCase();

    //添加客户
    AddCustomUseCase addCustomUseCase();

    //选择行政区域
    GetAreaUseCase getAreaUseCase();

    //选择客户属性
    GetCustomAttributeUseCase getCustomAttributeUseCase();

    //获取客户联系人
    GetContactUseCase getContactUseCase();

    //获取片区
    GetOpenSeaUseCase getOpenSeaUseCase();

    //获取客户详情
    GetCustomDetailUseCase getCustomDetailUseCase();

    //添加联系人
    AddContactUseCase addContactUseCase();

    //删除联系人
    DelContactUseCase delContactUse();

    //删除联系人
    UpdateContactUseCase updateContactUseCase();

    //更新客户
    UpdateCustomUseCase updateCustomUseCase();

}
