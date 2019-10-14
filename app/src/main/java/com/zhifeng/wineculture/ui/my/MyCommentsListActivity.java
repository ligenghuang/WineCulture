package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyCommentsListAction;
import com.zhifeng.wineculture.adapters.MyCommentsAdapter;
import com.zhifeng.wineculture.modules.MyCommentListDto;
import com.zhifeng.wineculture.ui.impl.MyCommentsListView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 我的评论
 * @Author: Administrator
 * @Date: 2019/9/28 18:05
 */
public class MyCommentsListActivity extends UserBaseActivity<MyCommentsListAction> implements MyCommentsListView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MyCommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_comments;
    }

    @Override
    protected void init() {
        super.init();
        mActicity=this;
        mContext=this;
        adapter=new MyCommentsAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.autoRefresh();
        loadView();
    }

    @Override
    protected void loadView() {
        refreshLayout.setOnRefreshListener( refreshLayout -> {
           getCommentList();
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
                .addTag("MyCommentsListActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_mycomments);
    }

    @Override
    protected MyCommentsListAction initAction() {
        return new MyCommentsListAction(this,this);
    }

    @Override
    public void getCommentList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getCommentList();
        }
    }

    @Override
    public void getCommentListSuccess(MyCommentListDto myCommentListDto) {
        refreshLayout.finishRefresh();
        List<MyCommentListDto.DataBean> beans = myCommentListDto.getData();
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
