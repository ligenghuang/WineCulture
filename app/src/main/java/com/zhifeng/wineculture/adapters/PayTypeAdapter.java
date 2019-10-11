package com.zhifeng.wineculture.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.Temporary;

/**
  *
  * @ClassName:     商品详情提交订单  支付方式列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/11 15:13
  * @Version:        1.0
 */

public class PayTypeAdapter extends BaseRecyclerAdapter<Temporary.DataBean.PayTypeBean> {

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PayTypeAdapter() {
        super(R.layout.layout_item_paytype);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Temporary.DataBean.PayTypeBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView iv = holder.itemView.findViewById(R.id.ivPayType);
        int pay_type = model.getPay_type();
        if (pay_type == 1) {
            iv.setImageResource(model.isSelect() ? R.drawable.yueweizhifu_y : R.drawable.yueweizhifu);
        } else if (pay_type == 3) {
            iv.setImageResource(model.isSelect() ? R.drawable.zhifubaoweizhifu_y : R.drawable.zhifubaoweizhifu);
        } else {
            iv.setImageResource(model.isSelect() ? R.drawable.weixinzhifu : R.drawable.weixinweizhifu);
        }
        holder.text(R.id.tvPayType, model.getPay_name());

        ImageView ivPayTypeCircle = holder.itemView.findViewById(R.id.iv);
        if (model.isSelect()){
            ivPayTypeCircle.setImageResource(R.drawable.shape_solid_circle);
        }else{
            ivPayTypeCircle.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClickListener.onClick(pay_type,model.getPay_name());
           }
       });
    }

    public interface OnClickListener{
        void onClick(int type, String name);
    }
}
