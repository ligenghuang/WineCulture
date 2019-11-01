package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ModifyPayPwdAction;
import com.zhifeng.wineculture.modules.BaseDto;
import com.zhifeng.wineculture.ui.impl.ModifyPayPwdView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
  *
  * @ClassName:     修改支付密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 16:52
  * @Version:        1.0
 */

public class ModifyPayPwdActivity extends UserBaseActivity<ModifyPayPwdAction> implements ModifyPayPwdView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_pay_pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected ModifyPayPwdAction initAction() {
        return new ModifyPayPwdAction(this, this);
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
                .addTag("ModifyPayPwdActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.pay_pwd_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 修改支付密码
     *
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void modifyPayPwd(String oldPwd, String newPwd) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.modifyPayPwd(oldPwd, newPwd);
        }
    }

    /**
     * 修改支付密码成功
     *
     * @param baseDto
     */
    @Override
    public void modifyPayPwdSuccess(BaseDto baseDto) {
        loadDiss();
        showNormalToast(baseDto.getMsg());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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

    @OnClick(R.id.tv_save)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_save:
                //todo 提交
                save();
                break;
        }
    }

    /**
     * 提交修改密码
     */
    private void save() {
        //判断是否输入原密码
        String oldPwd = etOldPwd.getText().toString();
        if(TextUtils.isEmpty(oldPwd)){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_7));
            return;
        }
        if (oldPwd.length() < 6) {
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_16));
            return;
        }
        //判断是否输入新密码
        String newPwd = etNewPwd.getText().toString();
        if (TextUtils.isEmpty(newPwd)){
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_8));
            return;
        }
        if (newPwd.length() < 6) {
            showNormalToast(ResUtil.getString(R.string.pay_pwd_tab_17));
            return;
        }
        modifyPayPwd(oldPwd, newPwd);
    }
}
