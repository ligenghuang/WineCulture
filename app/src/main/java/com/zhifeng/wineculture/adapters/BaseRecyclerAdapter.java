package com.zhifeng.wineculture.adapters;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by SCWANG on 2017/6/11.
 */

@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<SmartViewHolder> implements ListAdapter {


    //<editor-fold desc="BaseRecyclerAdapter">

    private final int mLayoutId;
    public List<T> mList;
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private AdapterView.OnItemClickListener mListener;

    public BaseRecyclerAdapter(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutId = layoutId;
        mHeaderViews = new SparseArray<>();
    }

    public BaseRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
        mHeaderViews = new SparseArray<>();
    }

    public BaseRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
        mHeaderViews = new SparseArray<>();
    }
    //</editor-fold>
    public List<T> getAllData() {
        if (mList == null) {
            return new ArrayList<>();
        }
        return mList;
    }

    private void addAnimate(SmartViewHolder holder, int postion) {
        if (mOpenAnimationEnable && mLastPosition < postion) {
            holder.itemView.setAlpha(0);
            holder.itemView.animate().alpha(1).start();
            mLastPosition = postion;
        }
    }

    //<editor-fold desc="RecyclerAdapter">
    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(SmartViewHolder holder, int position) {
        onBindViewHolder(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    protected abstract void onBindViewHolder(SmartViewHolder holder, T model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(SmartViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimate(holder, holder.getLayoutPosition());
    }

    public void setOpenAnimationEnable(boolean enabled) {
        this.mOpenAnimationEnable = enabled;
    }

    //</editor-fold>

    //<editor-fold desc="API">

    public BaseRecyclerAdapter<T> setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        mLastPosition = -1;
        return this;
    }

    public BaseRecyclerAdapter<T> loadMore(Collection<T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="ListAdapter">
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

//    public boolean hasStableIds() {
//        return false;
//    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartViewHolder holder;
        if (convertView != null) {
            holder = (SmartViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        holder.setPosition(position);
        onBindViewHolder(holder, position);
        addAnimate(holder, position);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    //</editor-fold>
    private SparseArray<View> mHeaderViews;
    // 基本的头部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    /**
     * 添加头部
     */
    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }
}
