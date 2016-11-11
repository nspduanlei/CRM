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
package com.apec.crm.views.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.domin.entities.User;
import com.apec.crm.support.picasso.ImageLoad;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView mTextView;

    @BindView(R.id.imageView)
    ImageView mImageView;

    Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public UserItemViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void render(User user){
        mTextView.setText(user.getRealName());
        ImageLoad.loadUrlRound(mContext, user.getImg(), mImageView);
    }
}
