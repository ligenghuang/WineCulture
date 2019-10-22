package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MemberCenterAction;
import com.zhifeng.wineculture.modules.MemberCenterDto;
import com.zhifeng.wineculture.ui.impl.MemberCenterView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 会员中心
 * @Author: Administrator
 * @Date: 2019/9/28 18:05
 */
public class MemberCenterActivity extends UserBaseActivity<MemberCenterAction> implements MemberCenterView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_member_center_name)
    TextView tvMemberCenterName;
    @BindView(R.id.tv_member_center_phone)
    TextView tvMemberCenterPhone;
    @BindView(R.id.tv_member_center_lever)
    TextView tvMemberCenterLever;
    @BindView(R.id.tv_member_center_balance)
    TextView tvMemberCenterBalance;
    @BindView(R.id.tv_member_center_can_use)
    TextView tvMemberCenterCanUse;
    @BindView(R.id.tv_member_center_to_be_determined)
    TextView tvMemberCenterToBeDetermined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_member_center;
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
                .addTag("MemberCenterActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_membercenter));
    }

    @Override
    protected MemberCenterAction initAction() {
        return new MemberCenterAction(this,this);
    }

    /**
     * 获取会员中心数据
     */
    @Override
    public void getMemberCenterData() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getMemberCenterData();
        }
    }

    /**
     * 获取会员中心数据 成功
     */
    @Override
    public void getMemberCenterDataSuccess(MemberCenterDto memberCenterDto) {
        loadDiss();
        MemberCenterDto.DataBean dataBean = memberCenterDto.getData();
        tvMemberCenterName.setText(dataBean.getRealname());
        tvMemberCenterLever.setText(dataBean.getLevelname());
        String mobile = dataBean.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tvMemberCenterPhone.setText(mobile);
        GlideUtil.setImageCircle(mContext,dataBean.getAvatar(),ivAvatar,R.drawable.icon_avatar);
        tvMemberCenterBalance.setText("￥"+dataBean.getRemainder_money());
        tvMemberCenterCanUse.setText("￥"+dataBean.getDistribut_money());
        tvMemberCenterToBeDetermined.setText("￥"+dataBean.getEstimate_money());

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
        getMemberCenterData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @OnClick({R.id.ll_member_center_balance, R.id.ll_member_center_can_use, R.id.ll_member_center_to_be_determined, R.id.tv_balance_of_payments, R.id.tv_transfer, R.id.tv_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_member_center_balance:
                //todo 余额
                jumpActivityNotFinish(mContext, BalanceActivity.class);
                break;
            case R.id.ll_member_center_can_use:
                //todo 可使用
                break;
            case R.id.ll_member_center_to_be_determined:
                //todo 待确定
                break;
            case R.id.tv_balance_of_payments:
                //todo  我的收支
                jumpActivityNotFinish(mContext,BalanceOfPaymentActivity.class);
                break;
            case R.id.tv_transfer:
                //todo 转账记录
                jumpActivityNotFinish(mContext,TransferRecordActivity.class);
                break;
            case R.id.tv_withdrawal:
                //todo 提现记录
                jumpActivityNotFinish(mContext,WithdrawalRecordActivity.class);
                break;
        }
    }


}
