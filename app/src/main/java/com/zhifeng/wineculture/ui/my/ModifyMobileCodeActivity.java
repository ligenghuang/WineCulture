package com.zhifeng.wineculture.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ModifyMobileCodeAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.ui.impl.ModifyMobileCodeView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lgh.huanglib.util.data.ResUtil.getString;

/**
  *
  * @ClassName:     修改手机号码  验证码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 16:40
  * @Version:        1.0
 */

public class ModifyMobileCodeActivity extends UserBaseActivity<ModifyMobileCodeAction> implements ModifyMobileCodeView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_moblie)
    TextView tvMoblie;
    @BindView(R.id.et_moblie_code)
    EditText etMoblieCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    String moblie;

    //获取验证码倒计时
    private MyCountDownTimer timer;

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_mobile_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected ModifyMobileCodeAction initAction() {
        return new ModifyMobileCodeAction(this, this);
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
                .addTag("ModifyMobileCodeActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(getString(R.string.modify_mobile_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        moblie = getIntent().getStringExtra("moblie");
        tvMoblie.setText(ResUtil.getFormatString(R.string.modify_mobile_tab_8,moblie));
        timer = new MyCountDownTimer(60000, 1000);
        getCode();
    }

    /**
     * 获取验证码
     */
    @Override
    public void getCode() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getCode(moblie);
        }
    }

    /**
     * 获取验证码成功
     *
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
     * 换绑手机
     *
     * @param verify_code
     * @param phone
     */
    @Override
    public void changePhone(String verify_code, String phone) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.changePhone(verify_code,phone);
        }
    }

    /**
     * 换绑手机成功
     *
     * @param generalDto
     */
    @Override
    public void changePhoneSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(getString(R.string.modify_mobile_tab_15));
        ActivityStack.getInstance().exitClass(ModifyMobileActivity.class);
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
        if (timer != null) {
            timer.cancel();
        }
        tvGetCode.setEnabled(true);
        tvGetCode.setSelected(false);
        tvGetCode.setText(R.string.modify_mobile_tab_12);
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

    @OnClick({R.id.tv_get_code, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                //todo 获取验证码
                getCode();
                break;
            case R.id.tv_next:
                //todo 提交
                Change();
                break;
        }
    }

    /**
     * 修改手机号码
     */
    private void Change() {
        if(TextUtils.isEmpty(etMoblieCode.getText().toString())){
            showNormalToast(getString(R.string.modify_mobile_tab_16));
            return;
        }
        changePhone(etMoblieCode.getText().toString(),moblie);
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
            tvGetCode.setEnabled(false);
            tvGetCode.setSelected(true);
            tvGetCode.setText(FormatUtils.format(getString(R.string.modify_mobile_tab_13), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tvGetCode.setEnabled(true);
            tvGetCode.setSelected(false);
            tvGetCode.setText(R.string.modify_mobile_tab_12);
        }
    }
/*****************************************计时器 end**************************************************/
}
