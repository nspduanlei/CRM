package com.apec.crm.utils;

import android.graphics.Color;

/**
 * Created by duanlei on 2016/10/13.
 */

public class MyUtils {

    /**
     * 客户头像生成随机颜色
     */
    private static String[] mRandomColor = {"#D32F2F", "#C2185B", "#7B1FA2", "#FF1744", "#F50057", "#D500F9"};

    public static int getRandomColor() {
        return Color.parseColor(mRandomColor[(int) (Math.random() * 10) % mRandomColor.length]);
    }

}
