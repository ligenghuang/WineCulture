package com.zhifeng.wineculture.ui.loginandregister;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends UserBaseActivity {
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
    @BindView(R.id.etConfirmPwd)
    EditText etConfirmPwd;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;

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
    protected BaseAction initAction() {
        return null;
    }

    private void register() {
        String mobile=etMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)){
            showNormalToast(R.string.register_numHint);
            return;
        }
        if (ValidateUtils.isPhone2(mobile)){
            showNormalToast(R.string.register_rightMobileHint);
            return;
        }
        String pwd=etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)){
            showNormalToast(R.string.register_newPwdHint);
            return;
        }
        String confirmPwd=etConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(confirmPwd)){
            showNormalToast(R.string.register_comfirmPwdHint);
            return;
        }
        String code=etCode.getText().toString();
        if (TextUtils.isEmpty(code)){
            showNormalToast(R.string.register_codeHint);
            return;
        }
    }

    @OnClick({R.id.tvGetCode, R.id.btnRegister})
    public void onClick(View view) {
        if (view.getId() == R.id.tvGetCode) {

        } else {
            register();
        }
    }
}
