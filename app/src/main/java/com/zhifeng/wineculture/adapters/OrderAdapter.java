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
public class OrderAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean> implements View.OnClickListener {
    private Context context;
    /**
     * 取消
     */
    private final int CANCEL = 0;
    /**
     * 确认收货
     */
    private final int CONFIRM_TO_RECEIVE = 1;
    /**
     * 立即支付
     */
    private final int PAY_NOW = 2;
    /**
     * 退货
     */
    private final int REFUND = 3;
    /**
     * 查看物流
     */
    private final int LOOK_UP_LOGISTICS = 4;
    /**
     * 评价
     */
    private final int COMMENT = 5;
    private OnButtonClickListener onButtonClickListener;

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
        int state = 0;
        if (status == 1) {
            state = R.string.myorder_obligation;
            btnLeft.setText(R.string.orderdetail_cancel);
            btnRight.setText(R.string.orderdetail_payNow2);
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setOnClickListener(this);
            btnRight.setOnClickListener(this);
            int[] dataLeft = {CANCEL, model.getOrder_id(), status};
            btnLeft.setTag(dataLeft);
            double totalPrice = 0;
            for (OrderListDto.DataBean.GoodsBean goodsBean : model.getGoods()) {
                totalPrice += Double.parseDouble(goodsBean.getGoods_price());
            }
            Object[] dataRight = {PAY_NOW, model.getOrder_id(), model.getPay_type(), totalPrice};
            btnRight.setTag(dataRight);
        } else if (status == 2) {
            state = R.string.myorder_toBeShipped;
            btnRight.setText(R.string.myorder_returnofgoods);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setOnClickListener(this);
            Object[] dataRight = {REFUND, model.getOrder_id()};
            btnRight.setTag(dataRight);
        } else if (status == 3) {
            state = R.string.myorder_toBeReceived;
            btnLeft.setText(R.string.myorder_confirmtakeover);
            btnRight.setText(R.string.myorder_lookuplogistics);
            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setOnClickListener(this);
            btnRight.setOnClickListener(this);
            int[] dataLeft = {CONFIRM_TO_RECEIVE, model.getOrder_id(), status};
            btnLeft.setTag(dataLeft);
            Object[] dataRight = {LOOK_UP_LOGISTICS, model.getOrder_id()};
            btnRight.setTag(dataRight);
        } else if (status == 4) {
            state = R.string.myorder_toBeComment;
            btnRight.setText(R.string.myorder_comment);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setOnClickListener(this);
            int order_id = model.getOrder_id();
            Object[] dataRight = {COMMENT, order_id};
            btnRight.setTag(dataRight);
        } else if (status == 5) {
            state = R.string.myorder_hadCancel;
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(state);
        } else if (status == 6) {
            state = R.string.myorder_toBeRefund;
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(state);
        } else if (status == 7) {
            state = R.string.myorder_hadRefund;
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(state);
        } else if (status == 8) {
            state = R.string.myorder_refuseRefund;
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setText(state);
        }
        if (state != 0) {
            holder.text(R.id.tvGoodsStatus, state);
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLeft) {
            int[] data = (int[]) v.getTag();
            if (onButtonClickListener != null) {
                int type = data[0];
                if (type == CANCEL || type == CONFIRM_TO_RECEIVE) {
                    int order_id = data[1];
                    int status = data[2];
                    onButtonClickListener.cancelOrConfirmToReceive(order_id, status);
                }
            }
        } else {
            Object[] data = (Object[]) v.getTag();
            if (onButtonClickListener != null) {
                int type = (int) data[0];
                int order_id = (int) data[1];
                if (type == PAY_NOW) {
                    int goods_id = (int) data[2];
                    double totalPrice = (double) data[3];
                    onButtonClickListener.payNow(order_id, goods_id, totalPrice);
                } else if (type == REFUND) {
                    onButtonClickListener.refund(order_id);
                } else if (type == LOOK_UP_LOGISTICS) {
                    onButtonClickListener.lookUpLogistics(order_id);
                } else if (type == COMMENT) {
                    onButtonClickListener.comment(order_id);
                }
            }
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public interface OnButtonClickListener {
        /**
         * 取消/确认收货
         */
        void cancelOrConfirmToReceive(int order_id, int status);

        /**
         * 立即支付
         */
        void payNow(int order_id, int pay_type, double totalPrice);

        /**
         * 退货
         */
        void refund(int order_id);

        /**
         * 查看物流
         */
        void lookUpLogistics(int order_id);

        /**
         * 评价
         */
        void comment(int order_id);
    }
}
