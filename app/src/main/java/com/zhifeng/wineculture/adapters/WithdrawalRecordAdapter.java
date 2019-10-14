package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.text.style.DynamicDrawableSpan;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.WithdrawalRecordDto;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

import java.text.DecimalFormat;

/**
  *
  * @ClassName:     提现记录 适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 10:23
  * @Version:        1.0
 */

public class WithdrawalRecordAdapter extends BaseRecyclerAdapter<WithdrawalRecordDto.DataBean.ListBean> {
    Context context;

    public WithdrawalRecordAdapter(Context context) {
        super(R.layout.layout_item_withdrawal_record);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, WithdrawalRecordDto.DataBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString5(model.getCreatetime()));
        DecimalFormat df = new DecimalFormat("#0.00");
        holder.text(R.id.tv_item_money,"￥"+ model.getMoney());
        holder.text(R.id.tv_item_poundage,"￥"+model.getTaxfee());
        String status = ResUtil.getString(R.string.withdrawal_record_tab_5);
        switch (model.getStatus()){
            case 1:
                //todo 待审核
                status = ResUtil.getString(R.string.withdrawal_record_tab_5);
                break;
            case 2:
                //todo 审核通过
                status = ResUtil.getString(R.string.withdrawal_record_tab_7);
                break;
            default:
                //todo 审核失败
                status = ResUtil.getString(R.string.withdrawal_record_tab_6);
                break;
        }
        holder.text(R.id.tv_item_stats,status);
    }
}
