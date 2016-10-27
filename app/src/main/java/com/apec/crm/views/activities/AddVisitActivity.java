package com.apec.crm.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.AddVisitBean;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerVisitComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.AddVisitPresenter;
import com.apec.crm.mvp.views.AddVisitView;
import com.apec.crm.utils.GalleryFinalUtils;
import com.apec.crm.utils.LocationTask;
import com.apec.crm.utils.StringUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by duanlei on 16/9/19.
 */
public class AddVisitActivity extends BaseActivity implements AMapLocationListener,
        GalleryFinal.OnHanlderResultCallback, AddVisitView {

    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_custom)
    TextView mTvCustom;
    @BindView(R.id.fl_custom)
    FrameLayout mFlCustom;
    @BindView(R.id.tv_contact)
    TextView mTvContact;
    @BindView(R.id.iv_add_image)
    ImageView mIvAddImage;
    @BindView(R.id.ll_pic)
    LinearLayout mLlPic;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    LocationTask mLocationTask;
    //当前选择经纬度
    LatLng mLatLng;

    @Inject
    AddVisitPresenter mAddVisitPresenter;

    private String mCustomId, mContactId;
    private AddVisitBean mAddVisitBean = new AddVisitBean();

    GalleryFinalUtils mGalleryFinalUtils;
    public static final String ARG_CUSTOM_ID = "arg_custom_id";

    //选择的图片
    private ArrayList<File> mImages = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_visit, R.string.add_visit_title);
        setMenuText("保存", v -> {
            if (mCustomId == null) {
                T.showShort(this, "请选择客户");
            } else if (mContactId == null) {
                T.showShort(this, "请选择联系人");
            }
//            else if (mImages.size() == 0) {
//                T.showShort(this, "请选择图片");
//            }
            else {
                String mark = mEtContent.getText().toString();
                if (!StringUtils.isNullOrEmpty(mark)) {
                    mAddVisitBean.setVisitRemarks(mark);
                }

                //添加拜访
                mAddVisitPresenter.addVisit(mAddVisitBean, mImages);
            }
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCustomId = getIntent().getStringExtra(ARG_CUSTOM_ID);

        if (mCustomId != null) {
            mFlCustom.setVisibility(View.GONE);
        }

        //定位获取当前位置
        mLocationTask = new LocationTask(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();

        mGalleryFinalUtils = new GalleryFinalUtils(this);
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerVisitComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mAddVisitPresenter.attachView(this);
        mAddVisitPresenter.onCreate();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            mLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            setLocationString(aMapLocation.getAddress());

            //保存地址信息
            AddVisitBean.LocationJsonBean locationJsonBean =
                    new AddVisitBean.LocationJsonBean();
            locationJsonBean.setLatitude(mLatLng.latitude);
            locationJsonBean.setLongitude(mLatLng.longitude);
            mAddVisitBean.setLocationJson(locationJsonBean);
            mAddVisitBean.setCustomerAddress(aMapLocation.getAddress());
        }
    }

    @OnClick(R.id.fl_location)
    void onLocationClicked(View view) {
        Intent intent = new Intent(this, MapLocationActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MARK_MAP);
    }

    @OnClick(R.id.fl_custom)
    void onCustomClicked(View view) {
        Intent intent = new Intent(this, SearchCustomActivity.class);
        intent.putExtra(SearchCustomActivity.FROM_TYPE, SearchCustomActivity.SEARCH_CUSTOM);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_CUSTOM);
    }

    @OnClick(R.id.fl_contact)
    void onContactClicked(View view) {
        if (mCustomId == null) {
            T.showShort(this, "请先选择客户");
            return;
        }
        Intent intent = new Intent(this, SelectListActivity.class);
        intent.putExtra(SelectListActivity.CUSTOM_ID, mCustomId);
        intent.putExtra(SelectListActivity.LIST_TYPE, SelectListActivity.CUSTOM_CONTACT);
        startActivityForResult(intent, Constants.REQUEST_CODE_SELECT_ATTR);
    }


    @OnClick(R.id.iv_add_image)
    void onSelectImageClicked(View view) {
        if (mImages.size() < 3) {
            mGalleryFinalUtils.selectVisitImage(this, mImages.size());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.REQUEST_CODE_MARK_MAP: //选择地址
                if (resultCode == Constants.RESULT_CODE_MARK_MAP) {
                    Bundle bundle = data.getExtras();
                    mLatLng = bundle.getParcelable(MapLocationActivity.RESULT_LATLNG);
                    String locationDetail =
                            bundle.getString(MapLocationActivity.RESULT_DETAIL);
                    setLocationString(locationDetail);

                    //保存地址信息
                    AddVisitBean.LocationJsonBean locationJsonBean =
                            new AddVisitBean.LocationJsonBean();
                    locationJsonBean.setLatitude(mLatLng.latitude);
                    locationJsonBean.setLongitude(mLatLng.longitude);
                    mAddVisitBean.setLocationJson(locationJsonBean);
                    mAddVisitBean.setCustomerAddress(locationDetail);
                }
                break;

            case Constants.REQUEST_CODE_SELECT_CUSTOM: //选择客户
                if (resultCode == Constants.RESULT_CODE_SELECT_CUSTOM) {
                    Custom custom = data.getParcelableExtra(SearchCustomActivity.CUSTOM);
                    mCustomId = custom.getId();
                    mTvCustom.setText(custom.getCustomerName());

                    mAddVisitBean.setCustomerNo(custom.getId());
                    mAddVisitBean.setCustomerName(custom.getCustomerName());
                }

                break;

            case Constants.REQUEST_CODE_SELECT_ATTR: //选择联系人
                if (resultCode == Constants.RESULT_CODE_SELECT_CONTACT) {
                    SelectContent content = data.getParcelableExtra(SelectListActivity.RESULT);
                    mContactId = content.getId();
                    mTvContact.setText(content.getName());

                    mAddVisitBean.setContactNo(content.getId());
                    mAddVisitBean.setContactName(content.getName());
                    mAddVisitBean.setContactPhone(content.getOther());
                }
                break;
        }
    }

    private void setLocationString(String locationString) {
        mTvLocation.setText(locationString);
    }

    @Override
    public void onHanlderSuccess(int request, List<PhotoInfo> resultList) {
        if (request == GalleryFinalUtils.REQUEST_SELECT_IMAGE) {

            for (int i = 0; i < resultList.size(); i++) {

                mImages.add(new File(resultList.get(i).getPhotoPath()));

                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(mIvAddImage.getLayoutParams());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Picasso.with(this)
                        .load(new File(resultList.get(i).getPhotoPath()))
                        .config(Bitmap.Config.RGB_565)
                        .resize(mIvAddImage.getWidth(), mIvAddImage.getHeight())
                        .centerInside()
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(imageView);

                mLlPic.addView(imageView, i);
            }


            if (mLlPic.getChildCount() >= 4) {
                mIvAddImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }

    @Override
    public void addVisitSuccess() {
        T.showShort(this, "添加拜访成功");
        setResult(Constants.RESULT_CODE_ADD_VISIT);
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
