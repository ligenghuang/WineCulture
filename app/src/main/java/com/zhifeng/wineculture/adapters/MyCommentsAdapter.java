package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.MyCommentListDto;
/**
 * @ClassName:
 * @Description: 我的-我的评论
 * @Author: Administrator
 * @Date: 2019/10/14 11:26
 */
public class MyCommentsAdapter extends BaseRecyclerAdapter<MyCommentListDto.DataBean> {
    private Context context;
    private OnButtonClickListener onButtonClickListener;

    public MyCommentsAdapter(Context context) {
        super(R.layout.item_mycomments);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, MyCommentListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView iv=holder.itemView.findViewById(R.id.iv_item_goods);
        GlideUtil.setImage(context,model.getImg(),iv,R.drawable.icon_goods_img);
        holder.text(R.id.tvGoodsName, model.getGoods_name());
        holder.text(R.id.tvGoodsPrice, ResUtil.getFormatString(R.string.mycomment_goodPrice,model.getGoods_price()));
        holder.text(R.id.tvGoodsNum, ResUtil.getFormatString(R.string.mycomment_goodNum,model.getGoods_num()));
        holder.text(R.id.tvTotalGoodsNum, ResUtil.getFormatString(R.string.mycomment_totalGoodNum,model.getGoods_num()));
        holder.text(R.id.tvTotalGoodsPrice, ResUtil.getFormatString(R.string.mycomment_totalGoodPrice,model.getOrder_amount()));
        TextView tvComment = holder.itemView.findViewById(R.id.tvComment);
        tvComment.setOnClickListener(v -> {
            if (onButtonClickListener != null) {
                onButtonClickListener.onClick(model.getGoods_id());
            }
        });
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }

    public interface OnButtonClickListener {
        void onClick(int goods_id);
    }
}
