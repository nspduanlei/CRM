package com.apec.crm.views.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.utils.DensityUtils;
import com.apec.crm.utils.LocationTask;
import com.apec.crm.utils.ScreenUtils;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by duanlei on 16/9/14.
 */
public class MapMarkActivity extends BaseActivity implements LocationSource, AMapLocationListener,
        AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, PoiSearch.OnPoiSearchListener {

    AMap mAMap = null;
    PoiSearch mPoiSearch = null;
    OnLocationChangedListener mListener;
    LocationTask mLocationTask;
    Marker mMarker;

    @BindView(R.id.lv_poi)
    ListView mPois;
    ArrayList<PoiItem> mPoiResult;
    CommonAdapter<PoiItem> mPoiItemCommonAdapter;

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;


    private void initMarker() {
        //默认点标记
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(true);

        FrameLayout frameLayout = new FrameLayout(this);

        ImageView viewIng = new ImageView(this);
        viewIng.setImageResource(R.drawable.map_loc_ing);

        //ObjectAnimator.ofFloat(viewIng, "translationY", 0, 20).start();

        ImageView viewBg = new ImageView(this);
        viewBg.setImageResource(R.drawable.map_loc_bg);

        frameLayout.addView(viewBg);
        frameLayout.addView(viewIng);

        markerOption.icon(BitmapDescriptorFactory.fromView(frameLayout));

        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);

        mMarker = mAMap.addMarker(markerOption);
        mMarker.setPositionByPixels(ScreenUtils.getScreenWidth(this) / 2,
                DensityUtils.dp2px(this, 104));
    }

    private void initLocation() {
        //显示定位按钮
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true); // 显示默认的定位按钮
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);


        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(
                R.drawable.location_poi_loc));
        locationStyle.strokeWidth(0);
        locationStyle.anchor(0.5f, 0.5f);
        locationStyle.radiusFillColor(Color.TRANSPARENT);
        mAMap.setMyLocationStyle(locationStyle);

        //启动定位
        mLocationTask = new LocationTask(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mLocationTask.startSingleLocate();
    }

    private void initPoiSearch() {
        PoiSearch.Query query = new PoiSearch.Query("餐饮服务", "");
        query.setPageSize(20);
        mPoiSearch = new PoiSearch(this, query);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiResult = new ArrayList<>();
        mPoiItemCommonAdapter = new CommonAdapter<PoiItem>(this, mPoiResult, R.layout.item_poi_list) {
            @Override
            public void convert(MyViewHolder holder, PoiItem poiItem) {
                holder.setText(R.id.tv_name, poiItem.getTitle())
                        .setText(R.id.tv_location, poiItem.getSnippet())
                        .setText(R.id.tv_distance, poiItem.getDistance() + "米");
            }
        };
        mPois.setAdapter(mPoiItemCommonAdapter);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_map_mark, R.string.location_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();

        mAMap.setOnCameraChangeListener(this);
        mAMap.setOnMapLoadedListener(this);
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
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            mListener.onLocationChanged(aMapLocation);
            if (aMapLocation.getErrorCode() == 0) {

                Log.d("test001", "latitude:" + aMapLocation.getLatitude());

            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mMarker.setToTop();
        Log.e("test001", "" + cameraPosition.target.latitude);

        //检索周边的poi
        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mMarker.getPosition().latitude,
                mMarker.getPosition().longitude), 500));

        mPoiSearch.searchPOIAsyn();
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapLoaded() {
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        initMarker();
        initLocation();
        //初始化poi搜索
        initPoiSearch();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        mLoading.setVisibility(View.GONE);
        if (i == 1000) {
            mPoiResult.clear();
            mPoiResult.addAll(poiResult.getPois());
            mPoiItemCommonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
