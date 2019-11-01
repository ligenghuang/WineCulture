package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyCollectAction;
import com.zhifeng.wineculture.adapters.CollectionListAdapter;
import com.zhifeng.wineculture.modules.CollectionListDto;
import com.zhifeng.wineculture.ui.impl.MyCollectView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.dialog.CollectMoreDialog;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 我的收藏
 * @Author: Administrator
 * @Date: 2019/9/28 18:05
 */
public class MyCollectActivity extends UserBaseActivity<MyCollectAction> implements MyCollectView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    CollectionListAdapter collectionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        refreshLayout.setEnableLoadMore(false);
        collectionListAdapter = new CollectionListAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(collectionListAdapter);

        refreshLayout.autoRefresh();
        loadView();
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
                .addTag("MyCollectActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_mycollect);
    }

    @Override
    protected MyCollectAction initAction() {
        return new MyCollectAction(this,this);
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCollectionList();
            }
        });

        collectionListAdapter.setOnClickListener(new CollectionListAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                CollectMoreDialog collectMoreDialog = new CollectMoreDialog(mContext,R.style.Collect_Dialog);
                collectMoreDialog.setOnClickListener(new CollectMoreDialog.OnClickListener() {
                    @Override
                    public void onCancelCollect() {
                        deleteCollection(id+"");
                        collectMoreDialog.dismiss();
                    }
                });
                collectMoreDialog.show();
            }
        });

    }

    /**
     * 获取关注列表
     */
    @Override
    public void getCollectionList() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getCollectionList();
        }else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 获取关注列表成功
     * @param collectionListDto
     */
    @Override
    public void getCollectionListSuccess(CollectionListDto collectionListDto) {
        loadDiss();
        refreshLayout.finishRefresh();
        collectionListAdapter.refresh(collectionListDto.getData());

    }

    /**
     * 取消关注
     * @param goods_id
     */
    @Override
    public void deleteCollection(String goods_id) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.deleteCollection(goods_id);
        }
    }

    /**
     * 取消关注成功
     * @param msg
     */
    @Override
    public void deleteCollectionSuccess(String msg) {
        getCollectionList();
    }

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
