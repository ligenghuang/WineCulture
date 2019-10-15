package com.zhifeng.wineculture.adapters;

import android.view.View;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.SearchGoodsHistoryDto;

/**
  *
  * @ClassName:     搜索历史列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 16:36
  * @Version:        1.0
 */

public class SearchGoodsHistoryAdapter extends BaseRecyclerAdapter<SearchGoodsHistoryDto.DataBean> {

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SearchGoodsHistoryAdapter() {
        super(R.layout.layout_item_search_history);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, SearchGoodsHistoryDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_history,model.getKeyword());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(model.getKeyword());
            }
        });
    }

    public interface OnClickListener{
        void onClick(String keyword);
    }
}
