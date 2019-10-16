package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.SearchGoodsDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.data.MySp;

/**
  *
  * @ClassName:     搜索商品列表 适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/16 11:52
  * @Version:        1.0
 */

public class SearchGoodsAdapter extends BaseRecyclerAdapter<SearchGoodsDto.DataBeanX.ListBean.DataBean> {
    Context context;
//    OnClickListener onClickListener;
//
//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }

    public SearchGoodsAdapter(Context context) {
        super(R.layout.layout_item_search);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, SearchGoodsDto.DataBeanX.ListBean.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_search_goods_name,model.getGoods_name());
        holder.text(R.id.tv_item_search_goods_price,"￥"+model.getPrice());
        holder.text(R.id.tv_item_search_goods_sales, ResUtil.getFormatString(R.string.search_tab_8,model.getNumber_sales()+""));
        ImageView img = holder.itemView.findViewById(R.id.iv_item_search_goods);
        GlideUtil.setImage(context,model.getPicture(),img,R.drawable.icon_goods_img);

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

//        holder.itemView.findViewById(R.id.iv_item_search_add_cart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickListener.onGoodsClick(model.getGoods_id());
//            }
//        });
    }

//    public interface OnClickListener{
//        void onGoodsClick(int id);
//    }
}
