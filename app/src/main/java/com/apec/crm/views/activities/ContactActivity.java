package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.ContactPresenter;
import com.apec.crm.mvp.views.ContactView;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/14.
 */

public class ContactActivity extends BaseActivity implements ContactView {

    public static final int TYPE_ADD = 0; //添加联系人，数据返回
    public static final int TYPE_ADD_SAVE = 1; //添加联系人，在本页保存
    public static final int TYPE_SHOW = 2;
    public static final int TYPE_EDIT = 3;
    public static final int TYPE_EDIT_SAVE = 4;

    public static final String ARG_TYPE = "arg_type";
    public static final String ARG_CONTACT = "arg_contact";
    public static final String ARG_CUSTOM_ID = "arg_custom_id";

    @BindView(R.id.et_contact_name)
    EditText mEtContactName;
    @BindView(R.id.tv_custom)
    TextView mTvCustom;
    @BindView(R.id.et_position)
    EditText mEtPosition;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_tel)
    EditText mEtTel;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_contact_age)
    TextView mTvContactAge;

    @BindView(R.id.fl_custom)
    FrameLayout mFLCustom;
    @BindView(R.id.custom_line)
    View mCustomLine;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    private int mType;
    String mSex, mAge;

    @Inject
    ContactPresenter mContactPresenter;

    String mCustomId;
    Contact mContact;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_contact);
        setMenuText("保存", v -> {
            saveData();
        });

        mType = getIntent().getIntExtra(ARG_TYPE, 0);

        switch (mType) {
            case TYPE_ADD:
                mFLCustom.setVisibility(View.GONE);
                mCustomLine.setVisibility(View.GONE);
            case TYPE_ADD_SAVE:
                setUpTitle("添加联系人");
                break;

            case TYPE_EDIT:
                mFLCustom.setVisibility(View.GONE);
                mCustomLine.setVisibility(View.GONE);
            case TYPE_EDIT_SAVE:
                setUpTitle("联系人详情");
                //删除联系人
                setBtnImage(R.drawable.nav_delete_drawable, v -> {
                    if (mType == TYPE_EDIT) {
                        setResult(Constants.RESULT_CODE_DELETE_CONTACT,
                                getIntent().putExtra(ARG_CONTACT, mContact.getId()));
                        this.finish();
                    } else {
                        mContactPresenter.delContact(mContact.getId());
                    }
                });
                break;
            case TYPE_SHOW:
                setUpTitle("联系人详情");
                break;
        }

        mContact = getIntent().getParcelableExtra(ARG_CONTACT);
        if (mContact == null) {
            mContact = new Contact();
        } else {
            showContact();
        }

        mCustomId = getIntent().getStringExtra(ARG_CUSTOM_ID);
        if (mCustomId != null) {
            mFLCustom.setVisibility(View.GONE);
            mCustomLine.setVisibility(View.GONE);
            mContact.setCustomerNo(mCustomId);
        }
    }

    public void showContact() {
        mEtContactName.setText(mContact.getContactName());
        mEtPhone.setText(mContact.getContactPhone());
        mEtPosition.setText(mContact.getContactPost());
        mEtTel.setText(mContact.getContactTel());
        mTvContactAge.setText(mContact.getContactAge());
        mTvSex.setText(mContact.getContactSex());
    }

    public void saveData() {
        String name = mEtContactName.getText().toString();
        String post = mEtPosition.getText().toString();
        String phone = mEtPhone.getText().toString();

        String msgPhone = StringUtils.checkMobile(phone);

        if (StringUtils.isNullOrEmpty(name)) {
            T.showShort(this, "姓名不能为空");
        } else if (StringUtils.isNullOrEmpty(post)) {
            T.showShort(this, "职位不能为空");
        } else if (!msgPhone.equals("")) {
            T.showShort(this, msgPhone);
        } else {
            mContact.setContactName(name);
            mContact.setContactPhone(phone);
            mContact.setContactPost(post);

            if (mEtTel.getText().length() != 0) {
                mContact.setContactTel(mEtTel.getText().toString());
            }
            if (mSex != null) {
                mContact.setContactTel(mSex);
            }
            if (mAge != null) {
                mContact.setContactTel(mAge);
            }

            switch (mType) {
                case TYPE_ADD:
                    setResult(Constants.RESULT_CODE_ADD_CONTACT,
                            getIntent().putExtra(ARG_CONTACT, mContact));
                    this.finish();
                    break;
                case TYPE_EDIT:
                    setResult(Constants.RESULT_CODE_EDIT_CONTACT,
                            getIntent().putExtra(ARG_CONTACT, mContact));
                    this.finish();
                    break;
                case TYPE_ADD_SAVE:
                    mContactPresenter.addContact(mContact);
                    break;

                case TYPE_EDIT_SAVE:
                    mContactPresenter.updateContact(mContact);
                    break;
            }
        }
    }


    @Override
    protected void initUi(Bundle savedInstanceState) {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerCustomComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mContactPresenter.attachView(this);
        mContactPresenter.onCreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mContactPresenter.onStop();
    }

    /**
     * 选择性别
     *
     * @param view
     */
    @OnClick(R.id.fl_sex)
    void onSexClicked(View view) {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(0, "男"));
        menuEntities.add(new MenuEntity(1, "女"));

        MyUtils.showListDialog(this, menuEntities, (dialog, item, view1, position) -> {
            mTvSex.setText(menuEntities.get(position).getName());

            dialog.dismiss();
        });
    }

    /**
     * 选择年龄
     *
     * @param view
     */
    @OnClick(R.id.fl_age)
    void onAgeClicked(View view) {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(0, "20岁以下"));
        menuEntities.add(new MenuEntity(1, "21-30"));
        menuEntities.add(new MenuEntity(2, "31-40"));
        menuEntities.add(new MenuEntity(3, "41-50"));
        menuEntities.add(new MenuEntity(4, "51以上"));

        MyUtils.showListDialog(this, menuEntities, (dialog, item, view1, position) -> {
            mTvContactAge.setText(menuEntities.get(position).getName());

            dialog.dismiss();
        });

    }

    /**
     * 选择客户
     *
     * @param view
     */
    @OnClick(R.id.fl_custom)
    void onCustomClicked(View view) {
        Intent intent = new Intent(this, SearchCustomActivity.class);
        intent.putExtra(SearchCustomActivity.FROM_TYPE, SearchCustomActivity.SEARCH_CUSTOM);
        startActivity(intent);
    }

    @Override
    public void addContactSuccess() {
        T.showShort(this, "添加联系人成功");
        setResult(Constants.RESULT_CODE_ADD_CONTACT,
                getIntent().putExtra(ARG_CONTACT, mContact));
        this.finish();
    }

    @Override
    public void delContactSuccess() {
        T.showShort(this, "删除联系人成功");
        setResult(Constants.RESULT_CODE_DELETE_CONTACT,
                getIntent().putExtra(ARG_CONTACT, mContact.getId()));
        this.finish();
    }

    @Override
    public void updateContactSuccess() {
        T.showShort(this, "更新联系人成功");
        setResult(Constants.RESULT_CODE_UPDATE_CONTACT,
                getIntent().putExtra(ARG_CONTACT, mContact));
        this.finish();
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
