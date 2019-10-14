package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.FootPrintDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;

/**
  *
  * @ClassName:     我的足迹列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 12:08
  * @Version:        1.0
 */

public class MyFootPrintAdapter extends BaseRecyclerAdapter<FootPrintDto.DataBean> {
    Context context;

    boolean isManagement = false;

    OnClickListener onClickListener;

    public MyFootPrintAdapter(Context context) {
        super(R.layout.item_myfootprint);
        this.context = context;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 是否显示选择按钮
     * @param management
     */
    public void setManagement(boolean management) {
        isManagement = management;

    }


    @Override
    protected void onBindViewHolder(SmartViewHolder holder, FootPrintDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tvGoodsName,model.getGoods_name());
        holder.text(R.id.tvGoodsPrice,"￥"+model.getPrice());
        TextView textView = holder.itemView.findViewById(R.id.tvGoodsOriginPrice);
        textView.setText("￥"+model.getOriginal_price());
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        ImageView img = holder.itemView.findViewById(R.id.iv_item_goods);
        GlideUtil.setImage(context,model.getImg(),img);

        ImageView imageView = holder.itemView.findViewById(R.id.iv_item_cart);
        imageView.setVisibility(isManagement? View.VISIBLE:View.GONE);
        imageView.setSelected(model.isClick());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isManagement){
                    onClickListener.onClick(model.getGoods_id());
                }else {
                    //todo 跳转至商品详情页
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("goods_id",model.getGoods_id());
                    context.startActivity(intent);
                }
            }
        });
    }

    public interface OnClickListener{
        void onClick(int id);
    }
}
