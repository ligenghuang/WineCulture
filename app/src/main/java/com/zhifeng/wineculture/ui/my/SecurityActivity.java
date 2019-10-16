package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.SecurityAction;
import com.zhifeng.wineculture.modules.SafeInfoDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.SecurityView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
  *
  * @ClassName:     安全中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 16:16
  * @Version:        1.0
 */

public class SecurityActivity extends UserBaseActivity<SecurityAction> implements SecurityView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_security_name)
    TextView tvSecurityName;
    @BindView(R.id.tv_security_phone)
    TextView tvSecurityPhone;

    int isPwd = 0;
    String Mobile;

    @Override
    public int intiLayout() {
        return R.layout.activity_security;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected SecurityAction initAction() {
        return new SecurityAction(this, this);
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
                .addTag("SecurityActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.security_tab_6));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

//        getSafeInfo();
    }

    /**
     * 获取安全中心信息
     */
    @Override
    public void getSafeInfo() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getSafeInfo();
        }
    }

    /**
     * 获取安全中心信息
     *
     * @param safeInfoDto
     */
    @Override
    public void getSafeInfoSuccess(SafeInfoDto safeInfoDto) {
        loadDiss();
        SafeInfoDto.DataBean dataBean = safeInfoDto.getData();
        tvSecurityName.setText(dataBean.getRealname());
        String phone = dataBean.getMobile();
        Mobile = dataBean.getMobile();
        tvSecurityPhone.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        isPwd = dataBean.getPwd();
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
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (baseAction != null){
            baseAction.toRegister();
            getSafeInfo();
        }
    }

    @OnClick({ R.id.tv_security_phone, R.id.ll_security_pay_pwd, R.id.ll_security_user,R.id.tv_logout,R.id.tv_security_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_security_phone:
                //修改手机号码
                jumpActivityNotFinish(mContext,ModifyMobileActivity.class);
                break;
            case R.id.ll_security_pay_pwd:
                //支付密码
                if (isPwd == 1){
                    jumpPwdActivity(PayPwdActivity.class,0);
                }else {
                    jumpPwdActivity(ForgetPwdActivity.class,1);
                }
                break;
            case R.id.ll_security_user:
                //切换用户
                jumpActivityNotFinish(mContext,ChangeAccountActivity.class);
                break;
            case R.id.tv_logout:
                //退出
                MySp.clearAllSP(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                MainActivity.Position = 0;
                ActivityStack.getInstance().exitIsNotHaveMain( LoginActivity.class);
                break;
            case R.id.tv_security_name:
                //todo 修改用户名
                Intent intent1 = new Intent(mContext,ModifyUserNameActivity.class);
                intent1.putExtra("userName",tvSecurityName.getText().toString());
                startActivity(intent1);
                break;
        }
    }

    /**
     * 跳转至支付密码页面或者设置支付密码页面
     * @param payPwdActivityClass
     * @param i
     */
    private void jumpPwdActivity(Class payPwdActivityClass,int i) {
        Intent intent = new Intent(mContext,payPwdActivityClass);
        intent.putExtra("phone",Mobile);
        intent.putExtra("type",i);
        startActivity(intent);
    }
}
