package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.ui.impl.OrderDetailView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 订单详情
 * @Author: Administrator
 * @Date: 2019/10/15 15:56
 */
public class OrderDetailActivity extends UserBaseActivity<OrderDetailAction> implements OrderDetailView {
    @BindView(R.id.top_view)
    View topView;
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
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.cvDingwei)
    CardView cvDingwei;
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
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnPayNow)
    Button btnPayNow;
    private String order_id;

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
        int status = orderDetailDto.getData().getStatus();
        int drawableRes = 0;
        int stringRes = 0;
        switch (status) {
            case 1:
                drawableRes = R.drawable.icon_wait_pa_bg;
                stringRes = R.string.orderdetail_waitToPay;
                break;
            case 2:
                drawableRes = R.drawable.icon_wait_receive_bg;
                stringRes = R.string.orderdetail_packaging;
                break;
            case 3:
                drawableRes = R.drawable.icon_wait_receive_bg;
                stringRes = R.string.orderdetail_transit;
                break;
            case 4:
                //0 未评论 1已评论
                int comment = orderDetailDto.getData().getComment();
                drawableRes = comment == 0 ? R.drawable.icon_wait_evaluation_bg : R.drawable.icon_sign_in_bg;
                stringRes = comment == 0 ? R.string.orderdetail_tobecomment : R.string.orderdetail_finish;
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
        String user_note = orderDetailDto.getData().getUser_note();
        tvRemark.setText(TextUtils.isEmpty(user_note) ? ResUtil.getString(R.string.orderdetail_remark_no) : user_note);
        tvOrderNo.setText(orderDetailDto.getData().getOrder_sn());
        tvCreateTime.setText(DynamicTimeFormat.LongToString2(orderDetailDto.getData().getAdd_time() * (long) 1000));
    }

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
    }

    @Override
    public void cancelOrder() {

    }

    @Override
    public void cancelOrderSuccess(GeneralDto generalDto) {

    }

    @Override
    public void cancelOrderFail(int code, String msg) {

    }

    @Override
    public void pay() {

    }

    @Override
    public void paySuccess(GeneralDto generalDto) {

    }

    @Override
    public void payFail(int code, String msg) {

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

    @OnClick({R.id.btnCancel, R.id.btnPayNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                cancelOrder();
                break;
            case R.id.btnPayNow:
                pay();
                break;
        }
    }
}
