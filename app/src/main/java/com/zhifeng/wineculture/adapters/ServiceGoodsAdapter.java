package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderListDto;

/**
 * @ClassName:
 * @Description: 售后-商品
 * @Author: Administrator
 * @Date: 2019/10/18 14:03
 */
public class ServiceGoodsAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean.GoodsBean> {
    private Context context;

    public ServiceGoodsAdapter(Context context) {
        super(R.layout.item_service_goods);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListDto.DataBean.GoodsBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivGoods = holder.itemView.findViewById(R.id.ivGoods);
        GlideUtil.setImage(context, model.getGoods_price(), ivGoods, R.drawable.icon_goods_img);
        holder.text(R.id.tv_item_goods_name, model.getGoods_name());
        holder.text(R.id.tv_goods_spec, model.getSpec_key_name());
        holder.text(R.id.tv_goods_num, ResUtil.getFormatString(R.string.temporary_tab_15, model.getGoods_num()));
    }
}
