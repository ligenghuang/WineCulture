package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OrderCommentListDto;

public class CommentAdapter extends BaseRecyclerAdapter<OrderCommentListDto.DataBean> {
    private Context context;

    public CommentAdapter(Context context) {
        super(R.layout.item_activitycomment);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderCommentListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivGoods = holder.itemView.findViewById(R.id.ivGoods);
        GlideUtil.setImage(context, model.getImg(), ivGoods,R.drawable.icon_goods_img);
        holder.text(R.id.tvGoodsName, model.getGoods_name());
    }
}
