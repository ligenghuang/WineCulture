package com.zhifeng.wineculture.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderDetailDto;
/**
 * @ClassName:
 * @Description: 订单详情
 * @Author: Administrator
 * @Date: 2019/10/12 12:02
 */
public class GoodsDetailAdapter extends BaseRecyclerAdapter<OrderDetailDto> {
    private Context context;

    public GoodsDetailAdapter(Context context) {
        super(R.layout.item_orderdetail);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderDetailDto model, int position) {
        holder.setIsRecyclable(false);
        int shipping_status = model.getData().getShipping_status();
        String status="未发货";//0未发货 1已发货 2部分发货 3已收货 4退货
        if (shipping_status==1){
            status="已发货";
        }else if (shipping_status==2){
            status="部分发货";
        }else if (shipping_status==3){
            status="已收货";
        }else if (shipping_status==4){
            status="退货";
        }
        RecyclerView rv=holder.itemView.findViewById(R.id.rv);

//        holder.text(R.id.tvGoodsStatus, status);
//        holder.text(R.id.tv_spec_key_name, model.getSpec_key_name());
//        holder.text(R.id.tv_goods_price, ResUtil.getFormatString(R.string.temporary_tab_14, model.getGoods_price()));
//        holder.text(R.id.tv_goods_num, ResUtil.getFormatString(R.string.temporary_tab_15, model.getGoods_num()));
//        holder.text(R.id.et_item_goods_num, String.valueOf(model.getGoods_num()));


    }

    public interface OnGoodsListener {
        void onChange(int totalNum, double totalPrice);
    }
}
