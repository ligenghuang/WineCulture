package com.zhifeng.wineculture.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
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
import java.util.List;

import butterknife.BindView;

public class GoodsCommentsActivity extends UserBaseActivity<CommentsListAction> implements CommentsListView {
    @BindView(R.id.top_view)
    View topView;
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
            baseAction.getCommentList(page, goodsId);
        }
    }

    @Override
    public void getCommentListSuccess(CommentsListDto commentsListDto) {
        refreshLayout.finishRefresh();
        tvCommentsNum.setText(ResUtil.getFormatString(R.string.goods_detail_tab_34,comment_count));
        List<CommentsListDto.DataBean> beans = commentsListDto.getData();
        adapter.refresh(beans);
    }

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
