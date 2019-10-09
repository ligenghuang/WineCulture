package com.zhifeng.wineculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;

import androidx.appcompat.widget.Toolbar;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.base.BaseActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int intiLayout() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(intiLayout());
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .titleBar(toolbar, false)
                .transparentBar()
                .statusBarDarkFont(true)
                .init();
        init();
    }

    @Override
    protected void init() {
        super.init();

        mActicity = this;
        mContext = this;

        //todo 引导页
//        if (!MySp.getFirst(getApplicationContext())) {
//            jumpActivityNotFinish(this, GuidePageActivity.class);
//            finish();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        List<String> needRequestPermissionList = findDeniedPermissions(needPermissions);
        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //todo 判断是否登录跳转不同页面
//                    Intent intent = new Intent(mContext, MySp.iSLoginLive(mContext)?MainActivity.class: LoginActivity.class);
//                    startActivity(intent);
                    jumpActivity(mContext,MainActivity.class);
                    isNeedAnim = false;
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }

}
