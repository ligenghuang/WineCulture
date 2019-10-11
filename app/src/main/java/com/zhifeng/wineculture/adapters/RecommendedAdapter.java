package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.HomeDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.data.MySp;

/**
 * @ClassName: 首页推荐商品列表适配器
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/11 10:43
 * @Version: 1.0
 */

public class RecommendedAdapter extends BaseRecyclerAdapter<HomeDto.DataBean.TuijianBean> {
    Context context;

    public RecommendedAdapter( Context context) {
        super(R.layout.layout_item_advertising);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, HomeDto.DataBean.TuijianBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_goods_name,model.getGoods_name());//todo 商品名称
        holder.text(R.id.tv_item_goods_price,"￥"+model.getPrice());//todo 价格
        ImageView img = holder.itemView.findViewById(R.id.iv_item_goods_img);
        GlideUtil.setImage(context,model.getImg(),img,R.drawable.icon_goods_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySp.iSLoginLive(context)) {
                    //todo 未登录
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("goods_id", model.getGoods_id());
                    context.startActivity(intent);
                }
            }
        });
    }
}
