package com.apec.crm.mvp.presenters;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/28.
 */

public class Cheeses {

    public static ArrayList<String> randomList(int count, int page) {
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            data.add("test-----page:" + page + "---item:" + i);
        }

        return data;
    }
}
