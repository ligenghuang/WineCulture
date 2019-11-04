package com.zhifeng.wineculture.adapters;

import android.view.View;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.ClassifyDto;


/**
  *
  * @ClassName:     商品分类列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/10 10:35
  * @Version:        1.0
 */

public class CategoryListAdapter extends BaseRecyclerAdapter<ClassifyDto.DataBean> {
    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CategoryListAdapter() {
        super(R.layout.layout_item_level_1);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, ClassifyDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_level_1,model.getCat_name());
        holder.itemView.setSelected(model.isClick());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnListClick(model.getCat_id(),model,position);
            }
        });
    }

    public interface OnClickListener{
        void OnListClick(int id, ClassifyDto.DataBean goodsBean,int position);
    }
}
