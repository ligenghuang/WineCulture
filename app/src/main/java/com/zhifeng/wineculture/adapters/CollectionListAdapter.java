package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.CollectionListDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;

/**
  *
  * @ClassName:     关注列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/11 11:25
  * @Version:        1.0
 */

public class CollectionListAdapter extends BaseRecyclerAdapter<CollectionListDto.DataBean> {
    Context context;
    OnClickListener onClickListener;

    public CollectionListAdapter(Context context) {
        super(R.layout.item_mycollect);
        this.context = context;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    protected void onBindViewHolder(SmartViewHolder holder, CollectionListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tvGoodsName,model.getGoods_name());
        holder.text(R.id.tvGoodsPrice,"￥"+model.getPrice());
        TextView textView = holder.itemView.findViewById(R.id.tvGoodsOriginPrice);
        textView.setText("￥"+model.getOriginal_price());
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        ImageView img = holder.itemView.findViewById(R.id.iv_item_goods);
        GlideUtil.setImage(context,model.getImg(),img);

        holder.itemView.findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(model.getGoods_id());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   //todo 跳转至商品详情页
                   Intent intent = new Intent(context, GoodsDetailActivity.class);
                   intent.putExtra("goods_id",model.getGoods_id());
                   context.startActivity(intent);

            }
        });
    }

    public interface OnClickListener{
        void onClick(int id);
    }
}
