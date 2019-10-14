package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderListDto;

/**
 * @ClassName:
 * @Description: 订单-商品
 * @Author: Administrator
 * @Date: 2019/10/12 15:42
 */
public class OrderGoodResAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean.GoodsBean> {
    private Context context;

    public OrderGoodResAdapter(Context context) {
        super(R.layout.item_myorder_goods);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListDto.DataBean.GoodsBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivGoods=holder.itemView.findViewById(R.id.iv_item_goods);
        GlideUtil.setImage(context,model.getImg(),ivGoods,R.drawable.icon_goods_img);
        holder.text(R.id.tvGoodsName, model.getGoods_name());
        holder.text(R.id.tv_goods_spec, model.getSpec_key_name());
        holder.text(R.id.tv_goods_price, ResUtil.getFormatString(R.string.temporary_tab_14, model.getGoods_price()));
    }
}
