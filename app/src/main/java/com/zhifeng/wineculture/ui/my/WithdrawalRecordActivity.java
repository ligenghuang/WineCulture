package com.zhifeng.wineculture.ui.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.WithdrawalRecordAction;
import com.zhifeng.wineculture.adapters.WithdrawalRecordAdapter;
import com.zhifeng.wineculture.modules.WithdrawalRecordDto;
import com.zhifeng.wineculture.ui.impl.WithdrawalRecordView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
  *
  * @ClassName:     提现记录
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/29 15:27
  * @Version:        1.0
 */

public class WithdrawalRecordActivity extends UserBaseActivity<WithdrawalRecordAction> implements WithdrawalRecordView {

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

    WithdrawalRecordAdapter withdrawalRecordAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_withdrawal_record;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected WithdrawalRecordAction initAction() {
        return new WithdrawalRecordAction(this,this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        refreshLayout.setEnableLoadMore(false);

        withdrawalRecordAdapter = new WithdrawalRecordAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(withdrawalRecordAdapter);

        loadView();
        refreshLayout.autoRefresh();
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
                .addTag("WithdrawalRecordActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.member_center_tab_6));
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getWithdrawalRecord();
            }
        });
    }

    /**
     * 获取提现记录
     */
    @Override
    public void getWithdrawalRecord() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getWithdrawalRecord();
        }
    }

    @Override
    public void getWithdrawalRecordSuccess(WithdrawalRecordDto withdrawalRecordDto) {
        refreshLayout.finishRefresh();
        withdrawalRecordAdapter.refresh(withdrawalRecordDto.getData().getList());
    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
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
