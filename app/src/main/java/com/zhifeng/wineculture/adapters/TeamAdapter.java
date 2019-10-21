package com.zhifeng.wineculture.adapters;

import android.widget.TextView;

import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.MyTeamDto;
/**
 * @ClassName:
 * @Description: 我的团队
 * @Author: Administrator
 * @Date: 2019/10/14 10:23
 */
public class TeamAdapter extends BaseRecyclerAdapter<MyTeamDto.DataBean.ListBean> {
    private OnClickListener onClickListener;

    public TeamAdapter() {
        super(R.layout.item_myteam);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, MyTeamDto.DataBean.ListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tvUserId, String.valueOf(model.getId()));
        holder.text(R.id.tvUserName, model.getRealname());
        String mobile = model.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        holder.text(R.id.tvMobile,mobile);
        TextView tv=holder.itemView.findViewById(R.id.tvOrder);
        tv.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(model.getId());
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int id);
    }
}
