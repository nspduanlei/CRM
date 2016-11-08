package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.VisitsFilter;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/28.
 */

public class FilterVisitActivity extends BaseActivity {

    private VisitsFilter mVisitsFilter;

    public static final String RET_FILTER = "ret_filter";
    public static final String ARG_FILTER = "arg_filter";

    @BindView(R.id.tv_custom)
    TextView mTvCustom;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_filter_visit, R.string.filter_visit_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mVisitsFilter = getIntent().getParcelableExtra(ARG_FILTER);
        if (mVisitsFilter == null) {
            mVisitsFilter = new VisitsFilter();
        }
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    /**
     * 选择客户
     * @param view
     */
    @OnClick(R.id.fl_custom)
    void onCustomClicked(View view) {
        Intent intent = new Intent(this, SearchCustomActivity.class);
        intent.putExtra(SearchCustomActivity.FROM_TYPE, SearchCustomActivity.SEARCH_CUSTOM);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_CUSTOM);
    }

    /**
     * 选择用户
     * @param view
     */
    @OnClick(R.id.fl_user)
    void onUserClicked(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_USER);
    }

    /**
     * 重置
     * @param view
     */
    @OnClick(R.id.btn_reset)
    void onResetClicked(View view) {
        mVisitsFilter = new VisitsFilter();
    }

    /**
     * 确定
     * @param view
     */
    @OnClick(R.id.btn_sure)
    void onSureClicked(View view) {
        setResult(Constants.RESULT_CODE_FILTER_VISIT,
                getIntent().putExtra(RET_FILTER, mVisitsFilter));
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_SELECT_CUSTOM) {
            if (resultCode == Constants.RESULT_CODE_SELECT_CUSTOM) {
                Custom custom = data.getParcelableExtra(SearchCustomActivity.ARG_CUSTOM);
                mTvCustom.setText(custom.getCustomerName());
                mVisitsFilter.setCustomNo(custom.getId());
                mVisitsFilter.setCustomName(custom.getCustomerName());
            }
        }
    }
}
