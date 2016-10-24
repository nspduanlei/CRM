package com.apec.crm.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/10/13.
 */

public class MyUtils {

    /**
     * 客户头像生成随机颜色
     */
    private static String[] mRandomColor = {"#D32F2F", "#C2185B", "#7B1FA2", "#FF1744", "#F50057",
            "#D500F9", "#D32F2F", "#C2185B", "#7B1FA2", "#FF1744"};

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




}
