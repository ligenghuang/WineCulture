package com.zhifeng.wineculture.adapters;

import android.content.Context;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.BalanceOfPaymentDto;

import java.text.DecimalFormat;

/**
  *
  * @ClassName:     收支明细列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 9:31
  * @Version:        1.0
 */

public class BalanceOfPaymentAdapter extends BaseRecyclerAdapter<BalanceOfPaymentDto.DataBean.ListBean> {
    Context context;

    public BalanceOfPaymentAdapter(Context context) {
        super(R.layout.layout_item_balance_of_payments);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, BalanceOfPaymentDto.DataBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_title,model.getNote());
        holder.text(R.id.tv_item_time,model.getCreate_time());
        String type = model.getLog_type() == 1?"-":"+";
        DecimalFormat df = new DecimalFormat("#0.00");
        holder.text(R.id.tv_item_money, type+df.format(model.getBalance()));
    }
}
