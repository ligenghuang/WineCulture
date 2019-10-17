package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderListDto;

/**
 * @ClassName:
 * @Description: 售后
 * @Author: Administrator
 * @Date: 2019/10/15 15:38
 */
public class ServiceAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean> {
    private Context context;
    private OnRefundButtonClickListener onRefundButtonClickListener;

    public ServiceAdapter(Context context) {
        super(R.layout.item_service);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivGoods = holder.itemView.findViewById(R.id.ivGoods);
        GlideUtil.setImage(context, model.getGoods_price(), ivGoods, R.drawable.icon_goods_img);
        holder.text(R.id.tv_item_goods_name, model.getComment());
        holder.text(R.id.tv_goods_spec, model.getGoods_price());
        holder.text(R.id.tv_goods_num, ResUtil.getFormatString(R.string.temporary_tab_15, model.getGoods_num()));
        Button btnRefund = holder.itemView.findViewById(R.id.btnRefund);
        btnRefund.setVisibility(model.getIs_refund() == 1 ? View.VISIBLE : View.GONE);
        btnRefund.setOnClickListener(v -> {
            if (model.getIs_refund() == 1 && onRefundButtonClickListener != null) {
                onRefundButtonClickListener.onRefund(model.getOrder_id());
            }
        });
    }

    public void setOnRefundButtonClickListener(OnRefundButtonClickListener onRefundButtonClickListener) {
        this.onRefundButtonClickListener = onRefundButtonClickListener;
    }

    public interface OnRefundButtonClickListener {
        void onRefund(int order_id);
    }
}
