package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lxj.xpopup.XPopup;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ShareUrlAction;
import com.zhifeng.wineculture.modules.SharePosterDto;
import com.zhifeng.wineculture.ui.MainActivity;
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
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.f_right_iv)
    ImageView fRightIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserId)
    TextView tvUserId;
    @BindView(R.id.iv)
    ImageView iv;

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
    public void getShareUrlSuccess(SharePosterDto sharePosterDto) {
        SharePosterDto.DataBean dataBean = sharePosterDto.getData();
        GlideUtil.setImageCircle(mContext, dataBean.getAvatar(), ivAvatar, R.drawable.icon_avatar);
        tvUserName.setText(dataBean.getRealname());
        tvUserId.setText(String.valueOf(dataBean.getId()));
        GlideUtil.setImage(mContext, sharePosterDto.getData().getQrcode(), iv);
    }

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
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
        //todo 导航栏右边菜单
        new XPopup.Builder(mActicity)
                .hasShadowBg(false)
                .atView(view)
                .hasStatusBarShadow(true) //启用状态栏阴影
                .asAttachList(new String[]{"首页", "分类", "购物车", "我的"},
                        new int[]{R.drawable.icon_home_y, R.drawable.icon_classify_y,
                                R.drawable.icon_shopping_cart_y, R.drawable.icon_my_y},
                        (position, text) -> {
                            //todo 点击事件
                            MainActivity.Position = position;
                            startActivity(new Intent(mContext, MainActivity.class));
                            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class, PopularizeActivity.class);
                            finish();
                        })
                .show();
    }
}
