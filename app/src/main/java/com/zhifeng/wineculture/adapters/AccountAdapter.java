package com.zhifeng.wineculture.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.modules.AccountDto;
import com.zhifeng.wineculture.utils.data.MySp;

public class AccountAdapter extends BaseRecyclerAdapter<AccountDto> {
    private Context context;

    public AccountAdapter(Context context) {
        super(R.layout.item_changeaccount);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, AccountDto model, int position) {
        holder.setIsRecyclable(false);
        ImageView ivAvatar = holder.itemView.findViewById(R.id.ivAvatar);
        GlideUtil.setImageCircle(context, model.getAvatar(), ivAvatar, R.drawable.icon_avatar);
        holder.text(R.id.tvUserName, model.getRealName());
        TextView tvCurrentAccount = holder.itemView.findViewById(R.id.tvCurrentAccount);
        tvCurrentAccount.setVisibility(model.getToken().equals(MySp.getAccessToken(context)) ? View.VISIBLE : View.GONE);
    }
}
