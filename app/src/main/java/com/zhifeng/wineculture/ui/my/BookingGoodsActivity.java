package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BookingGoodsAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.ui.impl.BookingGoodsView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 我要预约领商品
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/30 10:37
 * @Version: 1.0
 */

public class BookingGoodsActivity extends UserBaseActivity<BookingGoodsAction> implements BookingGoodsView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_booking_goods_name)
    EditText etBookingGoodsName;
    @BindView(R.id.et_booking_goods_phone)
    EditText etBookingGoodsPhone;
    @BindView(R.id.et_booking_goods_address)
    EditText etBookingGoodsAddress;

    @Override
    public int intiLayout() {
        return R.layout.activity_booking_goods;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected BookingGoodsAction initAction() {
        return new BookingGoodsAction(this, this);
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
                .addTag("BookingGoodsActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.booking_goods_tab_4));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 预约领商品
     *
     * @param contact
     * @param mobile
     * @param address
     */
    @Override
    public void bookingGoods(String contact, String mobile, String address) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.bookingGoods(contact, mobile, address);
        }
    }

    /**
     * 预约领商品 成功
     *
     * @param generalDto
     */
    @Override
    public void bookingGoodsSuccess(GeneralDto generalDto) {
        loadDiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
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


    @OnClick(R.id.tv_save)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_save:
                save();
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(etBookingGoodsName.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.booking_goods_tab_6));
            return;
        }

        String contact = etBookingGoodsName.getText().toString();

        if (TextUtils.isEmpty(etBookingGoodsPhone.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.booking_goods_tab_7));
            return;
        }

        String mobile = etBookingGoodsPhone.getText().toString();

        if (!ValidateUtils.isPhone2(mobile)){
            showNormalToast(ResUtil.getString(R.string.booking_goods_tab_9));
            return;
        }

        if (TextUtils.isEmpty(etBookingGoodsAddress.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.booking_goods_tab_8));
            return;
        }
        String address = etBookingGoodsAddress.getText().toString();

        bookingGoods(contact,mobile,address);
    }
}
