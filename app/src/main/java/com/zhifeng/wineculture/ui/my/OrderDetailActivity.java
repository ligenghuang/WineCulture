package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.OrderDetailAction;
import com.zhifeng.wineculture.adapters.GoodsDetailGoodResAdapter;
import com.zhifeng.wineculture.adapters.PayTypeAdapter;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.PayTypeDto;
import com.zhifeng.wineculture.modules.Temporary;
import com.zhifeng.wineculture.ui.impl.OrderDetailView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;
import com.zhifeng.wineculture.utils.data.MySp;
import com.zhifeng.wineculture.utils.dialog.BuyPwdDialog;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 订单详情
 * @Author: Administrator
 * @Date: 2019/10/15 15:56
 */
public class OrderDetailActivity extends UserBaseActivity<OrderDetailAction> implements OrderDetailView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvGoodsStatus)
    TextView tvGoodsStatus;
    @BindView(R.id.llGoodsStatus)
    LinearLayout llGoodsStatus;
    @BindView(R.id.tvReceiver)
    TextView tvReceiver;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tvTotalGoodsPrice)
    TextView tvTotalGoodsPrice;
    @BindView(R.id.tvRemainder)
    TextView tvRemainder;
    @BindView(R.id.tvPostage)
    TextView tvPostage;
    @BindView(R.id.tvpayPrice)
    TextView tvpayPrice;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    @BindView(R.id.tvOrderNo)
    TextView tvOrderNo;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.llButtons)
    LinearLayout llButtons;
    @BindView(R.id.btnLeft)
    Button btnLeft;
    @BindView(R.id.btnRight)
    Button btnRight;
    @BindView(R.id.rvPayType)
    RecyclerView rvPayType;
    @BindView(R.id.cd_pay_type)
    CardView llPayType;
    private String order_id;
    private int status;
    private final int REQUEST_CODE = 0;
    BuyPwdDialog bugPwdDialog;

    double money = 0;
    String payTypeNam;
    int payType;
    int pwd;
    private PayTypeAdapter payTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
        getOrderDetail();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected OrderDetailAction initAction() {
        return new OrderDetailAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
        order_id = getIntent().getStringExtra("order_id");

        rvPayType.setLayoutManager(new LinearLayoutManager(mContext));
        payTypeAdapter = new PayTypeAdapter(R.layout.layout_item_paytype2);
        rvPayType.setAdapter(payTypeAdapter);
        getPayType();
        loadView();
    }

    @Override
    protected void loadView() {
        payTypeAdapter.setOnClickListener((type, name) -> {
            List<Temporary.DataBean.PayTypeBean> list = payTypeAdapter.getAllData();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSelect(list.get(i).getPay_type() == type);
            }
            payTypeNam = name;
            payType = type;
            payTypeAdapter.notifyDataSetChanged();
        });
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
                .addTag("OrderDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.orderdetail_title);
    }

    @Override
    public void getOrderDetail() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getOrderDetail(order_id);
        }
    }

    @Override
    public void getOrderDetailSuccess(OrderDetailDto orderDetailDto) {
        //1待付款 2待发货 3待收货 4待评价 5已取消 6待退款 7已退款 8拒绝退款
        status = orderDetailDto.getData().getStatus();
        int drawableRes = 0;
        int stringRes = 0;
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        llPayType.setVisibility(View.GONE);
        switch (status) {
            case 1:
                drawableRes = R.drawable.icon_wait_pa_bg;
                stringRes = R.string.orderdetail_waitToPay;
                btnLeft.setText(R.string.orderdetail_cancel);
                btnRight.setText(R.string.orderdetail_payNow2);
                OrderDetailDto.DataBean dataBean = orderDetailDto.getData();
                double totalPrice = 0;
                for (OrderDetailDto.DataBean.GoodsResBean goodsResBean : dataBean.getGoods_res()) {
                    totalPrice += Double.parseDouble(goodsResBean.getGoods_price());
                }
                int pay_type = dataBean.getPay_type().getPay_type();
                Object[] data = {totalPrice, pay_type};
                btnRight.setTag(data);
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                llPayType.setVisibility(View.VISIBLE);
                break;
            case 2:
                drawableRes = R.drawable.icon_wait_receive_bg;
                stringRes = R.string.orderdetail_packaging;
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText(R.string.myorder_returnofgoods);
                break;
            case 3:
                drawableRes = R.drawable.icon_wait_receive_bg;
                stringRes = R.string.orderdetail_transit;
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText(R.string.myorder_confirmtakeover);
                btnRight.setText(R.string.myorder_lookuplogistics);
                break;
            case 4:
                //0 未评论 1已评论
                int comment = orderDetailDto.getData().getComment();
                drawableRes = comment == 0 ? R.drawable.icon_wait_evaluation_bg : R.drawable.icon_sign_in_bg;
                stringRes = comment == 0 ? R.string.orderdetail_tobecomment : R.string.orderdetail_finish;
                if (comment == 0) {
                    btnRight.setVisibility(View.VISIBLE);
                    btnRight.setTag(comment);
                    btnRight.setText(R.string.myorder_comment);
                } else {
                    llButtons.setVisibility(View.GONE);
                }
                break;
            case 5:
                llButtons.setVisibility(View.GONE);
                drawableRes = R.drawable.icon_sign_in_bg;
                stringRes = R.string.myorder_hadCancel;
                break;
            case 6:
                llButtons.setVisibility(View.GONE);
                drawableRes = R.drawable.icon_sign_in_bg;
                stringRes = R.string.myorder_toBeRefund;
                break;
            case 7:
                llButtons.setVisibility(View.GONE);
                drawableRes = R.drawable.icon_sign_in_bg;
                stringRes = R.string.myorder_hadRefund;
                break;
            case 8:
                llButtons.setVisibility(View.GONE);
                drawableRes = R.drawable.icon_sign_in_bg;
                stringRes = R.string.myorder_refuseRefund;
                break;
        }
        if (drawableRes != 0) {
            llGoodsStatus.setBackgroundResource(drawableRes);
        }
        if (stringRes != 0) {
            tvGoodsStatus.setText(stringRes);
        }
        tvReceiver.setText(orderDetailDto.getData().getConsignee());
        tvMobile.setText(orderDetailDto.getData().getMobile());
        tvAddress.setText(orderDetailDto.getData().getAddress());
        tvAddress.setText(orderDetailDto.getData().getAddress());
        GoodsDetailGoodResAdapter adapter = new GoodsDetailGoodResAdapter(mContext);
        adapter.refresh(orderDetailDto.getData().getGoods_res());
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        tvTotalGoodsPrice.setText(ResUtil.getFormatString(R.string.orderdetail_goodsPrice, orderDetailDto.getData().getOrder_amount()));
        tvRemainder.setText(ResUtil.getFormatString(R.string.orderdetail_goodsPrice, orderDetailDto.getData().getUser_money()));
        tvPostage.setText(ResUtil.getFormatString(R.string.orderdetail_goodsPrice, orderDetailDto.getData().getShipping_price()));
        tvpayPrice.setText(ResUtil.getFormatString(R.string.orderdetail_goodsPrice, orderDetailDto.getData().getTotal_amount()));
//        String user_note = orderDetailDto.getData().getUser_note();
//        tvRemark.setText(TextUtils.isEmpty(user_note) ? ResUtil.getString(R.string.orderdetail_remark_no) : user_note);
        tvOrderNo.setText(orderDetailDto.getData().getOrder_sn());
        tvCreateTime.setText(DynamicTimeFormat.LongToString2(orderDetailDto.getData().getAdd_time() * (long) 1000));
        money = Double.parseDouble(orderDetailDto.getData().getTotal_amount());
        pwd = orderDetailDto.getData().getIs_pwd();
        OrderDetailDto.DataBean.PayTypeBean payTypeBean = orderDetailDto.getData().getPay_type();
        List<Temporary.DataBean.PayTypeBean> list = payTypeAdapter.getAllData();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelect(list.get(i).getPay_type() == payTypeBean.getPay_type());
        }
        payTypeNam = payTypeBean.getPay_name();
        payType = payTypeBean.getPay_type();
        payTypeAdapter.notifyDataSetChanged();
    }

    /**
     * 获取支付方式
     */
    @Override
    public void getPayType() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getPayType();
        }
    }

    /**
     * 获取支付方式 成功
     *
     * @param payTypeDto
     */
    @Override
    public void getPayTypeSuccess(PayTypeDto payTypeDto) {
        List<Temporary.DataBean.PayTypeBean> payTypeBeans = payTypeDto.getData();
        Temporary.DataBean.PayTypeBean payTypeBean = payTypeBeans.get(0);
        payTypeBean.setSelect(true);
        payTypeNam = payTypeBean.getPay_name();
        payType = payTypeBean.getPay_type();
        payTypeAdapter.refresh(payTypeBeans);
    }

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
    }

    @Override
    public void cancelOrderOrConfirmToReceive(int status) {
        //todo 取消订单/确认收货
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.cancelOrderOrConfirmToReceive(order_id, status);
        }
    }

    @Override
    public void cancelOrderOrConfirmToReceiveSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getMsg());
        finish();
    }

    @Override
    public void cancelOrderOrConfirmToReceiveFail(int code, String msg) {
        showNormalToast(msg);
    }

    @Override
    public void pay() {
        //todo 立即支付
        if (CheckNetwork.checkNetwork2(mContext)) {
            switch (payType) {
                case 1:
                    //余额支付
                    if (pwd == 1) {
                        bugPwdDialog = new BuyPwdDialog(mContext, R.style.MY_AlertDialog, money, "余额支付");
                        bugPwdDialog.setOnFinishInput(new BuyPwdDialog.OnFinishInput() {
                            @Override
                            public void inputFinish(String password) {
                                loadDialog();
                                //支付订单
                                baseAction.pay(order_id, payType, password);
                            }

                            @Override
                            public void close() {

                            }
                        });
                        bugPwdDialog.show();
                    } else {
                        showToast(ResUtil.getString(R.string.goods_detail_tab_30));
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(mContext, ForgetPwdActivity.class);
                            intent.putExtra("phone", MySp.getMobile(mContext));
                            intent.putExtra("type", 1);
                            intent.putExtra("isOrder", true);
                            startActivity(intent);
                        }, 1000);
                    }
                    break;
                default:
                    showNormalToast("暂无开通其他支付方式");
                    break;
            }
        }
    }

    @Override
    public void paySuccess(PayOrderDto payOrderDto) {
        loadDiss();
        showNormalToast(ResUtil.getString(R.string.goods_detail_tab_29));
        if (bugPwdDialog != null) {
            bugPwdDialog.dismiss();
        }
        getOrderDetail();
    }

    @Override
    public void payFail(String msg) {
        loadDiss();
        showNormalToast(msg);
    }

    @Override
    public void refund() {
        //todo 退货
        Intent intent = new Intent(mContext, RefundActivity.class);
        intent.putExtra("order_id", order_id);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void lookUpLogistics() {
        //todo 查看物流
        Intent logistics = new Intent(mContext, LogisticsActivity.class);
        logistics.putExtra("orderId", Integer.parseInt(order_id));
        startActivity(logistics);
    }

    @Override
    public void comment() {
        //todo 评价
        Intent intent = new Intent(mContext, CommentActivity.class);
        intent.putExtra("order_id", order_id);
        startActivityForResult(intent, REQUEST_CODE);
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

    @OnClick({R.id.btnLeft, R.id.btnRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:
                //1待付款 2待发货 3待收货 4待评价 5已取消 6待退款 7已退款 8拒绝退款
                switch (status) {
                    case 1:
                        //todo 取消订单
                        cancelOrderOrConfirmToReceive(1);
                        break;
                    case 3:
                        //todo 确认收货
                        cancelOrderOrConfirmToReceive(3);
                        break;
                }
                break;
            case R.id.btnRight:
                //1待付款 2待发货 3待收货 4待评价 5已取消 6待退款 7已退款 8拒绝退款
                switch (status) {
                    case 1:
                        //todo 立即支付
                        pay();
                        break;
                    case 2:
                        //todo 退货
                        refund();
                        break;
                    case 3:
                        //todo 查看物流
                        lookUpLogistics();
                        break;
                    case 4:
                        //todo 评价
                        comment();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            getOrderDetail();
        }
    }
}
