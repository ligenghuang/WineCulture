package com.zhifeng.wineculture.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ModifyMoblieAction;
import com.zhifeng.wineculture.modules.BaseDto;
import com.zhifeng.wineculture.ui.impl.ModifyMobileView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
  *
  * @ClassName:     修改手机号码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 16:26
  * @Version:        1.0
 */

public class ModifyMobileActivity extends UserBaseActivity<ModifyMoblieAction> implements ModifyMobileView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_modify_moblie)
    TextView tvModifyMoblie;
    @BindView(R.id.et_modify_moblie)
    EditText etModifyMoblie;

    String moblie="";

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_mobile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected ModifyMoblieAction initAction() {
        return new ModifyMoblieAction(this, this);
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
                .addTag("ModifyMobileActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.modify_mobile_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 验证手机号
     * @param phone
     */
    @Override
    public void verifyPhone(String phone) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.verifyPhone(phone);
        }
    }

    /**
     * 验证成功
     * @param generalDto
     */
    @Override
    public void verifyPhoneSuccess(BaseDto generalDto) {
        loadDiss();
        Intent intent = new Intent(mContext,ModifyMobileCodeActivity.class);
        intent.putExtra("moblie",moblie);
        startActivity(intent);
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

    @OnClick(R.id.tv_next)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_next:
                //todo 下一步
                if (TextUtils.isEmpty(etModifyMoblie.getText().toString())){
                    showNormalToast(ResUtil.getString(R.string.modify_mobile_tab_2));
                    return;
                }
                if (!ValidateUtils.isPhone2(etModifyMoblie.getText().toString())){
                    showNormalToast(ResUtil.getString(R.string.modify_mobile_tab_17));
                    return;
                }

                moblie = etModifyMoblie.getText().toString();
                verifyPhone(moblie);
                break;
        }
    }
}
