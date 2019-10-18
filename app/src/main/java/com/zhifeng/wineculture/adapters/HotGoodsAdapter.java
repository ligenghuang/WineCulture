package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.HomeDto;
import com.zhifeng.wineculture.ui.home.AdvertisingActivity;

/**
  *
  * @ClassName:     热销商品列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/11 11:09
  * @Version:        1.0
 */

public class HotGoodsAdapter extends BaseRecyclerAdapter<HomeDto.DataBean.HotGoodsBean> {
    Context context;

    public HotGoodsAdapter(Context context) {
        super(R.layout.layout_item_hot_goods);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, HomeDto.DataBean.HotGoodsBean model, int position) {
        holder.setIsRecyclable(false);
        ImageView img = holder.itemView.findViewById(R.id.iv_item_hot_goods);
        GlideUtil.setImage(context,model.getImage(),img,R.drawable.icon_good_detail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertisingActivity.class);
                intent.putExtra("url",model.getUrl());
                intent.putExtra("title",model.getTitle());
                context.startActivity(intent);
            }
        });
    }
}
