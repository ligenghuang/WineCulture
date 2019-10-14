package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BalanceAction;
import com.zhifeng.wineculture.modules.BalanceDto;
import com.zhifeng.wineculture.ui.impl.BalanceView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 余额
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 11:08
 * @Version: 1.0
 */

public class BalanceActivity extends UserBaseActivity<BalanceAction> implements BalanceView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public int intiLayout() {
        return R.layout.activity_balance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected BalanceAction initAction() {
        return new BalanceAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        getBalanceData();
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
                .addTag("BalanceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.member_center_tab_1));
    }

    /**
     * 获取账户余额
     */
    @Override
    public void getBalanceData() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getBalanceData();
        }
    }

    /**
     * 获取账户余额 成功
     */
    @Override
    public void getBalanceDataSuccess(BalanceDto balanceDto) {
        loadDiss();
        BalanceDto.DataBean dataBean = balanceDto.getData();
//        DecimalFormat df = new DecimalFormat("#0.00");
        tvBalance.setText("￥"+dataBean.getRemainder_money());
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

    @OnClick({R.id.tv_balance_opening_member, R.id.tv_balance_transfer, R.id.tv_balance_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_balance_opening_member:
                //todo 开通会员
                break;
            case R.id.tv_balance_transfer:
                //todo 转账
                jumpActivityNotFinish(mContext,TransferSearchActivity.class);
                break;
            case R.id.tv_balance_withdrawal:
                //todo 提现
                jumpActivityNotFinish(mContext,WithdrawalActivity.class);
                break;
        }
    }
}
