package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AddressListAction;
import com.zhifeng.wineculture.adapters.AddressListAdapter;
import com.zhifeng.wineculture.modules.AddressListDto;
import com.zhifeng.wineculture.modules.GeneralDto;
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

    AddressListAdapter addressListAdapter;

    boolean isGoods = false;
    @BindView(R.id.tv_null_data)
    TextView tvNullData;

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
        return new AddressListAction(this, this);
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

        isGoods = getIntent().getBooleanExtra("isGoods", false);

        refreshLayout.setEnableLoadMore(false);
        addressListAdapter = new AddressListAdapter(isGoods);
        rvAddressList.setLayoutManager(new LinearLayoutManager(this));
        rvAddressList.setAdapter(addressListAdapter);

        loadDialog();
        getAddressList();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getAddressList();
            }
        });

        addressListAdapter.setOnClickListener(new AddressListAdapter.OnClickListener() {
            @Override
            public void itemView(AddressListDto.DataBean model) {
                if (isGoods) {
                    jumpActivity(model);
                } else {
                    //编辑
                    Intent intent = new Intent(mContext, AddAddressActivity.class);
                    intent.putExtra("address_id", model.getAddress_id());
                    startActivity(intent);
                }

            }

            @Override
            public void Detele(int id) {
                //删除
                deteleAddress(id);
            }

        });
    }

    /**
     * 跳转页面
     */
    private void jumpActivity(AddressListDto.DataBean model) {
        Intent intent = new Intent();
        intent.putExtra("address", model.getP_cn() + model.getC_cn() + model.getD_cn());
        intent.putExtra("phone", model.getMobile());
        intent.putExtra("address_id", model.getAddress_id());
        intent.putExtra("consignee", model.getConsignee());
        intent.putExtra("address2", model.getAddress());
        setResult(200, intent);
        finish();
    }

    /**
     * 获取地址列表
     */
    @Override
    public void getAddressList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getAddressList();
        } else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 获取地址列表成功
     *
     * @param addressListDto
     */
    @Override
    public void getAddressListSuccess(AddressListDto addressListDto) {
        loadDiss();
        refreshLayout.finishRefresh();
        if (addressListDto.getData().size() != 0) {
            rvAddressList.setVisibility(View.VISIBLE);
            tvNullData.setVisibility(View.GONE);
            addressListAdapter.refresh(addressListDto.getData());
        } else {
            //todo 2019/09/12 添加空布局
            rvAddressList.setVisibility(View.GONE);
            tvNullData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getAddressListNull() {
        loadDiss();
        refreshLayout.finishRefresh();
        //todo 2019/09/12 添加空布局
        rvAddressList.setVisibility(View.GONE);
        tvNullData.setVisibility(View.VISIBLE);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    @Override
    public void deteleAddress(int id) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.deteleAddress(id);
        }
    }

    /**
     * 删除地址成功
     *
     * @param generalDto
     */
    @Override
    public void deteleAddressSuccess(GeneralDto generalDto) {
        showNormalToast(ResUtil.getString(R.string.address_list_tab_3));
        getAddressList();
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
                jumpActivityNotFinish(mContext, AddAddressActivity.class);
                break;
        }
    }

}
