package com.zhifeng.wineculture.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CartSubmitOrdersAction;
import com.zhifeng.wineculture.adapters.CartSubmitOrdersGoodsAdapter;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.SubmitOrderDto;
import com.zhifeng.wineculture.modules.Temporary;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;
import com.zhifeng.wineculture.ui.impl.CartSubmitOrdersView;
import com.zhifeng.wineculture.ui.my.AddressListActivity;
import com.zhifeng.wineculture.ui.my.ForgetPwdActivity;
import com.zhifeng.wineculture.ui.my.MyOrderActivity;
import com.zhifeng.wineculture.ui.my.OrderDetailActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;
import com.zhifeng.wineculture.utils.dialog.BuyPwdDialog;
import com.zhifeng.wineculture.utils.dialog.PayTypeDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    String cartId;
    double money = 0;
    String payTypeNam;
    int payType;
    int addressId = -1;
    BuyPwdDialog bugPwdDialog;
    int pwd;
    CartSubmitOrdersGoodsAdapter cartSubmitOrdersGoodsAdapter;
    PayTypeDialog payTypeDialog;

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
        cartId = getIntent().getStringExtra("cartId");

        cartSubmitOrdersGoodsAdapter = new CartSubmitOrdersGoodsAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(cartSubmitOrdersGoodsAdapter);

        getCartOrder();
        loadView();
    }

    /**
     * 获取订单信息
     */
    @Override
    public void getCartOrder() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getCartOrder(cartId);
        }
    }

    /**
     * 获取订单信息 成功
     */
    @Override
    public void getCartOrderSuccess(Temporary temporary) {
        loadDiss();
        Temporary.DataBean dataBean = temporary.getData();
        cartSubmitOrdersGoodsAdapter.refresh(dataBean.getGoods_res());
        if (dataBean.getAddr_res().getAddress_id() == 0) {
            rlSubmitOrderAddress.setVisibility(View.GONE);
            tvNoAddress.setVisibility(View.VISIBLE);
        } else {
            rlSubmitOrderAddress.setVisibility(View.VISIBLE);
            tvNoAddress.setVisibility(View.GONE);
            addressId = dataBean.getAddr_res().getAddress_id();
            tvSubmitOrderConsignee.setText(dataBean.getAddr_res().getConsignee());
            tvSubmitOrderPhone.setText(dataBean.getAddr_res().getMobile());
            tvSubmitOrderAddress.setText(dataBean.getAddr_res().getAddress());
        }
        //todo 订单总金额
        money = Double.parseDouble(dataBean.getOrder_amount());
        tvTotalAmount.setText("￥" + dataBean.getOrder_amount());
        pwd = dataBean.getPwd();
        payTypeDialog = new PayTypeDialog(mContext, R.style.MY_AlertDialog, dataBean.getPay_type());
        payTypeDialog.setOnClickListener(new PayTypeDialog.OnClickListener() {
            @Override
            public void onPayTypeClick(int type, String name) {
                payTypeNam = name;
                payType = type;
                if (payType == 1 && pwd == 0) {
                    showNormalToast(ResUtil.getString(R.string.goods_detail_tab_30));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, ForgetPwdActivity.class);
                            intent.putExtra("phone", MySp.getMobile(mContext));
                            intent.putExtra("type", 1);
                            intent.putExtra("isOrder", true);
                            startActivityForResult(intent, 201);
                        }
                    }, 2000);
                    payTypeDialog.dismiss();
                } else {
                    submitOrder();
                }

            }
        });
    }

    /**
     * 组装提交数据
     */
    private void submitOrder() {
        SubmitOrderPost post = new SubmitOrderPost();
        post.setCart_id(cartId);
        post.setAddress_id(addressId + "");
        post.setPay_type(payType + "");
        List<Temporary.DataBean.GoodsResBean> list = cartSubmitOrdersGoodsAdapter.getAllData();
        List<String> note = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            L.e("lgh_note","note  = "+list.get(i).getNote());
            note.add(list.get(i).getNote());
        }
        post.setUser_note(note);
        submitOrders(post);
    }

    /**
     * 提交订单
     */
    @Override
    public void submitOrders(SubmitOrderPost post) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.submitOrders(post);
        }
    }

    /**
     * 提交订单成功
     */
    @Override
    public void submitOrdersSuccess(SubmitOrderDto submitOrderDto) {
        loadDiss();
        if (payTypeDialog != null){
            payTypeDialog.dismiss();
        }
        loadDiss();
        switch (payType) {
            case 1:
                //余额支付
                if (pwd == 1) {
                    bugPwdDialog = new BuyPwdDialog(mContext, R.style.MY_AlertDialog, money, payTypeNam);
                    bugPwdDialog.setOnFinishInput(new BuyPwdDialog.OnFinishInput() {
                        @Override
                        public void inputFinish(String password) {
                            //支付订单
                            SubmitOrderPost post = new SubmitOrderPost();
                            post.setCart_id(submitOrderDto.getData());
                            post.setPay_type(payType + "");
                            post.setPwd(password);
                            payOrder(post);
                        }

                        @Override
                        public void close() {
                            //取消支付  跳转至订单详情页
                            Intent intent = new Intent(mContext, MyOrderActivity.class);
                            intent.putExtra("position", 1);
                            startActivity(intent);
                            finish();
                        }
                    });
                    bugPwdDialog.show();
                } else {
                    showNormalToast(ResUtil.getString(R.string.goods_detail_tab_30));
                }
                break;
            default:
                break;
        }

    }


    /**
     * 支付
     *
     * @param submitOrderPost
     */
    @Override
    public void payOrder(SubmitOrderPost submitOrderPost) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.payOrder(submitOrderPost);
        }
    }

    /**
     * 支付成功
     *
     * @param submitOrderDto
     */
    @Override
    public void payOrderSuccess(PayOrderDto submitOrderDto) {
        loadDiss();
        showNormalToast(ResUtil.getString(R.string.goods_detail_tab_29));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("order_id", submitOrderDto.getData().getOrder_id());
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    /**
     * 支付失败
     *
     * @param msg
     */
    @Override
    public void payOrderError(String msg) {
        L.e("lgh_pay", "输出返回结果4" + msg);
        loadDiss();
        showNormalToast(msg);
    }
    /**
     * 失败
     *
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
                Intent i = new Intent(mContext, AddressListActivity.class);
                i.putExtra("isGoods", true);
                startActivityForResult(i, 200);
                break;
            case R.id.tv_submit_orders:
                //todo 提交订单
                buyNow();
                break;
        }
    }

    /**
     * 提交订单
     */
    private void buyNow() {
        //未选择收货地址
        if (addressId == -1) {
            showNormalToast(ResUtil.getString(R.string.temporary_tab_1));
            return;
        }
        if (payTypeDialog != null) {
            payTypeDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 200) {
            if (data != null) {
                String address = data.getStringExtra("address2");
                String address_info = data.getStringExtra("address");
                String phone = data.getStringExtra("phone");
                String consignee = data.getStringExtra("consignee");
                addressId = data.getIntExtra("address_id", -1);
                tvSubmitOrderAddress.setText(address_info + " " + address);
                tvSubmitOrderConsignee.setText(consignee);
                tvSubmitOrderPhone.setText(phone);
                rlSubmitOrderAddress.setVisibility(View.VISIBLE);
                tvNoAddress.setVisibility(View.GONE);
            }
        }else if (resultCode == 201) {
            if (data != null) {
                pwd = data.getIntExtra("pwd", 0);
            }
        }
    }
}
