/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apec.crm.views.widget.recyclerView.section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apec.crm.views.widget.recyclerView.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonSimpleSectionAdapter<T> extends
        SimpleSectionedAdapter<MyViewHolder> {

    private List<String> mSections = new ArrayList<>();
    private List<List<T>> mUsers = new ArrayList<>();
    private Context mContext;

    private final LayoutInflater mLayoutInflater;
    private int mResId;

    public CommonSimpleSectionAdapter(Context context, int resId) {
        mContext = context;
        mResId = resId;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        return mSections.size() == 0 ? "" : mSections.get(section);
    }

    @Override
    protected int getSectionCount() {
        return mSections.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return mUsers.get(section) == null ? 0 : mUsers.get(section).size();
    }

    @Override
    protected MyViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(mResId, parent, false);

        return new MyViewHolder(view, mContext);
    }

    @Override
    protected void onBindItemViewHolder(MyViewHolder holder, int section, int position) {
        convert(holder, mUsers.get(section).get(position), position);
    }

    public void notifyData(List<String> sections, List<List<T>> users) {
        mSections = sections;
        mUsers = users;
        notifyDataSetChanged();
    }

    public abstract void convert(MyViewHolder holder, T t, int position);
}
