package com.apec.crm.support.rest.interceptors;

import android.content.Context;


import com.apec.crm.utils.AppUtils;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.utils.ScreenUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    Context mContext;

    public HeaderInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request request = originalRequest.newBuilder()
                .header("_c", "android")
                .header("IMEI", AppUtils.getDeviceId(mContext))
                //.header("UA", getHeaderUserAgent())
                .header("x-auth-token", (String) SPUtils.get(mContext, SPUtils.SESSION_ID, "0"))
                .method(originalRequest.method(), originalRequest.body())
                .build();
        return chain.proceed(request);
    }

    public String getHeaderUserAgent() {
        return AppUtils.getAppName(mContext) + "/"
                + AppUtils.getVersionCode(mContext) + "&ADR&"
                + ScreenUtils.getScreenWidth(mContext) + "&"
                + ScreenUtils.getScreenHeight(mContext) + "&"
                + AppUtils.getModel();
    }
}

