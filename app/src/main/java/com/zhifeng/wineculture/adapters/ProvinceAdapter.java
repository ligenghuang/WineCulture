package com.zhifeng.wineculture.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.RegionDto;

import java.util.List;

public class ProvinceAdapter extends BaseRecyclerAdapter<RegionDto.DataBean> {

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProvinceAdapter(int layoutResId, @Nullable List<RegionDto.DataBean> data) {
       super(data,layoutResId);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, RegionDto.DataBean model, int position) {
        holder.text(R.id.textview, model.getArea_name());
        TextView textview = holder.itemView.findViewById(R.id.textview);
        textview.setTextColor(model.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(model.getCode(),position);
            }
        });

    }
    public interface OnClickListener{
        void onClick(String code, int position);
    }
}
