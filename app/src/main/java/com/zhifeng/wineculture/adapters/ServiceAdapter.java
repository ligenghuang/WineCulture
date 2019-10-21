package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        RecyclerView rv = holder.itemView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        ServiceGoodsAdapter adapter = new ServiceGoodsAdapter(context);
        adapter.refresh(model.getGoods());
        rv.setAdapter(adapter);
        Button btnRefund = holder.itemView.findViewById(R.id.btnRefund);
        btnRefund.setVisibility(View.VISIBLE);
        String text = "";
        switch (model.getOrder_status()) {
            case 6:
                text = ResUtil.getString(R.string.refund_tab_2);
                btnRefund.setVisibility(View.GONE);
                break;
            case 7:
                text = ResUtil.getString(R.string.refund_tab_3);
                btnRefund.setVisibility(View.GONE);
                break;
            case 8:
                text = ResUtil.getString(R.string.refund_tab_4);
                break;
            default:
                text = ResUtil.getString(R.string.refund_tab_1);
                break;
        }

        holder.text(R.id.tv_item_stats, text);
        btnRefund.setOnClickListener(v -> {
            if (onRefundButtonClickListener != null) {
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
