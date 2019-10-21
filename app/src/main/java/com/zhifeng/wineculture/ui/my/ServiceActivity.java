package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.DensityUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.ServiceAction;
import com.zhifeng.wineculture.adapters.ServiceAdapter;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.ui.impl.ServiceView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.view.VerticalItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 售后
 * @Author: Administrator
 * @Date: 2019/9/28 18:07
 */
public class ServiceActivity extends UserBaseActivity<ServiceAction> implements ServiceView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ServiceAdapter adapter;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean isMore = true;
    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_service;
    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new VerticalItemDecoration(DensityUtil.dp2px(5)));
        adapter = new ServiceAdapter(mContext);
        rv.setAdapter(adapter);

        refreshLayout.autoRefresh();
        loadView();
    }

    @Override
    protected void loadView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getOrderList();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreOrderList();
            }
        });
        adapter.setOnRefundButtonClickListener(order_id -> {
            Intent intent = new Intent(mContext, RefundActivity.class);
            intent.putExtra("order_id", String.valueOf(order_id));
            startActivityForResult(intent, REQUEST_CODE);
        });
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
                .addTag("ServiceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_service);
    }

    @Override
    protected ServiceAction initAction() {
        return new ServiceAction(this, this);
    }

    @Override
    public void getOrderList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            page = 1;
            isRefresh = true;
            baseAction.getOrderList(page, "tk");
        }else {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void loadMoreOrderList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            page++;
            isRefresh = false;
            baseAction.getOrderList(page, "tk");
        }else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void getOrderListSuccess(OrderListDto orderList) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        List<OrderListDto.DataBean> dataBeans = orderList.getData();
        if (dataBeans.size() != 0) {
            rv.setVisibility(View.VISIBLE);
            if (isRefresh) {
                adapter.refresh(dataBeans);
            } else {
                adapter.loadMore(dataBeans);
            }
        } else {
            if (isRefresh) {
                page = page - 1;
                adapter.refresh(new ArrayList<>());
            }
            isMore = false;
            loadSwapTab();
        }
    }

    @Override
    public void onError(String message, int code) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            getOrderList();
        }
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
