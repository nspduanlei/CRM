package com.apec.crm.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/25.
 */

public class SearchUserActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText mEtContent;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_user, -1, MODE_NONE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }


    @OnClick(R.id.iv_clear_text)
    void onClearTextClicked(View view) {
        mEtContent.setText("");
    }

    @OnClick(R.id.tv_exit_search)
    void onExitSearchClicked(View view) {
        this.finish();
    }
}
