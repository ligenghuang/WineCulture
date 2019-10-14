package com.zhifeng.wineculture.ui.loginandregister;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.UpdatePwdAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.SendVerifyCodeDto;
import com.zhifeng.wineculture.ui.impl.UpdatePwdView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePwdActivity extends UserBaseActivity<UpdatePwdAction> implements UpdatePwdView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
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
        return R.layout.activity_updatepwd;
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
    protected UpdatePwdAction initAction() {
        return new UpdatePwdAction(this, this);
    }

    @Override
    public void getCode() {
        String phone = etMobile.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showNormalToast(R.string.updatepwd_inputNumHint);
            return;
        }
        if (!ValidateUtils.isPhone2(phone)) {
            showNormalToast(R.string.updatepwd_inputRightNumHint);
            return;
        }
        baseAction.getVerifyCode(phone);
    }

    @Override
    public void sendVerifyCodeSuccess(SendVerifyCodeDto sendVerifyCodeDto) {
        showNormalToast(sendVerifyCodeDto.getData());
        tvGetCode.setEnabled(false);
        countDownTimer = new MyCountDownTimer(60 * 1000, 1000).start();
    }

    @Override
    public void sendVerifyCodeFail(String msg, int errorType) {
        showNormalToast(msg);
    }

    @Override
    public void updatePwd() {
        String phone = etMobile.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showNormalToast(R.string.updatepwd_inputNumHint);
            return;
        }
        if (!ValidateUtils.isPhone2(phone)) {
            showNormalToast(R.string.updatepwd_inputRightNumHint);
            return;
        }
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            showNormalToast(R.string.updatepwd_inputNewPwdHint);
            return;
        }
        String confirmPwd = etConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(confirmPwd)) {
            showNormalToast(R.string.updatepwd_inputComfirmPwdHint);
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            showNormalToast(R.string.updatepwd_differentPwdHint);
            return;
        }
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            showNormalToast(R.string.updatepwd_inputCodeHint);
            return;
        }
        baseAction.updatePwd("1", phone, code, pwd, confirmPwd);
    }

    @Override
    public void updatePwdSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getData());
        finish();
    }

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @OnClick({R.id.tvGetCode, R.id.btnConfirm})
    public void OnClick(View view) {
        if (view.getId() == R.id.tvGetCode) {
            getCode();
        } else {
            updatePwd();
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
            String text = ResUtil.getFormatString(R.string.updatepwd_remainderSecond, second);
            tvGetCode.setText(text);
        }

        @Override
        public void onFinish() {
            tvGetCode.setEnabled(true);
            tvGetCode.setText(R.string.updatepwd_getCode);
        }
    }
}
