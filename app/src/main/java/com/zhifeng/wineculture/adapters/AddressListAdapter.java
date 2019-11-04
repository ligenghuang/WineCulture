package com.zhifeng.wineculture.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.AddressListDto;

/**
  *
  * @ClassName:     地址管理
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/12 16:01
  * @Version:        1.0
 */

public class AddressListAdapter extends BaseRecyclerAdapter<AddressListDto.DataBean> {
    OnClickListener onClickListener;
    boolean isGoods;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AddressListAdapter(boolean isGoods) {
        super(R.layout.layout_item_address);
        this.isGoods = isGoods;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, AddressListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_address_name,model.getConsignee());//收货人
        holder.text(R.id.tv_item_address_phone,model.getMobile());//联系电话
        holder.text(R.id.tv_item_address,model.getP_cn()+model.getC_cn()+model.getD_cn()+" "+model.getAddress());//详细地址

        TextView tvDefault = holder.itemView.findViewById(R.id.tv_item_default);
        tvDefault.setVisibility(model.getIs_default()==1? View.VISIBLE:View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemView(model);
            }
        });
        holder.itemView.findViewById(R.id.iv_detele).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.Detele(model.getAddress_id(),(model.getIs_default()==1||getAllData().size() == 1));
            }
        });

    }

    public interface OnClickListener{
        void Detele(int id,boolean b);
        void itemView(AddressListDto.DataBean model);
    }
}
