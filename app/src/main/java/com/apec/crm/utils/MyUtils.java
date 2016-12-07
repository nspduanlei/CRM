package com.apec.crm.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.config.ErrorCode;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

import rx.Subscription;


/**
 * Created by duanlei on 2016/10/13.
 */

public class MyUtils {

    /**
     * 客户头像生成随机颜色
     */
    private static String[] mRandomColor = {"#30c6fe", "#2ee0da", "#c3ae81", "#caa6ff", "#ff80cb",
            "#ffbd49", "#ff6d8c", "#30c6fe", "#2ee0da", "#c3ae81"};

    public static int getRandomColor() {
        return Color.parseColor(mRandomColor[(int) (Math.random() * 10) % mRandomColor.length]);
    }

    public static int getColor(String icon) {
        int i;
        if (StringUtils.isNullOrEmpty(icon)) {
            i = 0;
            return Color.parseColor(mRandomColor[i]);
        } else {
            i = Integer.valueOf(icon);
            return Color.parseColor(mRandomColor[i - 1]);
        }
    }

    /**
     * 设置用户名头像
     */
    public static void setHeadText(TextView textView, String icon) {
        if (icon.length() <= 2) {
            textView.setText(icon);
        } else {
            textView.setText(icon.substring(0, 2));
        }
    }



    public static void showListDialog(Context context,
                                      ArrayList<MenuEntity> selectContents,
                                      OnItemClickListener itemClickListener) {

        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setAdapter(new CommonAdapter<MenuEntity>(context, selectContents,
                        R.layout.select_list_item) {
                    @Override
                    public void convert(MyViewHolder holder, MenuEntity menuEntity) {
                        holder.setText(R.id.tv_content, menuEntity.getName());
                    }
                })
                .setOnItemClickListener(itemClickListener)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .create();

        dialogPlus.show();
    }


    /**
     * 拨打电话
     * @param mobile
     */
    public static void callPhone(Context context, String mobile) {
        // 使用系统的电话拨号服务，必须去声明权限，在AndroidManifest.xml中进行声明
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + mobile));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        context.startActivity(intent);
    }


    /**
     * 取消订阅，防止内存泄漏
     */
    public static void cancelSubscribe(Subscription... subscriptions) {
        for (int i = 0; i < subscriptions.length; i++) {
            if (subscriptions[i] != null && !subscriptions[i].isUnsubscribed()) {
                subscriptions[i].unsubscribe();
            }
        }
    }

    /**
     * 登录token 过期
     */
    public static void tokenTimeOut(String errorCode, android.app.Activity activity) {
        if (errorCode.equals(ErrorCode.SESSION_OUT)) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
