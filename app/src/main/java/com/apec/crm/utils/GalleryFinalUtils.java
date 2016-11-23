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
    public static final int REQUEST_EDIT_IMAGE = 0x2;
    private Context mContext;
    private CoreConfig mCoreConfig;

    public GalleryFinalUtils(Context context) {
        mContext = context;

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

        mCoreConfig = new CoreConfig.Builder(context.getApplicationContext(),
                imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .build();

        GalleryFinal.init(mCoreConfig);
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
                .setCropHeight(DensityUtils.dp2px(mContext, 67))
                .setCropWidth(DensityUtils.dp2px(mContext, 67))
                .setForceCrop(true)
                .setEnablePreview(true)
                .setForceCropEdit(false)
                .build();

        GalleryFinal.openGallerySingle(REQUEST_SELECT_IMAGE, functionConfig, onHanlderResultCallback);
    }

    /**
     * 选择拜访图片
     */
    public void selectVisitImage(int size,
                                 GalleryFinal.OnHanlderResultCallback onHanlderResultCallback) {

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(size)
                .build();

        GalleryFinal.openGalleryMuti(REQUEST_SELECT_IMAGE, functionConfig, onHanlderResultCallback);

        GalleryFinal.getCallback();
    }

    /**
     * 打开图片编辑
     */
    public void openImageEdit(String photoPath,
                              GalleryFinal.OnHanlderResultCallback onHanlderResultCallback) {

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnablePreview(true)
                .build();
        //带配置
        GalleryFinal.openEdit(REQUEST_EDIT_IMAGE, functionConfig, photoPath,
                onHanlderResultCallback);
    }


}
