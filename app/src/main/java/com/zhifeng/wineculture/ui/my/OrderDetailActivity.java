package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.OrderDetailAction;
import com.zhifeng.wineculture.adapters.GoodsDetailGoodResAdapter;
import com.zhifeng.wineculture.modules.OrderDetailDto;
import com.zhifeng.wineculture.ui.impl.OrderDetailView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.DynamicTimeFormat;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.llDingwei)
    LinearLayout llDingwei;
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
    @BindView(R.id.btnPay)
    Button btnPay;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
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
        mActicity=this;
        mContext=this;
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
        tvGoodsStatus.setText("已发货");
        tvReceiver.setText(orderDetailDto.getData().getConsignee());
        tvMobile.setText(orderDetailDto.getData().getMobile());
        tvAddress.setText(orderDetailDto.getData().getAddress());
        tvAddress.setText(orderDetailDto.getData().getAddress());
        GoodsDetailGoodResAdapter adapter = new GoodsDetailGoodResAdapter(mContext);
        adapter.refresh(orderDetailDto.getData().getGoods_res());
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        tvTotalGoodsPrice.setText(orderDetailDto.getData().getOrder_amount());
        tvRemainder.setText(orderDetailDto.getData().getUser_money());
        tvPostage.setText(orderDetailDto.getData().getShipping_price());
        tvpayPrice.setText(orderDetailDto.getData().getTotal_amount());
        tvRemark.setText(orderDetailDto.getData().getUser_note());
        tvOrderNo.setText(orderDetailDto.getData().getOrder_sn());
        tvCreateTime.setText( DynamicTimeFormat.LongToString2(orderDetailDto.getData().getAdd_time()));
    }

    @Override
    public void onError(String message, int code) {

    }

    @OnClick({R.id.btnCancel, R.id.btnPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                break;
            case R.id.btnPay:
                break;
        }
    }
}
