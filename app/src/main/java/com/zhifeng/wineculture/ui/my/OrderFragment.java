package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.DensityUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.OrderListAction;
import com.zhifeng.wineculture.adapters.OrderAdapter;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.ui.impl.OrderListView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.view.VerticalItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFragment extends UserBaseFragment<OrderListAction> implements OrderListView {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int position;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean isMore = true;
    private OrderAdapter adapter;

    public OrderFragment(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        binding();
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible && position == ((MyOrderActivity) mActivity).currentPosition) {
            rv.setVisibility(View.GONE);
            refreshLayout.autoRefresh();
        }
    }

    @Override
    protected OrderListAction initAction() {
        return new OrderListAction((RxAppCompatActivity) mActivity, this);
    }

    @Override
    protected void initialize() {
        adapter = new OrderAdapter(mContext);
        rv.addItemDecoration(new VerticalItemDecoration(DensityUtil.dp2px(5)));
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isRefresh = true;
                getOrderList();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                getOrderList();
            }
        });
        adapter.setOnItemClickListener((parent, view, position, id) -> {
            OrderListDto.DataBean dataBean = (OrderListDto.DataBean) adapter.getItem(position);
            String order_id = String.valueOf(dataBean.getOrder_id());
            Intent i = new Intent(mContext, OrderDetailActivity.class);
            i.putExtra("order_id", order_id);
            mContext.startActivity(i);
        });

        adapter.setOnButtonClickListener(new OrderAdapter.OnButtonClickListener() {
            @Override
            public void cancel(int order_id) {

            }

            @Override
            public void confirmToReceive(int order_id) {

            }

            @Override
            public void payNow(int order_id, int pay_type) {

            }

            @Override
            public void refund(int order_id) {

            }

            @Override
            public void lookUpLogistics(int order_id) {

            }

            @Override
            public void comment(int order_id, int goods_id, int sku_id) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("sku_id", sku_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getOrderList() {
        String type = "all";
        if (position == 1) {
            type = "dfk";
        } else if (position == 2) {
            type = "dfh";
        } else if (position == 3) {
            type = "dsh";
        } else if (position == 4) {
            type = "tk";
        }
        baseAction.getOrderList(page, type);
    }

    @Override
    public void getOrderListSuccess(OrderListDto orderList) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        page++;
        List<OrderListDto.DataBean> dataBeans = orderList.getData();
        if (dataBeans.size() != 0) {
            rv.setVisibility(View.VISIBLE);
//            isMore = page > orderList.getData().;
            loadSwapTab();
            if (isRefresh) {
                adapter.refresh(dataBeans);
            } else {
                adapter.loadMore(dataBeans);
            }
        } else {
            isMore = false;
            loadSwapTab();
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

    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }
}
