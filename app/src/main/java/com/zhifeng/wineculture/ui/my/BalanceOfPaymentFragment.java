package com.zhifeng.wineculture.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BalanceOfPaymentAction;
import com.zhifeng.wineculture.adapters.BalanceOfPaymentAdapter;
import com.zhifeng.wineculture.modules.BalanceOfPaymentDto;
import com.zhifeng.wineculture.ui.impl.BalanceOfPaymentView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: 收支fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 10:59
 * @Version: 1.0
 */

public class BalanceOfPaymentFragment extends UserBaseFragment<BalanceOfPaymentAction> implements BalanceOfPaymentView {
    View view;

    BalanceOfPaymentAdapter balanceOfPaymentAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    int type;


    @Override
    protected BalanceOfPaymentAction initAction() {
        return new BalanceOfPaymentAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {
        init();
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_balance_of_payment, container, false);
        ButterKnife.bind(this, view);
        binding();
        return view;
    }

    @Override
    protected void init() {
        super.init();
        refreshLayout.setEnableLoadMore(false);

        balanceOfPaymentAdapter = new BalanceOfPaymentAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(balanceOfPaymentAdapter);


    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        recyclerview.setVisibility(View.GONE);
        if (isVisible &&((BalanceOfPaymentActivity) mActivity).Position == type){
            refreshLayout.autoRefresh();
        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBalanceOfPayment();
            }
        });
    }

    /**
     * 获取我的收支
     */
    @Override
    public void getBalanceOfPayment() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getBalanceOfPayment(type);
        }
    }

    /**
     * 获取我的收支成功
     * @param balanceOfPaymentDto
     */
    @Override
    public void getBalanceOfPaymentSuccess(BalanceOfPaymentDto balanceOfPaymentDto) {
        refreshLayout.finishRefresh();
        balanceOfPaymentAdapter.refresh(balanceOfPaymentDto.getData().getList());
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
        showToast(message);
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    public static BalanceOfPaymentFragment newInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", state);
        BalanceOfPaymentFragment testFm = new BalanceOfPaymentFragment();
        testFm.setArguments(bundle);
        return testFm;
    }
}
