package com.apec.crm.utils;

import android.content.Context;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by duanlei on 2016/10/20.
 */

public class GalleryFinalUtils {

    public static final int REQUEST_SELECT_IMAGE = 0x1;

    public GalleryFinalUtils(Context context) {

        //设置主题
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                .build();

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageLoader = new PicassoImageLoader();

        CoreConfig coreConfig = new CoreConfig.Builder(context, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .build();

        GalleryFinal.init(coreConfig);
    }

    /**
     * 选择头像
     */
    public void selectUserHead(GalleryFinal.OnHanlderResultCallback onHanlderResultCallback) {

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        GalleryFinal.openGallerySingle(REQUEST_SELECT_IMAGE, functionConfig, onHanlderResultCallback);
    }

    /**
     * 选择拜访图片
     */
    public void selectVisitImage(GalleryFinal.OnHanlderResultCallback onHanlderResultCallback,
                                 int size) {

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(3 - size)
                .build();

        GalleryFinal.openGalleryMuti(REQUEST_SELECT_IMAGE, functionConfig, onHanlderResultCallback);

    }



}
