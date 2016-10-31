package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Address;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.utils.KeyBoardUtils;
import com.apec.crm.utils.SelectCityUtil;
import com.apec.crm.views.activities.core.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 2016/10/24.
 * <p>
 * 客户基本信息修改
 */
public class CustomEditActivity extends BaseActivity implements SelectCityUtil.SelectArea {


    public static final String ARG_CUSTOM = "arg_custom";
    CustomDetail mCustomDetail;

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

    LatLng mLatLng;

    Address mAddress;

    @Inject
    SelectCityUtil mSelectCityUtil;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_edit, R.string.custom_base);
        setMenuText("保存", v -> {
            setResult(Constants.RESULT_CODE_CUSTOM_BASE,
                    getIntent().putExtra(ARG_CUSTOM, mCustomDetail));
            this.finish();
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCustomDetail = getIntent().getParcelableExtra(ARG_CUSTOM);

        if (mCustomDetail == null) {
            this.finish();
        }

        mEtCustomName.setText(mCustomDetail.getCustomerName());
        mTvCustomType.setText(mCustomDetail.getCustomerTypeName());

        mAddress = mCustomDetail.getAddress();

        mTvAddressPca.setText(String.format("%s/%s/%s",
                mAddress.getProvinceName(),
                mAddress.getCityName(),
                mAddress.getAreaName()));
        mTvLocation.setText(mAddress.getLocationDes());
        mEtRoad.setText(mAddress.getRodeName());

        //选择省市区
        mSelectCityUtil.init(this, mAddress);
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
        mCustomDetail.setAddress(address);
        mTvAddressPca.setText(String.format("%s/%s/%s", address.getProvinceName(),
                address.getCityName(), address.getAreaName()));
    }

    /**
     * 定位
     *
     * @param view
     */
    @OnClick(R.id.tv_location)
    void onSelectLocationClicked(View view) {
        Intent intent = new Intent(this, MapLocationActivity.class);
        intent.putExtra(MapLocationActivity.ARG_LATLNG, new LatLng(mAddress.getLatitude(),
                mAddress.getLongitude()));
        intent.putExtra(MapLocationActivity.ARG_LOCATION_DES, mAddress.getLocationDes());
        startActivityForResult(intent, Constants.REQUEST_CODE_MARK_MAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_SELECT_ATTR) { //选择客户类型
            if (resultCode == Constants.RESULT_CODE_SELECT_CUSTOM_TYPE) {
                SelectContent selectContent =
                        data.getParcelableExtra(SelectListActivity.RESULT);

                mCustomDetail.setCustomerType(selectContent.getId());
                mTvCustomType.setText(selectContent.getName());
            }
        } else if (requestCode == Constants.REQUEST_CODE_MARK_MAP) { //定位返回
            if (resultCode == Constants.RESULT_CODE_MARK_MAP) {
                Bundle bundle = data.getExtras();
                mLatLng = bundle.getParcelable(MapLocationActivity.RESULT_LATLNG);
                String locationStr = bundle.getString(MapLocationActivity.RESULT_DETAIL);

                mAddress.setLongitude(mLatLng.longitude);
                mAddress.setLatitude(mLatLng.latitude);
                mAddress.setLocationDes(locationStr);

                mTvLocation.setText(locationStr);
            }
        }
    }
}
