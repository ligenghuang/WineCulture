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
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BindBankCardAction;
import com.zhifeng.wineculture.modules.BindBankCardDto;
import com.zhifeng.wineculture.ui.impl.BindBankCardView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class BindBankCardActivity extends UserBaseActivity<BindBankCardAction> implements BindBankCardView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etBankCard)
    EditText etBankCard;
    @BindView(R.id.etBankName)
    EditText etBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_bindbankcard;
    }

    @Override
    protected BindBankCardAction initAction() {
        return new BindBankCardAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;
        String bankName = getIntent().getStringExtra("bankName");
        String bankCard = getIntent().getStringExtra("bankCard");
        etBankName.setText(bankName);
        etBankCard.setText(bankCard);
        if (TextUtils.isEmpty(bankName)) {
            etBankName.requestFocus();
        }
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
                .addTag("BindBankCardActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.bindbankcard_title));
    }

    @Override
    public void bindBankCard() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            String card = etBankCard.getText().toString();
            if (TextUtils.isEmpty(card)) {
                showNormalToast(R.string.bindbankcard_cardhint);
                return;
            }
            if (!ValidateUtils.isBankNo(card)) {
                showNormalToast(R.string.bindbankcard_rightcardhint);
                return;
            }
            String bankName = etBankName.getText().toString();
            if (TextUtils.isEmpty(bankName)) {
                showNormalToast(R.string.bindbankcard_banknamehint);
                return;
            }
            baseAction.bindBankCard(card, bankName);
        }
    }

    @Override
    public void bindBankCardSuccess(BindBankCardDto bindBankCardDto) {
        showNormalToast(bindBankCardDto.getMsg());
        setResult(12);
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
        bindBankCard();
    }
}
