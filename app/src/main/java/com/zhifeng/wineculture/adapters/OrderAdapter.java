package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

/**
 * @ClassName:
 * @Description: 订单
 * @Author: Administrator
 * @Date: 2019/10/12 15:25
 */
public class OrderAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean> {
    private Context context;

    public OrderAdapter(Context context) {
        super(R.layout.item_myorder);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tvCreateTime, DynamicTimeFormat.LongToString2(model.getAdd_time() * 1000));
        Button btnLeft = holder.itemView.findViewById(R.id.btnLeft);
        Button btnRight = holder.itemView.findViewById(R.id.btnRight);
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        int status = model.getStatus();
        //1：待付款，2：待发货，3：待收货，4：交易成功（待评价），5：已取消，6：待退款，7：已退款，8：拒绝退款
        int state;
        if (status == 2) {
            state = R.string.myorder_toBeShipped;
            btnRight.setText(R.string.myorder_returnofgoods);
            btnRight.setVisibility(View.VISIBLE);
        } else if (status == 3) {
            state = R.string.myorder_toBeReceived;
            btnLeft.setText(R.string.myorder_confirmtakeover);
            btnRight.setText(R.string.myorder_lookuplogistics);
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
        } else if (status == 4) {
            state = R.string.myorder_toBeComment;
            btnRight.setText(R.string.myorder_comment);
            btnRight.setVisibility(View.VISIBLE);
        } else if (status == 5) {
            state = R.string.myorder_hadCancel;
        } else if (status == 6) {
            state = R.string.myorder_toBeRefund;
        } else if (status == 7) {
            state = R.string.myorder_hadRefund;
        } else if (status == 8) {
            state = R.string.myorder_refuseRefund;
        } else {
            state = R.string.myorder_obligation;
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setText(R.string.orderdetail_cancel);
            btnRight.setText(R.string.orderdetail_payNow2);
        }
        holder.text(R.id.tvGoodsStatus, state);
        RecyclerView rv = holder.itemView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        OrderGoodResAdapter adapter = new OrderGoodResAdapter(context);
        adapter.refresh(model.getGoods());
        rv.setAdapter(adapter);
        holder.text(R.id.tv_goods_num, ResUtil.getFormatString(R.string.myorder_totalGoodsNum, model.getGoods_num()));
        holder.text(R.id.tvTotalGoodsPrice, ResUtil.getFormatString(R.string.myorder_totalGoodsPrice, model.getGoods_price()));
        String shipping_price = model.getShipping_price();
        try {
            double postage = Double.parseDouble(shipping_price);
            holder.text(R.id.tvPostage, postage == 0 ? ResUtil.getString(R.string.myorder_free) : ResUtil.getFormatString(R.string.myorder_shipping_price, shipping_price));
        } catch (Exception e) {
            holder.text(R.id.tvPostage, shipping_price);
        }
    }

    public interface OnButtonClickListener {
        void onClick(View view);
    }
}
