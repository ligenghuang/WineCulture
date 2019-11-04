package com.zhifeng.wineculture.ui.loginandregister;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.zhifeng.wineculture.actions.RegisterAction;
import com.zhifeng.wineculture.modules.RegisterDto;
import com.zhifeng.wineculture.modules.SendVerifyCodeDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.RegisterView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 注册页
 * @Author: Administrator
 * @Date: 2019/10/10 14:16
 */
public class RegisterActivity extends UserBaseActivity<RegisterAction> implements RegisterView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.etConfirmPwd)
    EditText etConfirmPwd;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
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
                .addTag("RegisterActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.register_register);
    }

    @Override
    protected RegisterAction initAction() {
        return new RegisterAction(this, this);
    }

    @Override
    public void sendVerifyCode() {
        String phone = etMobile.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showNormalToast(R.string.register_numHint);
            return;
        }
        if (!ValidateUtils.isPhone2(phone)) {
            showNormalToast(R.string.register_rightMobileHint);
            return;
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.sendVerifyCode(phone);
        }
    }

    @Override
    public void sendVerifyCodeSuccess(SendVerifyCodeDto sendVerifyCodeDto) {
        showNormalToast(sendVerifyCodeDto.getData());
        tvGetCode.setEnabled(false);
        countDownTimer = new MyCountDownTimer(60 * 1000, 1000).start();
    }

    @Override
    public void sendVerifyCodeFail(String msg, int code) {
        showNormalToast(msg);
    }

    @Override
    public void register() {
        String mobile = etMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            showNormalToast(R.string.register_numHint);
            return;
        }
        if (!ValidateUtils.isPhone2(mobile)) {
            showNormalToast(R.string.register_rightMobileHint);
            return;
        }
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            showNormalToast(R.string.register_newPwdHint);
            return;
        }
        String confirmPwd = etConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(confirmPwd)) {
            showNormalToast(R.string.register_comfirmPwdHint);
            return;
        }
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            showNormalToast(R.string.register_codeHint);
            return;
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.register(mobile, code, pwd, confirmPwd);
        }
    }

    @Override
    public void registerSuccess(RegisterDto registerDto) {
        showNormalToast(R.string.register_success);
        MySp.setMobile(mContext, registerDto.getData().getMobile());
        MySp.setAccessToken(mContext, registerDto.getData().getToken());
        ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class);
        jumpActivity(mContext, MainActivity.class);
    }

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
    }

    @Override
    public void hadRegister(String message) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @OnClick({R.id.tvGetCode, R.id.btnRegister})
    public void onClick(View view) {
        if (view.getId() == R.id.tvGetCode) {
            sendVerifyCode();
        } else {
            register();
        }
    }

    private class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        private MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int second = (int) (millisUntilFinished / 1000);
            String text = ResUtil.getFormatString(R.string.register_remainderSecond, second);
            tvGetCode.setText(text);
        }

        @Override
        public void onFinish() {
            tvGetCode.setEnabled(true);
            tvGetCode.setText(R.string.register_getcode);
        }
    }
}
