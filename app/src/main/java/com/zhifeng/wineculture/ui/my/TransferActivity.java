package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.TransferAction;
import com.zhifeng.wineculture.modules.BalanceDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.TransferPost;
import com.zhifeng.wineculture.ui.impl.TransferView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.dialog.BuyPwdDialog;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 转账
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/16 14:36
 * @Version: 1.0
 */

public class TransferActivity extends UserBaseActivity<TransferAction> implements TransferView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name_id)
    TextView tvNameId;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_note)
    EditText etNote;

    String avatar;
    String name;
    int id;


    @Override
    public int intiLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected TransferAction initAction() {
        return new TransferAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        avatar = getIntent().getStringExtra("avatar");

        tvNameId.setText(ResUtil.getFormatString(R.string.transfer_tab_8,name,id+""));
        GlideUtil.setImageCircle(mContext,avatar,ivAvatar,R.drawable.icon_avatar);

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
                .addTag("TransferActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.transfer_tab_7));
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
        tvBalance.setText("￥" + dataBean.getRemainder_money());
    }

    /**
     * 转账
     */
    @Override
    public void transfer(TransferPost transferPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.transfer(transferPost);
        }
    }

    /**
     * 转账成功
     */
    @Override
    public void transferSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getMsg());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
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

    @OnClick(R.id.tv_balance_transfer)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_balance_transfer:
                //todo 转账
                Transfer();
                break;
        }
    }

    private void Transfer() {
        //todo 判断是否输入金额
        if (TextUtils.isEmpty(etMoney.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.transfer_tab_3));
            return;
        }

        double money = Double.parseDouble(etMoney.getText().toString());

        //todo 判断输入的金额是否大于0
        if (money <= 0){
            showNormalToast(ResUtil.getString(R.string.transfer_tab_9));
            return;
        }

        BuyPwdDialog bugPwdDialog = new BuyPwdDialog(mContext, R.style.MY_AlertDialog, money, "我的余额");
        bugPwdDialog.setOnFinishInput(new BuyPwdDialog.OnFinishInput() {
            @Override
            public void inputFinish(String password) {
                //转账
                TransferPost post = new TransferPost();
                post.setEnd_user_id(id+"");
                if (!TextUtils.isEmpty(etNote.getText().toString())){
                    post.setDescription(etNote.getText().toString());
                }
                post.setExchange_money(money+"");
                post.setPwd(password);
                transfer(post);
            }

            @Override
            public void close() {
               bugPwdDialog.dismiss();
            }
        });
        bugPwdDialog.show();

    }
}
