package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.CustomDetailPresenter;
import com.apec.crm.mvp.views.CustomDetailView;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.NoScrollListView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/20.
 */

public class CustomDetailActivity extends BaseActivity implements CustomDetailView {

    @BindView(R.id.lv_contact_list)
    NoScrollListView mLVContactList;

    @Inject
    CustomDetailPresenter mCustomDetailPresenter;

    public static final String ARG_CUSTOM_ID = "arg_custom_id";

    @BindView(R.id.tv_custom_name)
    TextView mTvCustomName;
    @BindView(R.id.tv_custom_type)
    TextView mTvCustomType;
    @BindView(R.id.tv_custom_address)
    TextView mTvCustomAddress;
    @BindView(R.id.tv_custom_location)
    TextView mTvCustomLocation;
    @BindView(R.id.tv_custom_class)
    TextView mTvCustomClass;
    @BindView(R.id.tv_custom_state)
    TextView mTvCustomState;
    @BindView(R.id.tv_custom_source)
    TextView mTvCustomSource;
    @BindView(R.id.tv_custom_area)
    TextView mTvCustomArea;
    @BindView(R.id.tv_custom_car)
    TextView mTvCustomCar;
    @BindView(R.id.tv_custom_time)
    TextView mTvCustomTime;
    @BindView(R.id.tv_custom_cate)
    TextView mTvCustomCate;
    @BindView(R.id.tv_custom_belong)
    TextView mTvCustomBelong;
    @BindView(R.id.tv_custom_create)
    TextView mTvCustomCreate;

    private String mCustomId;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    CommonAdapter<Contact> mContactAdapter;

    CustomDetail mCustomDetail;

    List<Contact> mContacts = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_detail, R.string.custom_detail_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

        mCustomId = getIntent().getStringExtra(ARG_CUSTOM_ID);

        mContactAdapter = new CommonAdapter<Contact>(this, new ArrayList<>(),
                R.layout.item_contact, mLVContactList) {
            @Override
            public void convert(MyViewHolder holder, Contact contact) {
                holder.setText(R.id.tv_id, contact.getContactPost())
                        .setText(R.id.tv_nick, contact.getContactName());
            }
        };

        mLVContactList.setAdapter(mContactAdapter);

        mContactAdapter.setOnItemClickListener(data -> {
            Intent intent = new Intent(CustomDetailActivity.this, ContactActivity.class);
            intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_EDIT_SAVE);
            intent.putExtra(ContactActivity.ARG_CONTACT, data);
            startActivity(intent);
        });

        mCustomDetailPresenter.getCustomDetail(mCustomId);
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
        mCustomDetailPresenter.attachView(this);
        mCustomDetailPresenter.onCreate();
    }

    /**
     * 编辑更多资料
     *
     * @param view
     */
    @OnClick(R.id.fl_more_data)
    void onMoreDataClicked(View view) {
        if (mCustomDetail != null) {
            Intent intent = new Intent(this, CustomMoreDataActivity.class);
            intent.putExtra(CustomMoreDataActivity.ARG_CUSTOM, mCustomDetail);
            startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
        }
    }

    /**
     * 编辑基本资料
     *
     * @param view
     */
    @OnClick(R.id.fl_base_data)
    void onBaseDataClicked(View view) {
        if (mCustomDetail != null) {
            Intent intent = new Intent(this, CustomEditActivity.class);
            intent.putExtra(CustomEditActivity.ARG_CUSTOM, mCustomDetail);
            startActivityForResult(intent, Constants.REQUEST_CODE_CUSTOM_BASE);
        }
    }

    /**
     * 添加联系人
     *
     * @param view
     */
    @OnClick(R.id.tv_add_contact)
    void onAddContactClicked(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CONTACT);
    }

    @Override
    public void getCustomDetailSuccess(CustomDetail customDetail) {
        mCustomDetail = customDetail;

        setBaseInfo();
        setMoreInfo();
        mContacts = mCustomDetail.getContacts();
        setContacts();
    }

    public void setBaseInfo() {
        mTvCustomName.setText(mCustomDetail.getCustomerName());
        mTvCustomType.setText(mCustomDetail.getCustomerTypeName());
        mTvCustomAddress.setText(mCustomDetail.getAddress().getProvinceName() +
                mCustomDetail.getAddress().getCityName() +
                mCustomDetail.getAddress().getAreaName() +
                mCustomDetail.getAddress().getRodeName());
        mTvCustomLocation.setText(mCustomDetail.getAddress().getLocationDes());
    }

    public void setMoreInfo() {
        mTvCustomCate.setText(mCustomDetail.getClassName());
        mTvCustomState.setText(mCustomDetail.getCustomerStateName());
        mTvCustomSource.setText(mCustomDetail.getSourceName());
        mTvCustomArea.setText(mCustomDetail.getAreaName());
        mTvCustomCar.setText(mCustomDetail.getIsSpeedInto());
        mTvCustomTime.setText(mCustomDetail.getBusinessHours());
        mTvCustomCreate.setText(DateUtil.getDateFormatStr(Long.valueOf(mCustomDetail.getCreateDate()),
                "yyyy-MM-dd"));
        mTvCustomBelong.setText(mCustomDetail.getUserName());
        mTvCustomClass.setText(mCustomDetail.getCustomerLevelName());

    }

    public void setContacts() {
        //联系人
        mContactAdapter.clear();
        mContactAdapter.addAll(mContacts);
    }

    @Override
    public void updateCustomSuccess() {
        T.showShort(this, "更新客户信息成功");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_MORE_DATA) { //更多资料返回
            if (resultCode == Constants.RESULT_CODE_MORE_DATA) {
                mCustomDetail =
                        data.getParcelableExtra(CustomMoreDataActivity.ARG_RESULT);
                setMoreInfo();
            }
        } else if (requestCode == Constants.REQUEST_CODE_ADD_CONTACT) { //添加联系人
            if (resultCode == Constants.RESULT_CODE_ADD_CONTACT) {
                Contact contact =
                        data.getParcelableExtra(ContactActivity.ARG_CONTACT);
                mContacts.add(contact);
                setContacts();
            }
        } else if (requestCode == Constants.REQUEST_CODE_CUSTOM_BASE) { //更多资料返回
            if (resultCode == Constants.RESULT_CODE_CUSTOM_BASE) {
                mCustomDetail =
                        data.getParcelableExtra(CustomMoreDataActivity.ARG_RESULT);
                setMoreInfo();
            }
        }
    }
}
