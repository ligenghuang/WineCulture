package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.DensityUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.OrderListAction;
import com.zhifeng.wineculture.adapters.OrderAdapter;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.OrderListDto;
import com.zhifeng.wineculture.modules.PayOrderDto;
import com.zhifeng.wineculture.modules.post.SubmitOrderPost;
import com.zhifeng.wineculture.ui.impl.OrderListView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.dialog.BuyPwdDialog;
import com.zhifeng.wineculture.utils.view.VerticalItemDecoration;

import java.util.ArrayList;
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
    private BuyPwdDialog bugPwdDialog;

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
        if (isVisible && ((MyOrderActivity) mActivity).currentPosition == position) {
            baseAction.toRegister();
            rv.setVisibility(View.GONE);
            getOrderList();
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
                if (position == ((MyOrderActivity) mActivity).currentPosition) {
                    getOrderList();
                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (position == ((MyOrderActivity) mActivity).currentPosition) {
                    loadMoreOrderList();
                }
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
            public void cancelOrConfirmToReceive(int order_id, int status) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                View view = View.inflate(mContext, R.layout.layout_dialog_delete, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                TextView tvTitle = view.findViewById(R.id.tv_title);
                tvTitle.setText(status == 1 ? R.string.orderdetail_cancel : R.string.myorder_confirmtakeover);
                TextView tvCancel = view.findViewById(R.id.tv_cancel);
                TextView tvConfirm = view.findViewById(R.id.tv_confirm);
                tvCancel.setOnClickListener(v -> {
                    alertDialog.dismiss();
                });
                tvConfirm.setOnClickListener(v -> {
                    //todo 取消订单/确认收货
                    alertDialog.dismiss();
                    baseAction.cancelOrderOrConfirmToReceive(order_id, status);
                });
                alertDialog.show();
            }


            public void payNow(int order_id, int pay_type, double totalPrice) {
                Intent detail = new Intent(mContext, OrderDetailActivity.class);
                detail.putExtra("order_id", order_id + "");
                startActivity(detail);
                //todo 立即付款
//                switch (pay_type) {
//                    case 1:
//                        //余额支付
//                        if (MySp.getPwd(mContext) == 1) {
//                            bugPwdDialog = new BuyPwdDialog(mContext, R.style.MY_AlertDialog, totalPrice, "余额支付");
//                            bugPwdDialog.setOnFinishInput(new BuyPwdDialog.OnFinishInput() {
//                                @Override
//                                public void inputFinish(String password) {
//                                    //支付订单
//                                    SubmitOrderPost post = new SubmitOrderPost();
//                                    post.setCart_id(order_id + "");
//                                    post.setPay_type(pay_type + "");
//                                    post.setPwd(password);
//                                    payOrder(post);
//                                }
//
//                                @Override
//                                public void close() {
//
//                                }
//                            });
//                            bugPwdDialog.show();
//                        } else {
//                            showToast(ResUtil.getString(R.string.goods_detail_tab_30));
//                            new Handler().postDelayed(() -> {
//                                Intent intent = new Intent(mContext, ForgetPwdActivity.class);
//                                intent.putExtra("phone", MySp.getMobile(mContext));
//                                intent.putExtra("type", 1);
//                                intent.putExtra("isOrder", true);
//                                startActivity(intent);
//                            }, 2000);
//                        }
//                        break;
//                    default:
//                        break;
//                }
            }

            @Override
            public void refund(int order_id) {
                //todo 退货
                Intent intent = new Intent(mContext, RefundActivity.class);
                intent.putExtra("order_id", String.valueOf(order_id));
                startActivity(intent);
            }

            @Override
            public void lookUpLogistics(int order_id) {
                //todo 查看物流
                Intent logistics = new Intent(mContext, LogisticsActivity.class);
                logistics.putExtra("orderId", order_id);
                startActivity(logistics);
            }

            @Override
            public void comment(int order_id) {
                //todo 评价
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("order_id", String.valueOf(order_id));
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
            type = "dpj";
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            page = 1;
            isRefresh = true;
            baseAction.getOrderList(page, type);
        } else {
            refreshLayout.finishRefresh();
        }
    }

    private void loadMoreOrderList() {
        String type = "all";
        if (position == 1) {
            type = "dfk";
        } else if (position == 2) {
            type = "dfh";
        } else if (position == 3) {
            type = "dsh";
        } else if (position == 4) {
            type = "dpj";
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getOrderList(page, type);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void getOrderListSuccess(OrderListDto orderList) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        List<OrderListDto.DataBean> dataBeans = orderList.getData();
        if (dataBeans.size() > 0) {
            rv.setVisibility(View.VISIBLE);
            if (isRefresh) {
                adapter.refresh(dataBeans);
            } else {
                adapter.loadMore(dataBeans);
            }
        } else {
            if (isRefresh) {
                adapter.refresh(new ArrayList<>());
            } else {
                page--;
                isMore = false;
                loadSwapTab();
            }
        }
    }

    @Override
    public void onError(String message, int code) {
        showToast(message);
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
            page--;
        }
    }

    /**
     * 支付
     *
     * @param submitOrderPost
     */
    @Override
    public void payOrder(SubmitOrderPost submitOrderPost) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.payOrder(submitOrderPost);
        }
    }

    /**
     * 支付成功
     *
     * @param submitOrderDto
     */
    @Override
    public void payOrderSuccess(PayOrderDto submitOrderDto) {
        if (bugPwdDialog != null) {
            bugPwdDialog.dismiss();
        }
        loadDiss();
        showToast(ResUtil.getString(R.string.goods_detail_tab_29));
        refreshLayout.autoRefresh();
    }

    /**
     * 支付失败
     *
     * @param msg
     */
    @Override
    public void payOrderError(String msg, int code) {
        L.e("lgh_pay", "输出返回结果4" + msg);
        loadDiss();
        showToast(msg);
    }

    @Override
    public void cancelOrderOrConfirmToReceiveSuccess(GeneralDto generalDto) {
        showToast(generalDto.getMsg());
        refreshLayout.autoRefresh();
    }

    @Override
    public void cancelOrderOrConfirmToReceiveFail(String msg, int code) {
        showToast(msg);
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
