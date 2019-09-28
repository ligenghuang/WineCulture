package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;

import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

/**
 * @ClassName:
 * @Description: 待评价
 * @Author: Administrator
 * @Date: 2019/9/28 18:07
 */
public class ToBeCommentActivity extends UserBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_to_be_comment;
    }

    @Override
    protected void init() {
        super.init();
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
                .addTag("ToBeCommentActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
//        toolbar.setNavigationOnClickListener(view -> finish());
//        fTitleTv.setText(R.string.login_login);
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }
}
