package com.apec.crm.support.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.apec.crm.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by duanlei on 2016/11/8.
 */

public class ImageLoad {

    public static void loadFileRound(Context context, File imgFile, ImageView imageView) {
        if (imgFile == null) {
            return;
        }
        Picasso.with(context)
                .load(imgFile)
                .config(Bitmap.Config.RGB_565)
                .resize(imageView.getWidth(), imageView.getHeight())
                .transform(new CircleTransform())
                .into(imageView);
    }

    public static void loadUrlRound(Context context, String url, ImageView imageView) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        Picasso.with(context)
                .load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }

    public static void loadUrl(Context context, ImageView iv, String url) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        Picasso.with(context)
                .load(url)
                .into(iv);
    }
}

