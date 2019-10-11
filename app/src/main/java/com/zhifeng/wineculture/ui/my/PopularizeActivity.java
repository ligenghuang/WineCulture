package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ShareUrlAction;
import com.zhifeng.wineculture.modules.ShareUrlDto;
import com.zhifeng.wineculture.ui.impl.ShareUrlView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 我要推广
 * @Author: Administrator
 * @Date: 2019/9/28 18:07
 */
public class PopularizeActivity extends UserBaseActivity<ShareUrlAction> implements ShareUrlView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserId)
    TextView tvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
        getShareUrl();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_popularize;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("PopularizeActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.popularize_invitationCode);
    }

    @Override
    protected ShareUrlAction initAction() {
        return new ShareUrlAction(this, this);
    }

    @Override
    public void getShareUrl() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getShareUrl();
        }
    }

    @Override
    public void getShareUrlSuccess(ShareUrlDto shareUrlDto) {
        ShareUrlDto.DataBean dataBean = shareUrlDto.getData();
        GlideUtil.setImageCircle(mContext, dataBean.getAvatar(), ivAvatar, R.drawable.icon_avatar);
        tvUserName.setText(dataBean.getRealname());
        tvUserId.setText(String.valueOf(dataBean.getId()));
    }

    @Override
    public void onError(String message, int code) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnRegister();
    }

    @OnClick(R.id.f_right_iv)
    public void OnClick(View view) {

    }
}
