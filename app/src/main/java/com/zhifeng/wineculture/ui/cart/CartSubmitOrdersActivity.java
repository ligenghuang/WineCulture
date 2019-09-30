package com.zhifeng.wineculture.ui.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CartSubmitOrdersAction;
import com.zhifeng.wineculture.ui.impl.CartSubmitOrdersView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 购物车提交订单
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/30 9:31
 * @Version: 1.0
 */

public class CartSubmitOrdersActivity extends UserBaseActivity<CartSubmitOrdersAction> implements CartSubmitOrdersView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.tv_submit_order_consignee)
    TextView tvSubmitOrderConsignee;
    @BindView(R.id.tv_submit_order_phone)
    TextView tvSubmitOrderPhone;
    @BindView(R.id.tv_submit_order_address)
    TextView tvSubmitOrderAddress;
    @BindView(R.id.rl_submit_order_address)
    RelativeLayout rlSubmitOrderAddress;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_submit_orders)
    TextView tvSubmitOrders;

    @Override
    public int intiLayout() {
        return R.layout.activity_cart_submit_orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected CartSubmitOrdersAction initAction() {
        return new CartSubmitOrdersAction(this, this);
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
                .addTag("CartSubmitOrdersActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.cart_submit_orders_tab_8));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 获取订单信息
     */
    @Override
    public void getCartOrder() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getCartOrder();
        }
    }

    /**
     * 获取订单信息 成功
     */
    @Override
    public void getCartOrderSuccess() {

    }

    /**
     * 提交订单
     */
    @Override
    public void submitOrders() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.submitOrders();
        }
    }

    /**
     * 提交订单成功
     */
    @Override
    public void submitOrdersSuccess() {

    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
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

    @OnClick({R.id.ll_address, R.id.tv_submit_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                //todo 跳转至地址管理页面
                break;
            case R.id.tv_submit_orders:
                //todo 提交订单
                break;
        }
    }
}
