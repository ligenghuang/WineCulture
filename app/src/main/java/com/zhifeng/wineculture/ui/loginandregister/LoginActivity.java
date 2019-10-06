package com.zhifeng.wineculture.ui.loginandregister;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.LoginAction;
import com.zhifeng.wineculture.ui.impl.LoginView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName:
 * @Description: 登录页
 * @Author: Administrator
 * @Date: 2019/9/28 15:35
 */
public class LoginActivity extends UserBaseActivity<LoginAction> implements LoginView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etPwd)
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginAction initAction() {
        return new LoginAction(this, this);
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
                .addTag("LoginActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.login_login);
    }

    /**
     * 登录
     * @param phone
     * @param pwd
     */
    @Override
    public void login(String phone, String pwd) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.login(phone,pwd);
        }
    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {

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


    @OnClick({R.id.ivHidePwd, R.id.btnLogin,R.id.tvRegister,R.id.tvForgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHidePwd:
                break;
            case R.id.btnLogin:
                Login();
                break;
            case R.id.tvRegister:
                jumpActivityNotFinish(mContext, RegisterActivity.class);
                break;
            case R.id.tvForgetPwd:
                break;
        }
    }

    private void Login() {
        //todo 判断是否输入手机号码
        if (TextUtils.isEmpty(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_numHint));
            return;
        }

        //TODO 判断是否输入密码
        if(TextUtils.isEmpty(etPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_pwdHint));
            return;
        }

        login(etMobile.getText().toString(),etPwd.getText().toString());
    }


}
