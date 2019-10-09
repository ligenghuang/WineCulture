package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 关于酒文化
 * @Author: Administrator
 * @Date: 2019/9/28 18:05
 */
public class AboutWineCultureActivity extends UserBaseActivity {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_about_wine_culture;
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
                .addTag("AboutWineCultureActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_aboutwineculture);
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }
}
