package com.apec.crm.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.User;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.SearchUserPresenter;
import com.apec.crm.mvp.views.SearchUserView;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.recyclerView.MyViewHolder;
import com.apec.crm.views.widget.recyclerView.section.CommonSimpleSectionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/25.
 */
public class SearchUserActivity extends BaseActivity
        implements SearchUserView {

    @BindView(R.id.et_content)
    EditText mEtContent;

    @BindView(R.id.rv_user)
    RecyclerView mRvUser;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @BindView(R.id.iv_clear_text)
    ImageView mIvClearText;

    @BindView(R.id.fl_empty)
    FrameLayout mEmpty;

    @Inject
    SearchUserPresenter mPresenter;

    CommonSimpleSectionAdapter mAdapter;

    public static final String ARG_USER = "arg_user";

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search_user, -1, MODE_NONE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mEtContent.addTextChangedListener(new EditChangeListener());

        mEtContent.setOnEditorActionListener((v, actionId, event) -> {
            String text = mEtContent.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !StringUtils.isNullOrEmpty(text)) {
                mPresenter.getUserList(text);
            }
            return false;
        });
        setupRecycler();
    }

    private void setupRecycler() {
        mAdapter = new CommonSimpleSectionAdapter<User>(this, R.layout.view_agenda_item) {
            @Override
            public void convert(MyViewHolder holder, User user, int position) {
                holder.setText(R.id.title, user.getRealName())
                        .setRoundImageUrl(R.id.imageView, user.getImg());

                holder.setOnItemClickListener(v -> {
                    setResult(Constants.RESULT_CODE_SELECT_USER,
                            getIntent().putExtra(ARG_USER, user));
                    SearchUserActivity.this.finish();
                });
            }
        };
        mRvUser.setAdapter(mAdapter);
        mRvUser.setLayoutManager(new LinearLayoutManager(this));
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
        mPresenter.onCreate();
    }

    @OnClick(R.id.iv_clear_text)
    void onClearTextClicked(View view) {
        mEtContent.setText("");
    }

    @OnClick(R.id.tv_exit_search)
    void onExitSearchClicked(View view) {
        this.finish();
    }

    @Override
    public void onSearchSuccess(List<User> users) {
        if (users.size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            mEmpty.setVisibility(View.GONE);
        }
        sortDepUser(users);
    }

    private void sortDepUser(List<User> users) {
        Collections.sort(users);

        String currDepNo = "";
        List<User> currUser = new ArrayList<>();

        List<String> mSections = new ArrayList<>();
        List<List<User>> mListUsers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (currDepNo.equals(users.get(i).getDepNo())) {
                currUser.add(users.get(i));
            } else {
                if (currUser.size() != 0) {
                    mListUsers.add(currUser);
                }
                currDepNo = users.get(i).getDepNo();

                currUser = new ArrayList<>();
                currUser.add(users.get(i));

                mSections.add(users.get(i).getDepName());
            }
        }
        mListUsers.add(currUser);

        mAdapter.notifyData(mSections, mListUsers);
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

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mIvClearText.setVisibility(View.GONE);
            } else {
                mIvClearText.setVisibility(View.VISIBLE);
            }
        }
    }
}
