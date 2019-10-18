package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.OpenMemberAction;
import com.zhifeng.wineculture.adapters.OpenMemberAdapter;
import com.zhifeng.wineculture.modules.OpenMemberDto;
import com.zhifeng.wineculture.ui.impl.OpenMemberView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 开通会员
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/16 14:26
 * @Version: 1.0
 */

public class OpenMemberActivity extends UserBaseActivity<OpenMemberAction> implements OpenMemberView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    OpenMemberAdapter openMemberAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_open_member;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected OpenMemberAction initAction() {
        return new OpenMemberAction(this, this);
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
                .addTag("OpenMemberActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.balance_tab_3));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        refreshLayout.setEnableLoadMore(false);
        openMemberAdapter = new OpenMemberAdapter(mContext);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerview.setAdapter(openMemberAdapter);

        loadView();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getOpenMember();
            }
        });
    }

    /**
     * 开通会员
     */
    @Override
    public void getOpenMember() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getOpenMember();
        }
    }

    /**
     * 获取开通会员信息 成功
     * @param openMemberDto
     */
    @Override
    public void getOpenMemberSuccess(OpenMemberDto openMemberDto) {
        refreshLayout.finishRefresh();
        openMemberAdapter.refresh(openMemberDto.getData());
    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
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
}
