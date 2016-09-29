package com.apec.crm.views.widget.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by duanlei on 16/9/21.
 */

public abstract class CommonRecyclerAdapter<T> extends
        RecyclerView.Adapter<MyViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<T> mData;
    private int mResId;

    public CommonRecyclerAdapter(Context context, int resId, List<T> data) {
        mData = data;
        mResId = resId;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                mLayoutInflater.inflate(mResId, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        convert(holder, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public abstract void convert(MyViewHolder holder, T t, int position);

    public void clear() {
        mData.clear();
    }

    public void addAll(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
