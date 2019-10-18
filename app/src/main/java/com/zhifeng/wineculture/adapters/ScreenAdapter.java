package com.zhifeng.wineculture.adapters;

import android.view.View;
import android.widget.TextView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.ScreenDto;

public class ScreenAdapter extends BaseRecyclerAdapter<ScreenDto> {


    public ScreenAdapter() {
        super(R.layout.layout_item_screen);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, ScreenDto model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_type,model.getTitle());
        TextView textView = holder.itemView.findViewById(R.id.tv_type);
        textView.setSelected(model.isClick());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setClick(!model.isClick());
                textView.setSelected(model.isClick());
            }
        });
    }
}
