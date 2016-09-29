package com.apec.crm.injector.components;

import com.apec.crm.domin.useCase.user.GetVisitRecordUseCase;
import com.apec.crm.injector.Activity;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.injector.modules.VisitModule;
import com.apec.crm.views.fragments.VisitRecordFragment;

import dagger.Component;

/**
 * Created by duanlei on 16/9/27.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {VisitModule.class, ActivityModule.class})
public interface VisitComponent extends ActivityComponent {

    void inject(VisitRecordFragment visitRecordFragment);


    GetVisitRecordUseCase getVisitRecordUseCase();

}
