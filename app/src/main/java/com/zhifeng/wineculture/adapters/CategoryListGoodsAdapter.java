package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.ClassifyDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.data.MySp;

/**
 * @ClassName: 分类列表商品列表适配器
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/10 10:54
 * @Version: 1.0
 */

public class CategoryListGoodsAdapter extends BaseRecyclerAdapter<ClassifyDto.DataBean.GoodsBean> {
    Context context;

    public CategoryListGoodsAdapter(Context context) {
        super(R.layout.layout_item_level_goods);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, ClassifyDto.DataBean.GoodsBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_goods_name, model.getGoods_name());
        ImageView img = holder.itemView.findViewById(R.id.iv_item_goods);
        GlideUtil.setImage(context, model.getImg(), img, R.drawable.icon_good_detail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySp.iSLoginLive(context)) {
                    //todo 未登录
                    context.startActivity(new Intent(context,LoginActivity.class));
                } else {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("goods_id", model.getGoods_id());
                    context.startActivity(intent);
                }
            }
        });
    }
}
