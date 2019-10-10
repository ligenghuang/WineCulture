package com.zhifeng.wineculture.utils.sku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.utils.sku.ProductModel;

import java.util.List;

/**
 * description: 规格基类
 * autour: huang
 * date: 2019/9/19 21:31
 * update: 2019/9/19
 * version:
 */
public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    private List<ProductModel.AttributesEntity.AttributeMembersEntity> mAttributeMembersEntities;
    OnClickListener mOnClickListener;
    private ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem;
    int parentHeight;
    public SkuAdapter(List<ProductModel.AttributesEntity.AttributeMembersEntity> attributeMembersEntities) {
        this.mAttributeMembersEntities = attributeMembersEntities;
    }

    public ProductModel.AttributesEntity.AttributeMembersEntity getCurrentSelectedItem() {
        return currentSelectedItem;
    }

    public void setCurrentSelectedItem(ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem) {
        this.currentSelectedItem = currentSelectedItem;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getAttributeMembersEntities() {
        return mAttributeMembersEntities;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sku_item_layout, parent, false);
        parentHeight = parent.getHeight();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mAttributeMembersEntities.get(position));
        holder.setClick(position);
    }

    @Override
    public int getItemCount() {
        return mAttributeMembersEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }

        public void setData(ProductModel.AttributesEntity.AttributeMembersEntity entity) {
            mTextView.setText(entity.getName());
            mTextView.setSelected(entity.getStatus() == 1);
        }

        public void setClick(final int position) {
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null)
                        mOnClickListener.onItemClickListener(position);
                }
            });
        }
    }

    public interface OnClickListener {
        void onItemClickListener(int position);
    }

}
