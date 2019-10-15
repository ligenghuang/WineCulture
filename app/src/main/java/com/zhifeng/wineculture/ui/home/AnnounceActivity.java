package com.zhifeng.wineculture.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AnnounceAction;
import com.zhifeng.wineculture.adapters.AnnounceAdapter;
import com.zhifeng.wineculture.modules.AnnounceDto;
import com.zhifeng.wineculture.ui.impl.AnnounceView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 公告详情
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/10/12 18:20
 * @Version: 1.0
 */

public class AnnounceActivity extends UserBaseActivity<AnnounceAction> implements AnnounceView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    AnnounceAdapter announceAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_announce;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected AnnounceAction initAction() {
        return new AnnounceAction(this, this);
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
                .addTag("AnnounceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());

        fTitleTv.setText(ResUtil.getString(R.string.announce_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        announceAdapter = new AnnounceAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(announceAdapter);

        getAnnounce();
    }

    /**
     * 获取公告列表
     */
    @Override
    public void getAnnounce() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getAnnounce();
        }
    }

    /**
     * 获取公告列表 成功
     * @param announceDto
     */
    @Override
    public void getAnnounceSuccess(AnnounceDto announceDto) {
        loadDiss();
        announceAdapter.refresh(announceDto.getData().getList());
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
}

