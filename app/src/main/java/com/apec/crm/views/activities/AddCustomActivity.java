package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Address;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.AddCustomPresenter;
import com.apec.crm.mvp.views.AddCustomView;
import com.apec.crm.utils.KeyBoardUtils;
import com.apec.crm.utils.SelectCityUtil;
import com.apec.crm.utils.StringUtils;
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
 * Created by duanlei on 16/9/12.
 * 添加客户
 */
public class AddCustomActivity extends BaseActivity implements SelectCityUtil.SelectArea,
        AddCustomView {

    @Inject
    SelectCityUtil mSelectCityUtil;

    @BindView(R.id.et_custom_name)
    EditText mEtCustomName;
    @BindView(R.id.tv_custom_type)
    TextView mTvCustomType;
    @BindView(R.id.tv_address_pca)
    TextView mTvAddressPca;
    @BindView(R.id.et_road)
    EditText mEtRoad;
    @BindView(R.id.tv_location)
    TextView mTvLocation;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @BindView(R.id.lv_contact_list)
    NoScrollListView mLvContactList;
    CommonAdapter<Contact> mCommonAdapter;

    private Address mSelectAddress;
    private String mAreaId;
    private String mLocationDes;

    @Inject
    AddCustomPresenter mAddCustomPresenter;

    //当前选择经纬度
    LatLng mLatLng;

    CustomDetail mCustom = new CustomDetail();

    List<Contact> mContacts = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_custom, R.string.add_custom_title);
        setMenuText("保存", v -> {
            saveData();
        });
    }

    public void saveData() {
        String customName = mEtCustomName.getText().toString();
        String rodeName = mEtRoad.getText().toString();

        if (StringUtils.isNullOrEmpty(customName)) {
            T.showShort(this, "客户名称不能为空");
        } else if (StringUtils.isNullOrEmpty(mCustom.getCustomerType())) {
            T.showShort(this, "客户类型不能为空");
        } else if (mSelectAddress == null) {
            T.showShort(this, "省市区不能为空");
        } else if (StringUtils.isNullOrEmpty(rodeName)) {
            T.showShort(this, "街道不能为空");
        } else {
            mCustom.setCustomerName(customName);

            if (mLatLng != null) {
                mSelectAddress.setLatitude(String.valueOf(mLatLng.latitude));
                mSelectAddress.setLongitude(String.valueOf(mLatLng.longitude));
                mSelectAddress.setLocationDes(mLocationDes);
            }

            mSelectAddress.setRodeName(rodeName);

            mCustom.setAddress(mSelectAddress);

            if (mContacts.size() > 0) {
                mCustom.setContacts(mContacts);
            }

            mAddCustomPresenter.addCustom(mCustom);
        }
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mSelectCityUtil.init(this);

        mCommonAdapter = new CommonAdapter<Contact>(this, new ArrayList<>(),
                R.layout.item_contact) {
            @Override
            public void convert(MyViewHolder holder, Contact contact) {
                holder.setText(R.id.tv_id, contact.getContactPost())
                        .setText(R.id.tv_nick, contact.getContactName());
            }
        };

        mLvContactList.setAdapter(mCommonAdapter);
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
        mAddCustomPresenter.attachView(this);
        mAddCustomPresenter.onCreate();
    }

    /**
     * 添加联系人
     *
     * @param view
     */
    @OnClick(R.id.tv_add_contact)
    void onAddContactClicked(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_ADD);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CONTACT);
    }

    /**
     * 定位
     *
     * @param view
     */
    @OnClick(R.id.tv_location)
    void onSelectLocationClicked(View view) {
        Intent intent = new Intent(this, MapLocationActivity.class);
        intent.putExtra(MapLocationActivity.ARG_LATLNG, mLatLng);
        intent.putExtra(MapLocationActivity.ARG_LOCATION_DES, mLocationDes);
        startActivityForResult(intent, Constants.REQUEST_CODE_MARK_MAP);
    }

    /**
     * 更多资料
     *
     * @param view
     */
    @OnClick(R.id.fl_more_data)
    void onMoreDataClicked(View view) {
        Intent intent = new Intent(this, CustomMoreDataActivity.class);
        intent.putExtra(CustomMoreDataActivity.ARG_AREA, mAreaId);
        intent.putExtra(CustomMoreDataActivity.ARG_CUSTOM, mCustom);
        startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
    }

    /**
     * 选择地区
     *
     * @param view
     */
    @OnClick(R.id.tv_address_pca)
    void onSelectAreaClicked(View view) {
        mSelectCityUtil.showDialog();

        //关闭键盘
        KeyBoardUtils.closeKeybord(mEtCustomName, this);
        KeyBoardUtils.closeKeybord(mEtRoad, this);
    }

    /**
     * 选择客户类型
     */
    @OnClick(R.id.fl_custom_type)
    void onCustomTypeClicked(View view) {
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_TYPE);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }

    @Override
    public void selectCityFinish(Address address) {
        mSelectAddress = address;
        mAreaId = mSelectAddress.getAreaId();
        mTvAddressPca.setText(String.format("%s/%s/%s", address.getProvinceName(),
                address.getCityName(), address.getAreaName()));
    }

    @Override
    public void onAddCustomSuccess() {
        T.showShort(this, "添加客户成功");
        setResult(Constants.RESULT_CODE_ADD_CUSTOM);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_MARK_MAP) { //定位返回
            if (resultCode == Constants.RESULT_CODE_MARK_MAP) {
                Bundle bundle = data.getExtras();
                mLatLng = bundle.getParcelable(MapLocationActivity.RESULT_LATLNG);

                mLocationDes = bundle.getString(MapLocationActivity.RESULT_DETAIL);
                mTvLocation.setText(mLocationDes);
            }
        } else if (requestCode == Constants.REQUEST_CODE_SELECT_ATTR) { //选择客户类型
            if (resultCode == Constants.RESULT_CODE_SELECT_CUSTOM_TYPE) {
                SelectContent selectContent =
                        data.getParcelableExtra(SelectListActivity.RESULT);

                mCustom.setCustomerType(selectContent.getId());
                mTvCustomType.setText(selectContent.getName());
            }
        } else if (requestCode == Constants.REQUEST_CODE_MORE_DATA) { //更多资料返回
            if (resultCode == Constants.RESULT_CODE_MORE_DATA) {
                mCustom =
                        data.getParcelableExtra(CustomMoreDataActivity.ARG_RESULT);
            }
        } else if (requestCode == Constants.REQUEST_CODE_ADD_CONTACT) { //添加联系人
            if (resultCode == Constants.RESULT_CODE_ADD_CONTACT) {
                Contact contact =
                        data.getParcelableExtra(ContactActivity.ARG_CONTACT);
                mContacts.add(contact);

                mCommonAdapter.clear();
                mCommonAdapter.addAll(mContacts);
            }
        }
    }
}
