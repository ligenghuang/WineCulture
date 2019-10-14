package com.zhifeng.wineculture.adapters;

import android.content.Context;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.TransferRecordDto;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

import java.text.DecimalFormat;

/**
  *
  * @ClassName:     转账记录列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 10:04
  * @Version:        1.0
 */

public class TransferRecordAdapter extends BaseRecyclerAdapter<TransferRecordDto.DataBeanX.DataBean> {
    Context context;
    public TransferRecordAdapter(Context context) {
        super(R.layout.layout_item_transfer_record);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, TransferRecordDto.DataBeanX.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString5(model.getCreate_time()));
        holder.text(R.id.tv_item_name,model.getNickname());
        DecimalFormat df = new DecimalFormat("#0.00");
        holder.text(R.id.tv_item_money,"￥"+model.getExchange_money());
        holder.text(R.id.tv_item_note, ResUtil.getString(model.getType() == 1?R.string.transfer_record_tab_5:R.string.transfer_record_tab_6));
    }
}
