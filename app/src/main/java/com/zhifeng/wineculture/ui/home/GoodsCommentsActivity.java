package com.zhifeng.wineculture.ui.home;

import android.os.Bundle;
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
import com.zhifeng.wineculture.actions.CommentsListAction;
import com.zhifeng.wineculture.adapters.GoodsCommentsAdapter;
import com.zhifeng.wineculture.modules.CommentsListDto;
import com.zhifeng.wineculture.ui.impl.CommentsListView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsCommentsActivity extends UserBaseActivity<CommentsListAction> implements CommentsListView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tvCommentsNum)
    TextView tvCommentsNum;
    @BindView(R.id.tvLookUpAll)
    TextView tvLookUpAll;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean isMore = true;
    private GoodsCommentsAdapter adapter;
    private String goodsId;
    private String comment_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goodscomment;
    }

    @Override
    protected CommentsListAction initAction() {
        return new CommentsListAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        goodsId = getIntent().getStringExtra("goodsId");
        comment_count = getIntent().getStringExtra("comment_count");
        mActicity = this;
        mContext = this;
        adapter = new GoodsCommentsAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        loadView();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void loadView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCommentList();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreCommentList();
            }
        });
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
                .addTag("GoodsCommentsActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.goods_detail_tab_22);
    }

    @Override
    public void getCommentList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            baseAction.getCommentList(page, goodsId);
        } else {
            refreshLayout.finishRefresh();
        }
    }

    private void loadMoreCommentList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getCommentList(page, goodsId);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void getCommentListSuccess(CommentsListDto commentsListDto) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        tvCommentsNum.setText(ResUtil.getFormatString(R.string.goods_detail_tab_34, comment_count));
        List<CommentsListDto.DataBean> beans = commentsListDto.getData();
        if (beans.size() > 0) {
            if (isRefresh) {
                adapter.refresh(beans);
            } else {
                adapter.loadMore(beans);
            }
        } else {
            if (isRefresh) {
                adapter.refresh(new ArrayList<>());
            }
            isMore = false;
            loadSwapTab();
        }
        adapter.refresh(beans);
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

    @Override
    public void onError(String message, int code) {
        showNormalToast(message);
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            page--;
            refreshLayout.finishLoadMore();
        }
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
