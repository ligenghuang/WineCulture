package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ForgetPwdAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.ForgetPwdPost;
import com.zhifeng.wineculture.modules.post.SetPayPwdPost;
import com.zhifeng.wineculture.ui.impl.ForgetPwdView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
  *
  * @ClassName:     忘记支付密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 16:28
  * @Version:        1.0
 */

public class ForgetPwdActivity extends UserBaseActivity<ForgetPwdAction> implements ForgetPwdView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_forget_pwd_moblie)
    TextView tvForgetPwdMoblie;
    @BindView(R.id.et_forget_pwd_code)
    EditText etForgetPwdCode;
    @BindView(R.id.tv_forget_pwd_get_code)
    TextView tvForgetPwdGetCode;
    @BindView(R.id.et_forget_pwd)
    EditText etForgetPwd;
    @BindView(R.id.et_forget_confirm_pwd)
    EditText etForgetConfirmPwd;

    int type;
    String phone;
    boolean isOrder;

    //获取验证码倒计时
    private MyCountDownTimer timer;

    @Override
    public int intiLayout() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected ForgetPwdAction initAction() {
        return new ForgetPwdAction(this,this);
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
                .addTag("ForgetPwdActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        type = getIntent().getIntExtra("type",0);
        fTitleTv.setText(ResUtil.getString(type==0?R.string.pay_pwd_tab_2:R.string.pay_pwd_tab_3));
        isOrder = getIntent().getBooleanExtra("isOrder",false);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        phone = getIntent().getStringExtra("phone");
        tvForgetPwdMoblie.setText(ResUtil.getFormatString(R.string.pay_pwd_tab_9,phone));
        timer = new MyCountDownTimer(60000, 1000);
    }

    /**
     * 获取验证码
     */
    @Override
    public void getCode() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getCode(phone);
        }
    }

    /**
     * 获取验证码成功
     * @param msg
     */
    @Override
    public void getCodeSuccess(String msg) {
        loadDiss();
        showNormalToast(msg);
        //todo 启动计时器
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
    }

    /**
     * 重置支付密码
     * @param forgetPwdPost
     */
    @Override
    public void forgetPwd(ForgetPwdPost forgetPwdPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.forgetPwd(forgetPwdPost);
        }
    }

    /**
     * 重置支付密码成功
     * @param generalDto
     */
    @Override
    public void forgetPwdSuccess(GeneralDto generalDto) {
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
     * 设置支付密码
     * @param setPayPwdPost
     */
    @Override
    public void setPayPwd(SetPayPwdPost setPayPwdPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.setPayPwd(setPayPwdPost);
        }
    }

    /**
     * 设置支付密码成功
     * @param generalDto
     */
    @Override
    public void setPayPwdSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getMsg());
        MySp.setPwd(mContext,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOrder){
                    Intent intent = new Intent();
                    intent.putExtra("pwd",1);
                    setResult(201,intent);
                }
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
        if (timer != null) {
            timer.cancel();
        }
        tvForgetPwdGetCode.setEnabled(true);
        tvForgetPwdGetCode.setSelected(false);
        tvForgetPwdGetCode.setText(R.string.modify_mobile_tab_12);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        baseAction.toUnregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @OnClick({R.id.tv_forget_pwd_get_code, R.id.tv_forget_pwd_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd_get_code:
                //todo 获取验证码
                getCode();
                break;
            case R.id.tv_forget_pwd_save:
                //todo 提交
                save();
                break;
        }
    }

    /**
     * 提交
     */
    private void save() {
        //todo 判断是否输入验证码
        if (TextUtils.isEmpty(etForgetPwdCode.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_10));
            return;
        }

        //todo 判断是否输入密码
        String pwd = etForgetPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_13));
            return;
        }
        //todo 判断密码是否少于6位
        if (pwd.length() < 6) {
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_18));
            return;
        }
        //todo 判断是否再次输入密码
        String confirmPwd = etForgetConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(confirmPwd)){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_14));
            return;
        }
        //todo 判断确认密码是否少于6位
        if (confirmPwd.length() < 6) {
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_19));
            return;
        }
        //todo 判断两次密码是否一致
        if (!pwd.equals(confirmPwd)){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_15));
            return;
        }
        if (type == 0){
            //重置支付密码
            ForgetPwdPost forgetPwdPost = new ForgetPwdPost(phone,confirmPwd, pwd);
            forgetPwd(forgetPwdPost);
        }else {
            //设置支付密码
            SetPayPwdPost setPayPwdPost = new SetPayPwdPost(phone,confirmPwd, pwd);
            setPayPwd(setPayPwdPost);
        }
    }


    /**************************************计时器 start*******************************************/
    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            tvForgetPwdGetCode.setEnabled(false);
            tvForgetPwdGetCode.setSelected(true);
            tvForgetPwdGetCode.setText(FormatUtils.format(getString(R.string.modify_mobile_tab_13), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tvForgetPwdGetCode.setEnabled(true);
            tvForgetPwdGetCode.setSelected(false);
            tvForgetPwdGetCode.setText(R.string.modify_mobile_tab_12);
        }
    }
/*****************************************计时器 end**************************************************/
}
