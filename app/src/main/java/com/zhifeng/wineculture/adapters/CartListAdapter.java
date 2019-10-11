package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.CartListDto;
import com.zhifeng.wineculture.ui.home.GoodsDetailActivity;
import com.zhifeng.wineculture.utils.Util;

/**
  *
  * @ClassName:     购物车列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/10 12:31
  * @Version:        1.0
 */

public class CartListAdapter extends BaseRecyclerAdapter<CartListDto.DataBean> {
    Context context;

    OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CartListAdapter(Context context) {
        super(R.layout.layout_item_cart);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder,CartListDto.DataBean model,int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_cart_goods_name,model.getGoods_name());//商品名称
        holder.text(R.id.tv_item_cart_goods_price,"￥"+model.getGoods_price());//商品价格
        holder.text(R.id.tv_item_cart_goods_spec,model.getSpec_key_name());
        ImageView goodsImg = holder.itemView.findViewById(R.id.iv_item_cart_img);
        GlideUtil.setImage(context,
                model.getImg(),
                goodsImg,
                R.drawable.icon_goods_img);//商品图片

        /************************************商品数量  start*************************************************/
        EditText editText = holder.itemView.findViewById(R.id.et_item_goods_num);
        editText.setText(model.getGoods_num()+"");

        int inventory = model.getInventory();

        /**
         * 输入框监听
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    editText.setText("1");
                    model.setGoods_num(1);
                }else {
                    int goodsNum = Integer.parseInt(editText.getText().toString());
                    if (goodsNum <= 0){
                        editText.setText("1");
                        model.setGoods_num(1);
                        ToastUtils.getToast().cancel();
                        ToastUtils.show(ResUtil.getString(R.string.cart_tab_7));
                    }else if (goodsNum > inventory){
                        model.setGoods_num(inventory);
                        editText.setText(inventory+"");

                        ToastUtils.getToast().cancel();
                        ToastUtils.show(ResUtil.getString(R.string.cart_tab_8));
                    }else {
                        model.setGoods_num(goodsNum);
                    }
                    L.e("lgh_cart","onTextChanged = "+model.getGoods_num());
                    onClickListener.goodsNumListener();
                }
                onClickListener.editGoodsNum(model.getSku_id(),model.getGoods_num());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextView subtract = holder.itemView.findViewById(R.id.tv_item_goods_subtract);
        TextView add = holder.itemView.findViewById(R.id.tv_item_goods_add);

        /**
         * 减
         */
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!Util.isFastDoubleClick()){
                   if (model.getGoods_num() == 1){
                       model.setGoods_num(1);
                       editText.setText(model.getGoods_num()+"");

                       ToastUtils.getToast().cancel();
                       ToastUtils.show(ResUtil.getString(R.string.cart_tab_7));
                   }else {
                       int num = model.getGoods_num()-1;
                       model.setGoods_num(num);
                       editText.setText(num+"");
                   }
                   L.e("lgh_cart","subtract = "+model.getGoods_num());
               }else {
                   ToastUtils.getToast().cancel();
                   ToastUtils.show(ResUtil.getString(R.string.cart_tab_9));
               }
            }
        });

        /**
         * 加
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Util.isFastDoubleClick()){
                if (model.getGoods_num() >= inventory){
                    ToastUtils.getToast().cancel();
                    ToastUtils.show(ResUtil.getString(R.string.cart_tab_8));
                }else {
                    int num = model.getGoods_num()+1;
                    model.setGoods_num(num);
                    editText.setText(model.getGoods_num()+"");
                }
                L.e("lgh_cart","add = "+model.getGoods_num());
                }else {
                    ToastUtils.getToast().cancel();
                    ToastUtils.show(ResUtil.getString(R.string.cart_tab_9));
                }
            }
        });
        /***********************************商品数量 end*****************************************/

        ImageView imageView = holder.itemView.findViewById(R.id.iv_item_cart);
        imageView.setSelected(model.getSelected() == 1);//商品是否选中

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.selectedListener(model.getCart_id());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goods_id",model.getGoods_id());
                context.startActivity(intent);
            }
        });

    }

    public interface OnClickListener{
        void selectedListener(int id);
        void goodsNumListener();
        void editGoodsNum(int id, int num);
    }
}
