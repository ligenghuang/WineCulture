package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;

/**
 * @ClassName:
 * @Description: 订单详情-商品
 * @Author: Administrator
 * @Date: 2019/10/12 12:18
 */
public class GoodsDetailGoodResAdapter extends BaseRecyclerAdapter<OrderDetailDto.DataBean.GoodsResBean> {
    private Context context;

    public GoodsDetailGoodResAdapter(Context context) {
        super(R.layout.item_orderdetail);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderDetailDto.DataBean.GoodsResBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivGoods=holder.itemView.findViewById(R.id.ivGoods);
        GlideUtil.setImage(context,model.getImg(),ivGoods,R.drawable.icon_goods_img);
        holder.text(R.id.tvGoodsName, model.getGoods_name());
        holder.text(R.id.tvGoodsPrice, ResUtil.getFormatString(R.string.temporary_tab_14, model.getGoods_price()));
        TextView tvGoodsOriginPrice=holder.itemView.findViewById(R.id.tvGoodsOriginPrice);
        tvGoodsOriginPrice.setText(ResUtil.getFormatString(R.string.temporary_tab_14, model.getOriginal_price()));
        tvGoodsOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        holder.text(R.id.tvGoodsNum, ResUtil.getFormatString(R.string.temporary_tab_15, model.getGoods_num()));
        holder.text(R.id.tv_item_note,ResUtil.getFormatString(R.string.orderdetail_tab_1,model.getUser_note()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goods_id", model.getGoods_id()+"");
                context.startActivity(intent);
            }
        });
    }
}
