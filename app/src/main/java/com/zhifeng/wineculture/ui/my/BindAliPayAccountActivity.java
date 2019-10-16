package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BindAliPayAccountAction;
import com.zhifeng.wineculture.modules.BindAliPayDto;
import com.zhifeng.wineculture.ui.impl.BindAliPayAccountView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class BindAliPayAccountActivity extends UserBaseActivity<BindAliPayAccountAction> implements BindAliPayAccountView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etAliPay)
    EditText etAliPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_bindalipayaccount;
    }

    @Override
    protected BindAliPayAccountAction initAction() {
        return new BindAliPayAccountAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;
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
                .addTag("BindAliPayAccountActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.bindalipayaccount_title));
    }

    @Override
    public void bindAliPayAccount() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            String userName = etUserName.getText().toString();
            if (TextUtils.isEmpty(userName)) {
                showNormalToast(R.string.bindalipayaccount_namehint);
                return;
            }
            String aliPay = etAliPay.getText().toString();
            if (TextUtils.isEmpty(aliPay)) {
                showNormalToast(R.string.bindalipayaccount_alipayhint);
                return;
            }
            baseAction.bindAliPayAccount(userName, aliPay);
        }
    }

    @Override
    public void bindAliPayAccountSuccess(BindAliPayDto bindAliPayDto) {
        showNormalToast(bindAliPayDto.getMsg());
        setResult(11);
        finish();
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
        baseAction.toUnregister();
    }

    @OnClick(R.id.tvConfirm)
    public void onClick(View view) {
        bindAliPayAccount();
    }
}
