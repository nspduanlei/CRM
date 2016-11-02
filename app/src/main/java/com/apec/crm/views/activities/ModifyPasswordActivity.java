package com.apec.crm.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.ModifyPasswordPresenter;
import com.apec.crm.mvp.views.ModifyPasswordView;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by duanlei on 2016/10/13.
 * 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity implements ModifyPasswordView {

    @BindView(R.id.et_current_password)
    EditText mEtCurrentPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.et_sure_password)
    EditText mEtSurePassword;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Inject
    ModifyPasswordPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_modify_password, R.string.modify_password);
        setMenuText("保存", v -> {

            String currPassword = mEtCurrentPassword.getText().toString();
            String newPassword = mEtNewPassword.getText().toString();
            String surePassword = mEtSurePassword.getText().toString();

            String msg = StringUtils.checkPassword(newPassword);

            if (StringUtils.isNullOrEmpty(currPassword)) {
                T.showShort(this, "当前密码不能为空");
            } else if (!msg.equals("")) {
                T.showShort(this, msg);
            } else if (StringUtils.isNullOrEmpty(surePassword)) {
                T.showShort(this, "确认密码不能为空");
            } else if (!newPassword.equals(surePassword)) {
                T.showShort(this, "新密码两次输入不一致");
            } else {
                mPresenter.modifyPassword(currPassword, newPassword);
            }
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerUserComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
    }

    @Override
    public void onModifySuccess() {
        T.showShort(this, "修改密码成功");
    }

    @Override
    public void showLoadingView() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }
}
