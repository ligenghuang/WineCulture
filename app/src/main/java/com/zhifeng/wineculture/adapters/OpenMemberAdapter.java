package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.OpenMemberDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.data.MySp;

/**
  *
  * @ClassName:     开通会员适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/17 12:02
  * @Version:        1.0
 */

public class OpenMemberAdapter extends BaseRecyclerAdapter<OpenMemberDto.DataBean>{
    Context context;
    public OpenMemberAdapter(Context context) {
        super(R.layout.layout_item_like_goods);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OpenMemberDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_like_goods_name,model.getGoods_name());
        holder.text(R.id.tv_item_like_goods_price,"￥"+model.getPrice());
        TextView textView = holder.itemView.findViewById(R.id.tv_item_like_goods_original_price);
        textView.setText("￥"+model.getOriginal_price());
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        ImageView img = holder.itemView.findViewById(R.id.iv_item_like_goods_img);
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
