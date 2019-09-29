package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.WithdrawalAction;
import com.zhifeng.wineculture.ui.impl.WithdrawalView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 提现
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 12:18
 * @Version: 1.0
 */

public class WithdrawalActivity extends UserBaseActivity<WithdrawalAction> implements WithdrawalView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_withdrawal_balance)
    TextView tvWithdrawalBalance;
    @BindView(R.id.tv_withdrawal_limit)
    TextView tvWithdrawalLimit;
    @BindView(R.id.ll_pay_type_ali)
    LinearLayout llPayTypeAli;
    @BindView(R.id.ll_pay_type_bank)
    LinearLayout llPayTypeBank;
    @BindView(R.id.et_withdrawal_money)
    EditText etWithdrawalMoney;
    @BindView(R.id.tv_withdrawal_money_poundage)
    TextView tvWithdrawalMoneyPoundage;
    @BindView(R.id.et_withdrawal_account)
    EditText etWithdrawalAccount;

    @Override
    public int intiLayout() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected WithdrawalAction initAction() {
        return new WithdrawalAction(this, this);
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
                .addTag("WithdrawalActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.balance_tab_5));
    }

    /**
     * 获取余额
     */
    @Override
    public void getBalance() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getBalance();
        }
    }

    @Override
    public void getBalanceSuccess() {

    }

    /**
     * 提交提现
     */
    @Override
    public void withdrawal() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.withdrawal();
        }
    }

    @Override
    public void withdrawalSuccess() {

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

    @OnClick({R.id.ll_pay_type_ali, R.id.ll_pay_type_bank, R.id.et_withdrawal_account, R.id.tv_withdrawal_money_all, R.id.tv_withdrawal_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_type_ali:
                //todo 支付宝
                break;
            case R.id.ll_pay_type_bank:
                //todo 银行卡
                break;
            case R.id.et_withdrawal_account:
                //todo 账号
                break;
            case R.id.tv_withdrawal_money_all:
                //todo 全部提现
                break;
            case R.id.tv_withdrawal_confirm:
                //todo 提交
                break;
        }
    }

}
