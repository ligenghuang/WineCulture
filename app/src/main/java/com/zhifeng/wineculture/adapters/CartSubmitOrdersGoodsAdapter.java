package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.Temporary;

/**
  *
  * @ClassName:     购物车提交订单 商品列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/16 9:37
  * @Version:        1.0
 */

public class CartSubmitOrdersGoodsAdapter extends BaseRecyclerAdapter<Temporary.DataBean.GoodsResBean> {
    Context context;

    public CartSubmitOrdersGoodsAdapter(Context context) {
        super(R.layout.layout_item_submit_order);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Temporary.DataBean.GoodsResBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_submit_order_name,model.getGoods_name());
        holder.text(R.id.tv_item_submit_order_price,"￥"+model.getGoods_price());
        holder.text(R.id.tv_item_submit_order_num,"X"+model.getGoods_num());
        double freight = Double.parseDouble(model.getGood_shipping_price());
        holder.text(R.id.tv_item_submit_order_freight,freight == 0 ? ResUtil.getString(R.string.goods_detail_tab_7) : "￥" + model.getGood_shipping_price());
        holder.text(R.id.tv_item_submit_order_total_num,ResUtil.getFormatString(R.string.cart_submit_orders_tab_9,model.getGoods_num()+""));
        holder.text(R.id.tv_item_submit_order_total_price,"￥"+model.getSubtotal_price());

        ImageView img = holder.itemView.findViewById(R.id.iv_item_submit_order_img);
        GlideUtil.setImage(context,model.getImg(),img,R.drawable.icon_goods_img);
        EditText editText = holder.itemView.findViewById(R.id.et_item_submit_orders_note);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(editText.getText().toString())){
                    model.setNote(editText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
