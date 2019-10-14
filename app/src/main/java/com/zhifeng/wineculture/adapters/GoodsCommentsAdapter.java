package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.CommentsListDto;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

public class GoodsCommentsAdapter extends BaseRecyclerAdapter<CommentsListDto.DataBean> {
    private Context context;

    public GoodsCommentsAdapter(Context context) {
        super(R.layout.layout_item_goodscomments);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, CommentsListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivAvatar = holder.itemView.findViewById(R.id.ivAvatar);
        GlideUtil.setImageCircle(context, model.getAvatar(), ivAvatar, R.drawable.icon_goods_img);
        holder.text(R.id.tv_Mobile, model.getMobile());
        long time = model.getAdd_time() * (long) 1000;
        holder.text(R.id.tv_AddTime, DynamicTimeFormat.LongToString5(time));
        holder.text(R.id.tv_Comment, model.getContent());
        RecyclerView rv=holder.itemView.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(context,4));
        CommentImgAdapter adapter = new CommentImgAdapter(context);
        adapter.refresh(model.getImg());
        rv.setAdapter(adapter);
    }
}
