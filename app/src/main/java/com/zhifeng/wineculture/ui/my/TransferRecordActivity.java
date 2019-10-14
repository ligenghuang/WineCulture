package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.TransferRecordAction;
import com.zhifeng.wineculture.adapters.TransferRecordAdapter;
import com.zhifeng.wineculture.modules.TransferRecordDto;
import com.zhifeng.wineculture.ui.impl.TransferRecordView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: 转账记录
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 15:02
 * @Version: 1.0
 */

public class TransferRecordActivity extends UserBaseActivity<TransferRecordAction> implements TransferRecordView {

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

    private int page = 1;
    private boolean isRefresh = true;
    private boolean isMore = true;

    TransferRecordAdapter transferRecordAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_transfer_record;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected TransferRecordAction initAction() {
        return new TransferRecordAction(this, this);
    }


    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        transferRecordAdapter = new TransferRecordAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(transferRecordAdapter);

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
                .addTag("TransferRecordActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.member_center_tab_5));
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreTransferRecord();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getTransferRecord();
            }
        });
    }

    /**
     * 获取转账记录
     */
    @Override
    public void getTransferRecord() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            page = 1;
            isRefresh = true;
            baseAction.getTransferRecord(page);
        } else {
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多转账记录
     */
    @Override
    public void loadMoreTransferRecord() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            page++;
            isRefresh = false;
            baseAction.getTransferRecord(page);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 获取转账记录成功
     * @param transferRecordDto
     */
    @Override
    public void getTransferRecordSuccess(TransferRecordDto transferRecordDto) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        List<TransferRecordDto.DataBeanX.DataBean> beans = transferRecordDto.getData().getData();
        if (beans.size() > 0) {
            isMore = page < transferRecordDto.getData().getLast_page();
            loadSwapTab();
            if (isRefresh) {
                transferRecordAdapter.refresh(beans);
            } else {
                transferRecordAdapter.loadMore(beans);
            }
        } else {
            isMore = false;
            loadSwapTab();
        }
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
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
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


    /**
     * tab变换 加载更多的显示方式
     */
    private void loadSwapTab() {
        if (!isMore) {
            L.e("xx", "设置为没有加载更多....");
            refreshLayout.finishLoadMoreWithNoMoreData();
            refreshLayout.setNoMoreData(true);
        } else {
            L.e("xx", "设置为可以加载更多....");
            refreshLayout.setNoMoreData(false);
        }
    }
}
