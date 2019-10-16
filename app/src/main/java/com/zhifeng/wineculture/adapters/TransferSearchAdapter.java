package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.SearchPhoneDto;

/**
 * @ClassName: 转账 搜索手机号
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/14 10:50
 * @Version: 1.0
 */

public class TransferSearchAdapter extends BaseRecyclerAdapter<SearchPhoneDto.DataBean> {
    Context context;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TransferSearchAdapter(Context context) {
        super(R.layout.layout_item_transfer_search);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, SearchPhoneDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_name, model.getRealname());
        holder.text(R.id.tv_item_id, "ID:" + model.getId());
        ImageView img = holder.itemView.findViewById(R.id.iv_item_avatar);
        GlideUtil.setImageCircle(context, model.getAvatar(), img, R.drawable.icon_avatar);

        holder.itemView.findViewById(R.id.tv_item_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(model);
            }
        });
    }

    public interface OnClickListener {
        void onClick(SearchPhoneDto.DataBean model);
    }
}
