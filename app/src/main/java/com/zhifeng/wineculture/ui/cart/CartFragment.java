package com.zhifeng.wineculture.ui.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CartAction;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.CartView;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 购物车fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:40
 * @Version: 1.0
 */

public class CartFragment extends UserBaseFragment<CartAction> implements CartView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.tv_cart_title)
    TextView tvCartTitle;
    @BindView(R.id.iv_cart_menu)
    ImageView ivCartMenu;
    @BindView(R.id.tv_cart_detele)
    TextView tvCartDetele;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_cart_selector_all)
    TextView tvCartSelectorAll;
    @BindView(R.id.tv_cart_total_price)
    TextView tvCartTotalPrice;
    @BindView(R.id.tv_cart_settlement)
    TextView tvCartSettlement;
    @BindView(R.id.ll_cart_data)
    LinearLayout llCartData;
    @BindView(R.id.ll_cart_null)
    LinearLayout llCartNull;

    @Override
    protected CartAction initAction() {
        return new CartAction((RxAppCompatActivity) getActivity(), this);
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
        tvCartDetele.setVisibility(View.GONE);//todo 显示空布局时 隐藏删除按钮
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    public void getCartList() {

    }

    @Override
    public void getCartListSuccess() {

    }

    /**
     * 失败
     *
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        refreshLayout.finishRefresh();
        loadDiss();
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

    @OnClick({R.id.tv_cart_detele, R.id.tv_cart_selector_all, R.id.tv_cart_settlement, R.id.tv_to_home,R.id.iv_cart_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cart_detele:
                //todo 删除
                break;
            case R.id.tv_cart_selector_all:
                //todo 全选或取消全选
                break;
            case R.id.tv_cart_settlement:
                //todo 结算
                break;
            case R.id.tv_to_home:
                //todo 返回首页
                MainActivity.Position = 0;
                ((MainActivity)mActivity).setSelectedLin();
                break;
            case R.id.iv_cart_menu:
                //todo 导航栏右边菜单
                new XPopup.Builder(getActivity())
                        .hasShadowBg(false)
                        .atView(ivCartMenu)
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asAttachList(new String[]{"首页", "分类", "购物车", "我的"},
                                new int[]{R.drawable.icon_home_y,R.drawable.icon_classify_y,
                                        R.drawable.icon_shopping_cart_y,R.drawable.icon_my_y},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        //todo 点击事件
                                        if (position != 2) {
                                            MainActivity.Position = position;
                                            ((MainActivity)mActivity).setSelectedLin();
                                        }
                                    }
                                })
                        .show();
                break;
        }
    }
}
