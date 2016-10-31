package com.apec.crm.views.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.utils.DensityUtils;
import com.apec.crm.utils.LocationTask;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/14.
 */
public class MapLocationActivity extends BaseActivity implements AMapLocationListener,
        AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, GeocodeSearch.OnGeocodeSearchListener {

    AMap mAMap = null;
    LocationTask mLocationTask;
    GeocodeSearch mGeocodeSearch;

    @BindView(R.id.map)
    MapView mMapView;

    //当前选择经纬度
    LatLng mLatLngFirst;
    LatLng mLatLngSelect;

    @BindView(R.id.iv_location_icon)
    ImageView mLocationIcon;

    //显示当前选择的地址
    @BindView(R.id.tv_address_select)
    TextView mTvAddressSelect;

    ObjectAnimator mObjectAnimator;

    //城市编码, 用于搜索
    String mCityCode;

    public static final String RESULT_DETAIL = "locationStr";
    public static final String RESULT_LATLNG = "latLng";

    public static final String ARG_LATLNG = "arg_latlng";
    public static final String ARG_LOCATION_DES = "arg_location_des";

    String mLocationDes;

    private void initLocation() {
        //启动定位
        mLocationTask = new LocationTask(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_map_location, R.string.location_title);
        setMenuText("确定", v -> {

            Bundle bundle = new Bundle();
            bundle.putString(RESULT_DETAIL, mLocationDes);
            bundle.putParcelable(RESULT_LATLNG, mLatLngSelect);

            setResult(Constants.RESULT_CODE_MARK_MAP, getIntent().putExtras(bundle));
            this.finish();
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mLatLngFirst = getIntent().getParcelableExtra(ARG_LATLNG);
        mLocationDes = getIntent().getStringExtra(ARG_LOCATION_DES);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMapLoadedListener(this);

        mObjectAnimator = ObjectAnimator.ofFloat(mLocationIcon, "translationY",
                DensityUtils.dp2px(this, -30));
        mObjectAnimator.setRepeatCount(1);
        mObjectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        mObjectAnimator.setDuration(500);
    }

    private void addMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLngSelect);
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_poi_loc));
        markerOptions.setFlat(true);
        mAMap.addMarker(markerOptions);
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLngSelect, 18));
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //定位地址变化
        if (aMapLocation != null) {

            //当前位置描点
            mCityCode = aMapLocation.getCityCode();
            mLatLngFirst = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            mLatLngSelect = mLatLngFirst;
            addMarker();

            initGeocodeSearch();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        //地图滑动
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (cameraPosition != null) {
            LatLonPoint latLonPoint =
                    new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
            mLatLngSelect = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);

            startLoading();
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 100, GeocodeSearch.AMAP);
            mGeocodeSearch.getFromLocationAsyn(query);
        }
    }

    @Override
    public void onMapLoaded() {
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        initMap();

        if (mLatLngFirst != null && mLatLngFirst.latitude != 0) {
            mLatLngSelect = mLatLngFirst;
            addMarker();
            mTvAddressSelect.setText(mLocationDes);

            //地址选择完成,根据经纬度获取地址信息
            initGeocodeSearch();
        } else {
            initLocation();
        }
    }

    private void initMap() {
        mAMap.setOnCameraChangeListener(this);
        mAMap.getUiSettings().setZoomControlsEnabled(false);
    }

    private void initGeocodeSearch() {
        mGeocodeSearch = new GeocodeSearch(this);
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }

    @OnClick(R.id.iv_location)
    void onLocationClicked(View view) {
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLngFirst, 18));
    }

//    @OnClick(R.id.btn_search)
//    void onSearchClicked(View view) {
//        Intent intent = new Intent(this, MapSearchActivity.class);
//        if (mCityCode != null) {
//            intent.putExtra("cityCode", mCityCode);
//        }
//        startActivityForResult(intent, Constants.REQUEST_CODE_SEARCH_MAP);
//    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            mLocationDes = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            mTvAddressSelect.setText(mLocationDes);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private void startLoading() {
        if (mObjectAnimator != null) {
            mObjectAnimator.start();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.REQUEST_CODE_SEARCH_MAP) {
//            if (resultCode == Constants.RESULT_CODE_MAP_SELECT_SUC) {
//
//                Tip tip = data.getParcelableExtra("address");
//                mLatLngSelect = new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
//                addMarker();
//            }
//        }
//    }
}
