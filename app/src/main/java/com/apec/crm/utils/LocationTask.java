package com.apec.crm.utils;

import android.content.Context;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * Created by duanlei on 16/9/18.
 */
public class LocationTask  {

    private Context mContext;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    public LocationTask(Context context) {
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(false);
        mContext = context;
    }

    public void setOnLocationGetListener(AMapLocationListener aMapLocationListener) {
        mLocationClient.setLocationListener(aMapLocationListener);
    }

    /**
     * 开启单次定位
     */
    public void startSingleLocate() {
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(false);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    /**
     * 开启多次定位
     */
    public void startLocate() {
        mLocationOption.setInterval(1000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    /**
     * 结束定位，可以跟多次定位配合使用
     */
    public void stopLocate() {
        mLocationClient.stopLocation();
    }
}
