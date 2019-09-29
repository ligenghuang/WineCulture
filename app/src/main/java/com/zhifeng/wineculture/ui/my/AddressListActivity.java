package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AddressListAction;
import com.zhifeng.wineculture.ui.impl.AddressListView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 收货地址管理
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 17:49
 * @Version: 1.0
 */

public class AddressListActivity extends UserBaseActivity<AddressListAction> implements AddressListView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_address_list)
    RecyclerView rvAddressList;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    int page = 1;
    boolean isRefresh = true;
    //是否加载更多
    private boolean isMore = false;

    @Override
    public int intiLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected AddressListAction initAction() {
        return new AddressListAction(this,this);
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
                .addTag("AddressListActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_myaddress));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

    }

    /**
     * 刷新收货地址
     */
     @Override
    public void getAddressList() {
        if (CheckNetwork.checkNetwork2(mContext)){
            isRefresh = true;
            page = 1;
            baseAction.getAddressList(page);
        }
    }

    /**
     * 加载更多收货地址
     */
    @Override
    public void loadMoreAddressList() {
        if (CheckNetwork.checkNetwork2(mContext)){
            isRefresh = false;
            page++;
            baseAction.getAddressList(page);
        }
    }

    /**
     * 获取收货地址列表 成功
     */
    @Override
    public void getAddressListSuccess() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    /**
     * @param message
     * @param code
     */
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
        getAddressList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @OnClick(R.id.ll_add_address)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_address:
                //todo 新增收货地址
                jumpActivityNotFinish(mContext,AddAddressActivity.class);
                break;
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
}
