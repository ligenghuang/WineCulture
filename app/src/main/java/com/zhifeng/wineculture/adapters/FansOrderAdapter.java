package com.zhifeng.wineculture.adapters;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.FansOrderDto;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

public class FansOrderAdapter extends BaseRecyclerAdapter<FansOrderDto.DataBeanX.DataBean> {

    public FansOrderAdapter() {
        super(R.layout.layout_item_fansorder);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, FansOrderDto.DataBeanX.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tvOrderNo, model.getOrder_sn());
        holder.text(R.id.tvRealName, model.getRealname());
        holder.text(R.id.tvId, String.valueOf(model.getId()));
        holder.text(R.id.tvMoney, model.getTotal_amount());
        long time = model.getAdd_time() * (long) 1000;
        holder.text(R.id.tvDate, DynamicTimeFormat.LongToString5(time));
    }
}
