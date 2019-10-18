package com.zhifeng.wineculture.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.LogisticsDto;

/**
  *
  * @ClassName:     查看物流列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 10:55
  * @Version:        1.0
 */

public class LogisticsListAdapter extends BaseRecyclerAdapter<LogisticsDto.DataBean.ResultBean.ListBean> {
    public LogisticsListAdapter() {
        super(R.layout.layout_item_logistics);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, LogisticsDto.DataBean.ResultBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        TextView line = holder.itemView.findViewById(R.id.tv_item_line);
        line.setVisibility(position == (getAllData().size()-1)? View.GONE:View.VISIBLE);
        ImageView logo = holder.itemView.findViewById(R.id.iv_item_status);
        logo.setImageResource(position == 0?R.drawable.icon_address:R.drawable.icon_courier);
        holder.text(R.id.tv_item_address,model.getStatus());
    }
}
