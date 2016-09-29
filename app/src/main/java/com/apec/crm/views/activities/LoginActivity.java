package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.User;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.LoginPresenter;
import com.apec.crm.mvp.views.LoginView;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/27.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_user_name)
    EditText mEtUserName;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login, R.string.login_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

        //TODO  测试数据
        mEtUserName.setText("15507586666");
        mEtPassword.setText("256");

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
        mLoginPresenter.attachView(this);
        mLoginPresenter.onCreate();
    }

    @OnClick(R.id.btn_login)
    void onSubmitClicked(View view) {

        String userName = mEtUserName.getText().toString();
        String password = mEtPassword.getText().toString();

        if (StringUtils.isNullOrEmpty(userName)) {
            T.showShort(this, "用户名不能为空");
        } else if (StringUtils.isNullOrEmpty(password)) {
            T.showShort(this, "密码不能为空");
        } else {
            mLoginPresenter.login(userName, password);
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        SPUtils.put(this, SPUtils.USER_NAME, user.getRealName());
        SPUtils.put(this, SPUtils.TOKEN, user.getToken());
        SPUtils.put(this, SPUtils.USER_NO, user.getUserNo());

        T.showShort(this, "登录成功");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        this.finish();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError() {

    }
}
