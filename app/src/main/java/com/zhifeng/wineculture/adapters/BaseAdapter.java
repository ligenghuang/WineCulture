/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.data.IsFastClick;
import com.lgh.huanglib.util.listener.OnItemClickListener;
import com.lgh.huanglib.util.listener.OnItemLongClickListener;


/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/11/6 14:52
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private LayoutInflater mInflater;
    private Context context;

    public BaseAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }


    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivityNotFinish(final Context context, final Class<?> classActivity) {
        if (IsFastClick.isFastClick()) {
            context.startActivity(new Intent(context, classActivity));
        }
    }

    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }

}
