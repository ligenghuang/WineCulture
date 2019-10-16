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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.FansOrderAction;
import com.zhifeng.wineculture.adapters.FansOrderAdapter;
import com.zhifeng.wineculture.modules.FansOrderDto;
import com.zhifeng.wineculture.ui.impl.FansOrderView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 我的团队-查看
 * @Author: Administrator
 * @Date: 2019/10/15 18:27
 */
public class FansOrderActivity extends UserBaseActivity<FansOrderAction> implements FansOrderView {
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
    private String id;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean isMore = true;
    private FansOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_fansorder;
    }

    @Override
    protected FansOrderAction initAction() {
        return new FansOrderAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;
        id = getIntent().getStringExtra("id");
        adapter = new FansOrderAdapter();
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        refreshLayout.autoRefresh();
    }

    @Override
    protected void loadView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isRefresh = true;
                getFansOrder();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                isRefresh = false;
                getFansOrder();
            }
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
                .addTag("FansOrderActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.myteam_lookup_title);
    }

    @Override
    public void getFansOrder() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getFansOrder(id, page);
        } else if(isRefresh){
            refreshLayout.finishRefresh();
        }else{
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void getFansOrderSuccess(FansOrderDto fansOrderDto) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        List<FansOrderDto.DataBeanX.DataBean> beans = fansOrderDto.getData().getData();
        if (beans.size() > 0) {
            isMore = page > fansOrderDto.getData().getLast_page();
            loadSwapTab();
            if (isRefresh) {
                adapter.refresh(beans);
            } else {
                adapter.loadMore(beans);
            }
        } else {
            isMore = false;
            loadSwapTab();
        }
    }

    /**
     * tab变换 加载更多的显示方式
     */
    public void loadSwapTab() {
        if (!isMore) {
            L.e("xx", "设置为没有加载更多....");
            refreshLayout.finishLoadMoreWithNoMoreData();
            refreshLayout.setNoMoreData(true);
        } else {
            L.e("xx", "设置为可以加载更多....");
            refreshLayout.setNoMoreData(false);
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
}
