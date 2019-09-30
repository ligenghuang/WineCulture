package com.zhifeng.wineculture.ui.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CartAction;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.CartView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 购物车
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/30 15:39
 * @Version: 1.0
 */

public class CartActivity extends UserBaseActivity<CartAction> implements CartView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.tv_cart_detele)
    TextView tvCartDetele;
    @BindView(R.id.iv_cart_menu)
    ImageView ivCartMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_cart_selector_all)
    TextView tvCartSelectorAll;
    @BindView(R.id.tv_cart_total_price)
    TextView tvCartTotalPrice;
    @BindView(R.id.ll_cart_data)
    LinearLayout llCartData;
    @BindView(R.id.ll_cart_null)
    LinearLayout llCartNull;

    @Override
    public int intiLayout() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected CartAction initAction() {
        return new CartAction(this,this);
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("CartActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.cart_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    @Override
    public void getCartList() {

    }

    @Override
    public void getCartListSuccess() {

    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
        loadDiss();
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
        baseAction.toUnregister();
    }

    @OnClick({R.id.tv_cart_detele, R.id.iv_cart_menu, R.id.tv_cart_selector_all, R.id.tv_cart_settlement, R.id.tv_to_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cart_detele:
                //todo 删除
                break;
            case R.id.iv_cart_menu:
                //todo 导航栏右边菜单
                new XPopup.Builder(mActicity)
                        .hasShadowBg(false)
                        .atView(ivCartMenu)
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asAttachList(new String[]{"首页", "分类", "购物车", "我的"},
                                new int[]{R.drawable.icon_home_y,R.drawable.icon_classify_y,
                                        R.drawable.icon_shopping_cart_y,R.drawable.icon_my_y},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        //todo 点击事件
                                        if (position != 2) {
                                            MainActivity.Position = position;
                                            ((MainActivity)mActicity).setSelectedLin();
                                            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class,CartActivity.class);
                                            finish();
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.tv_cart_selector_all:
                //todo 全选或取消全选
                break;
            case R.id.tv_cart_settlement:
                //todo 结算
                break;
            case R.id.tv_to_home:
                //todo 返回首页
                MainActivity.Position = 0;
                ((MainActivity)mActicity).setSelectedLin();
                ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class,CartActivity.class);
                finish();
                break;
        }
    }
}
