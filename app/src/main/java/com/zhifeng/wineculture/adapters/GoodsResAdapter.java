package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.Temporary;
/**
  *
  * @ClassName:     商品详情提交订单 商品列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/11 15:07
  * @Version:        1.0
 */

public class GoodsResAdapter extends BaseRecyclerAdapter<Temporary.DataBean.GoodsResBean> {
    private Context context;

    public GoodsResAdapter(Context context) {
        super(R.layout.layout_item_temporary);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Temporary.DataBean.GoodsResBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView iv = holder.itemView.findViewById(R.id.iv_goods_img);
        GlideUtil.setImage(context, model.getImg(), iv, R.drawable.icon_goods_img);
        holder.text(R.id.tv_goods_name, model.getGoods_name());
        holder.text(R.id.tv_spec_key_name, model.getSpec_key_name());
        holder.text(R.id.tv_goods_price, ResUtil.getFormatString(R.string.temporary_tab_14, model.getGoods_price()));
        holder.text(R.id.tv_goods_num, ResUtil.getFormatString(R.string.temporary_tab_15, model.getGoods_num()));
        holder.text(R.id.et_item_goods_num, String.valueOf(model.getGoods_num()));


    }

    public interface OnGoodsListener {
        void onChange(int totalNum, double totalPrice);
    }
}
