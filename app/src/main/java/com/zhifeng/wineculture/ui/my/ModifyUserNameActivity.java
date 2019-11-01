package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ModifyUserNameAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.ui.impl.ModifyUserNameView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 修改用户名
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/14 16:29
 * @Version: 1.0
 */

public class ModifyUserNameActivity extends UserBaseActivity<ModifyUserNameAction> implements ModifyUserNameView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etRealName)
    EditText etRealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_user_name;
    }

    @Override
    protected void init() {
        super.init();
        mContext=this;
        mActicity=this;
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
                .addTag("ModifyUserNameActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.modifyrealname_title));
        String userName = getIntent().getStringExtra("userName");
        etRealName.setText(userName);
        if (userName != null) {
            etRealName.setSelection(userName.length());
        }
    }

    @Override
    protected ModifyUserNameAction initAction() {
        return new ModifyUserNameAction(this, this);
    }

    @Override
    public void modifyRealName() {
        if (CheckNetwork.checkNetwork2(mContext)){
            String realName=etRealName.getText().toString();
            if (TextUtils.isEmpty(realName)) {
                showNormalToast(R.string.modifyrealname_inputRealname);
                return;
            }
            baseAction.modifyRealName(realName);
        }
    }

    @Override
    public void modifyRealNameSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getData());
        finish();
    }

    @Override
    public void onError(String message, int code) {
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

    @OnClick(R.id.tvSubmit)
    public void onViewClicked() {
        modifyRealName();
    }
}
