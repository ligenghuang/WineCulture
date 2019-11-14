package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.AnnounceDto;
import com.zhifeng.wineculture.ui.home.AnnounceDetailActivity;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

/**
  *
  * @ClassName:     公告列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 18:07
  * @Version:        1.0
 */

public class AnnounceAdapter extends BaseRecyclerAdapter<AnnounceDto.DataBean.ListBean> {
    Context context;

    public AnnounceAdapter(Context context) {
        super(R.layout.layout_item_announce);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, AnnounceDto.DataBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_title,model.getTitle());
        holder.text(R.id.tv_item_desc,model.getBrief());
        long time = model.getCreate_time() * (long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString2(time));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnnounceDetailActivity.class);
                intent.putExtra("id",model.getId());
                context.startActivity(intent);
            }
        });
    }
}
