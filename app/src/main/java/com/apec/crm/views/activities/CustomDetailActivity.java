package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.CustomFragment;
import com.apec.crm.views.widget.NoScrollListView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.apec.crm.config.Constants.REQUEST_CODE_ADD_CONTACT;

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

    @BindView(R.id.iv_edit_base)
    ImageView mIvEditBase;
    @BindView(R.id.iv_edit_more)
    ImageView mIvEditMore;
    @BindView(R.id.tv_add_contact)
    TextView mTvAddContact;

    private String mCustomId;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    CommonAdapter<Contact> mContactAdapter;

    CustomDetail mCustomDetail;

    List<Contact> mContacts = new ArrayList<>();

    //公海或私海
    public static final String ARG_TYPE = "arg_type";
    private int mType;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_detail, R.string.custom_detail_title);

        mType = getIntent().getIntExtra(ARG_TYPE, CustomFragment.TYPE_PRIVATE);
        mCustomId = getIntent().getStringExtra(ARG_CUSTOM_ID);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        //公海客户不能修改
        if (mType == CustomFragment.TYPE_PUBLIC) {
            mIvEditBase.setVisibility(View.GONE);
            mIvEditMore.setVisibility(View.GONE);
            mTvAddContact.setVisibility(View.GONE);
        }

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
            if (mType == CustomFragment.TYPE_PRIVATE) {
                Intent intent = new Intent(CustomDetailActivity.this, ContactActivity.class);
                intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_EDIT_SAVE);
                intent.putExtra(ContactActivity.ARG_CUSTOM_ID, mCustomDetail.getId());
                intent.putExtra(ContactActivity.ARG_CONTACT, data);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CONTACT);
            }
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
        if (mCustomDetail != null && mType == CustomFragment.TYPE_PRIVATE) {
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
        if (mCustomDetail != null && mType == CustomFragment.TYPE_PRIVATE) {
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
        intent.putExtra(ContactActivity.ARG_CUSTOM_ID, mCustomDetail.getId());
        intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_ADD_SAVE);
        startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
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
//        mTvCustomCreate.setText(DateUtil.getDateFormatStr(Long.valueOf(mCustomDetail.getCreateDate()),
//                "yyyy-MM-dd"));
        mTvCustomCreate.setText(mCustomDetail.getCreateDate());
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
                mCustomDetailPresenter.updateCustom(mCustomDetail);
            }
        } else if (requestCode == REQUEST_CODE_ADD_CONTACT) { //联系人

            switch (resultCode) {
                case Constants.RESULT_CODE_ADD_CONTACT: //添加联系人
                case Constants.RESULT_CODE_UPDATE_CONTACT: //修改联系人
                case Constants.RESULT_CODE_DELETE_CONTACT: //删除联系人

                    mCustomDetailPresenter.getCustomDetail(mCustomId);

                    break;
            }
        } else if (requestCode == Constants.REQUEST_CODE_CUSTOM_BASE) { //基本资料返回
            if (resultCode == Constants.RESULT_CODE_CUSTOM_BASE) {
                mCustomDetail =
                        data.getParcelableExtra(CustomEditActivity.ARG_CUSTOM);
                setBaseInfo();
                mCustomDetailPresenter.updateCustom(mCustomDetail);
            }
        }
    }
}
