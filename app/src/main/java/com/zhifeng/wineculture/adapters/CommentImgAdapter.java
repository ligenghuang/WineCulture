package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;

/**
  *
  * @ClassName:     评价图片列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/18 14:20
  * @Version:        1.0
 */

public class CommentImgAdapter extends BaseRecyclerAdapter<String>{
    Context context;
    public CommentImgAdapter(Context context) {
        super(R.layout.layout_item_comment_img);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
        holder.setIsRecyclable(false);
        ImageView imageView = holder.itemView.findViewById(R.id.iv_item_comment_img);
        GlideUtil.setImage(context,model,imageView,R.drawable.icon_comment_img);
    }
}
