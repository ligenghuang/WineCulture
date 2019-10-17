package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.WithdrawalAction;
import com.zhifeng.wineculture.modules.AliPayAccountDto;
import com.zhifeng.wineculture.modules.BankCardDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.RemainderDto;
import com.zhifeng.wineculture.ui.impl.WithdrawalView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;
import com.zhifeng.wineculture.utils.dialog.PayPwdDialog;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

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
    @BindView(R.id.ivAliPay)
    ImageView ivAliPay;
    @BindView(R.id.ll_pay_type_bank)
    LinearLayout llPayTypeBank;
    @BindView(R.id.ivBankCard)
    ImageView ivBankCard;
    @BindView(R.id.tv_withdrawal_account)
    TextView tv_withdrawal_account;
    @BindView(R.id.et_withdrawal_money)
    EditText etWithdrawalMoney;
    @BindView(R.id.tv_withdrawal_money_poundage)
    TextView tvWithdrawalMoneyPoundage;
    /**
     * 提现类型：3支付宝 4银行卡
     */
    private int withdrawalType = 3;
    /**
     * 全部余额
     */
    private String remainder_money = "0";

    /**
     * 最大提现
     */
    private int max;
    /**
     * 手续费率
     */
    private double rate;

    /**
     * 支付宝账号
     */
    private String alipay;

    /**
     * 银行卡
     */
    private String bankCard;

    /**
     * 是否设置支付密码 0未设置 1已设置
     */
    private int pwd;
    private String aliPayAccount;
    private String bankAccount;
    private final int REQUEST_CODE = 1;
    private PayPwdDialog payPwdDialog;

    @Override
    public int intiLayout() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
        getBalance();
        getAliPayAccount();
        getBankCard();
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
        ivAliPay.setSelected(true);
        tvWithdrawalMoneyPoundage.setText(ResUtil.getFormatString(R.string.balance_withdrawal_tab_10, "0.00"));
        loadView();
    }

    @Override
    protected void loadView() {
        etWithdrawalMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                double money = 0;
                if (!TextUtils.isEmpty(string)) {
                    money = Double.parseDouble(string);
                }
                double totalMoney = Double.parseDouble(remainder_money);
                if (money > totalMoney) {
                    money = totalMoney;
                    etWithdrawalMoney.setText(remainder_money);
                    etWithdrawalMoney.setSelection(remainder_money.length());
                }
                double poundAge = money * rate;
                DecimalFormat df = new DecimalFormat("######0.00");
                tvWithdrawalMoneyPoundage.setText(ResUtil.getFormatString(R.string.balance_withdrawal_tab_10, String.valueOf(df.format(poundAge))));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
    public void getBalanceSuccess(RemainderDto remainderDto) {
        loadDiss();
        RemainderDto.DataBean bean = remainderDto.getData();
        remainder_money = bean.getRemainder_money();
        tvWithdrawalBalance.setText(ResUtil.getFormatString(R.string.balance_withdrawal_tab_18, remainder_money));
        max = remainderDto.getData().getMax();
        tvWithdrawalLimit.setText(ResUtil.getFormatString(R.string.balance_withdrawal_tab_2, String.valueOf(max)));
        rate = bean.getRate();
        pwd = bean.getPwd();
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
    public void getAliPayAccount() {
        baseAction.getAliPayAccount();
    }

    @Override
    public void getAliPayAccountSuccess(AliPayAccountDto aliPayAccountDto) {
        AliPayAccountDto.DataBean bean = aliPayAccountDto.getData().get(0);
        String aliPayName = bean.getAlipay_name();
        alipay = bean.getAlipay();
        if (!TextUtils.isEmpty(aliPayName) && !TextUtils.isEmpty(alipay)) {
            aliPayAccount = aliPayName + "(" + alipay.replaceAll(ValidateUtils.isPhone2(alipay) ? "(\\d{3})(\\d{4})(\\d{4})"
                    : "(.{2}).+(.{2}@.+)", "$1****$2") + ")";
            if (withdrawalType == 3) {
                tv_withdrawal_account.setText(aliPayAccount);
            }
        }
    }

    @Override
    public void getAliPayAccountFail(int code, String msg) {
        showNormalToast(msg);
    }

    @Override
    public void getBankCard() {
        baseAction.getBankCard();
    }

    @Override
    public void getBankCardSuccess(BankCardDto bankCardDto) {
        List<BankCardDto.DataBean> beans = bankCardDto.getData();
        if (beans.size() > 0) {
            BankCardDto.DataBean bean = beans.get(0);
            String bankName = bean.getBank_name();
            bankCard = bean.getBank_card();
            bankAccount = bankName + "(" + bankCard.replaceAll("(\\d{15})(\\d{4})", "***************$2") + ")";
            if (withdrawalType == 4) {
                tv_withdrawal_account.setText(bankAccount);
            }
        }
    }

    @Override
    public void getBankCardFail(int code, String msg) {
        showNormalToast(msg);
    }

    /**
     * 提交提现
     */
    @Override
    public void withdrawal() {
        String moneyString = etWithdrawalMoney.getText().toString();
        if (TextUtils.isEmpty(moneyString)) {
            showNormalToast(R.string.balance_withdrawal_tab_8);
            return;
        }
        float money = Float.parseFloat(moneyString);
        float a = 100f;
        if (money % a != 0) {
            showNormalToast(R.string.balance_withdrawal_tab_17);
            return;
        }
        if (withdrawalType == 3 && TextUtils.isEmpty(alipay)) {
            showNormalToast(R.string.balance_withdrawal_tab_6);
            return;
        }
        if (withdrawalType == 4 && TextUtils.isEmpty(bankCard)) {
            showNormalToast(R.string.balance_withdrawal_tab_16);
            return;
        }
        if (money == 0) {
            showNormalToast(R.string.balance_withdrawal_tab_15);
            return;
        }
        if (pwd == 0) {
            Intent intent = new Intent(mContext, ForgetPwdActivity.class);
            intent.putExtra("phone", MySp.getMobile(mContext));
            intent.putExtra("type", 1);
            intent.putExtra("isOrder", true);
            startActivityForResult(intent, 201);
            return;
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            payPwdDialog = new PayPwdDialog(mContext, R.style.MY_AlertDialog, moneyString, ResUtil.getString(withdrawalType == 3 ? R.string.balance_withdrawal_tab_4 : R.string.balance_withdrawal_tab_5), withdrawalType == 3 ? 1 : 2);
            payPwdDialog.setOnFinishInput(new PayPwdDialog.OnFinishInput() {
                @Override
                public void inputFinish(String password) {
                    loadDialog();
                    baseAction.withdrawal(money, withdrawalType);
                }

                @Override
                public void forget() {
                    jumpActivityNotFinish(mContext, ForgetPwdActivity.class);
                }

                @Override
                public void close() {

                }
            });
            payPwdDialog.show();
        }
    }

    @Override
    public void withdrawalSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getMsg());
        if (payPwdDialog != null) {
            payPwdDialog.dismiss();
        }
    }

    @Override
    public void withdrawalFail(int code, String msg) {
        loadDiss();
        showNormalToast(msg);
    }

    private void withdrawalAll() {
        double money = Double.parseDouble(remainder_money);
        if (money > max) {
            etWithdrawalMoney.setText(String.valueOf(max));
            etWithdrawalMoney.setSelection(String.valueOf(max).length());
        } else {
            etWithdrawalMoney.setText(remainder_money);
            etWithdrawalMoney.setSelection(remainder_money.length());
        }
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

    @OnClick({R.id.ll_pay_type_ali, R.id.ll_pay_type_bank, R.id.tv_withdrawal_account, R.id.tv_withdrawal_money_all, R.id.tv_withdrawal_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_type_ali: {
                //todo 支付宝
                ivAliPay.setSelected(true);
                ivBankCard.setSelected(false);
                withdrawalType = 3;
                tv_withdrawal_account.setText(aliPayAccount);
                if (TextUtils.isEmpty(alipay)) {
                    tv_withdrawal_account.setHint(R.string.balance_withdrawal_tab_6);
                }
                break;
            }
            case R.id.ll_pay_type_bank: {
                //todo 银行卡
                ivAliPay.setSelected(false);
                ivBankCard.setSelected(true);
                withdrawalType = 4;
                tv_withdrawal_account.setText(bankAccount);
                if (TextUtils.isEmpty(bankCard)) {
                    tv_withdrawal_account.setHint(R.string.balance_withdrawal_tab_16);
                }
                break;
            }
            case R.id.tv_withdrawal_account:
                //todo 账号
                if (TextUtils.isEmpty(alipay) && withdrawalType == 3) {
                    startActivityForResult(new Intent(mContext, BindAliPayAccountActivity.class), REQUEST_CODE);
                    return;
                }
                if (TextUtils.isEmpty(bankCard) && withdrawalType == 4) {
                    startActivityForResult(new Intent(mContext, BindBankCardActivity.class), REQUEST_CODE);
                }
                break;
            case R.id.tv_withdrawal_money_all:
                //todo 全部提现
                withdrawalAll();
                break;
            case R.id.tv_withdrawal_confirm:
                //todo 提交
                withdrawal();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 11) {
                getAliPayAccount();
            } else if (resultCode == 12) {
                getBankCard();
            }
        } else if (requestCode == 201 && resultCode == 201) {
            pwd = 1;
        }
    }
}
