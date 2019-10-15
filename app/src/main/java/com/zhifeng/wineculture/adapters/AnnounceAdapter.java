package com.zhifeng.wineculture.adapters;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.AnnounceDto;
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
    public AnnounceAdapter() {
        super(R.layout.layout_item_announce);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, AnnounceDto.DataBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_title,model.getTitle());
        holder.text(R.id.tv_item_desc,model.getBrief());
        long time = model.getCreate_time() * (long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString2(time));
    }
}
