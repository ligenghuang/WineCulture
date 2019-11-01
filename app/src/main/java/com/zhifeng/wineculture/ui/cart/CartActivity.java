package com.zhifeng.wineculture.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CartAction;
import com.zhifeng.wineculture.adapters.CartListAdapter;
import com.zhifeng.wineculture.modules.CartListDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.CartView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 购物车
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/30 15:39
 * @Version: 1.0
 */

public class CartActivity extends UserBaseActivity<CartAction> implements CartView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.tv_cart_detele)
    TextView tvCartDetele;
    @BindView(R.id.iv_cart_menu)
    ImageView ivCartMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_cart_selector_all)
    TextView tvCartSelectorAll;
    @BindView(R.id.tv_cart_total_price)
    TextView tvCartTotalPrice;
    @BindView(R.id.ll_cart_data)
    LinearLayout llCartData;
    @BindView(R.id.ll_cart_null)
    LinearLayout llCartNull;

    CartListAdapter cartListAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected CartAction initAction() {
        return new CartAction(this, this);
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
                .addTag("CartActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.cart_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        cartListAdapter = new CartListAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(cartListAdapter);

        loadDialog();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        cartListAdapter.setOnClickListener(new CartListAdapter.OnClickListener() {
            @Override
            public void selectedListener(int id) {
                List<CartListDto.DataBean> list = cartListAdapter.getAllData();
                int num = 0;
                double total = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCart_id() == id) {
                        list.get(i).setSelected(list.get(i).getSelected() == 1 ? 0 : 1);
                    }
                    if (list.get(i).getSelected() == 1) {
                        num++;//todo 计算选中数量
                        double totalPrice = Double.parseDouble(list.get(i).getGoods_price()) * list.get(i).getGoods_num();
                        total = total + totalPrice;
                    }
                }
                tvCartTotalPrice.setText(ResUtil.getFormatString(R.string.cart_tab_12, total + ""));
                tvCartSelectorAll.setSelected(num == list.size());
                cartListAdapter.notifyDataSetChanged();
            }

            @Override
            public void goodsNumListener() {
                //todo 计算总价
                List<CartListDto.DataBean> list = cartListAdapter.getAllData();
                double total = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getSelected() == 1) {
                        double totalPrice = Double.parseDouble(list.get(i).getGoods_price()) * list.get(i).getGoods_num();
                        total = total + totalPrice;
                    }
                }
                tvCartTotalPrice.setText(ResUtil.getFormatString(R.string.cart_tab_12, total + ""));
            }

            @Override
            public void editGoodsNum(int id, int num) {
                editCart(id + "", num + "");
            }
        });
    }

    /**
     * 获取购物车列表
     */
    @Override
    public void getCartList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getCartList();
        }
    }

    /**
     * 获取购物车列表成功
     *
     * @param cartListDto
     */
    @Override
    public void getCartListSuccess(CartListDto cartListDto) {
        loadDiss();
        if (cartListDto.getData().size() != 0) {
            llCartData.setVisibility(View.VISIBLE);
            llCartNull.setVisibility(View.GONE);
            cartListAdapter.refresh(cartListDto.getData());
            fTitleTv.setText(ResUtil.getFormatString(R.string.cart_tab_13, cartListDto.getData().size() + ""));
            double total = 0;
            int num = 0;
            for (int i = 0; i < cartListDto.getData().size(); i++) {
                if (cartListDto.getData().get(i).getSelected() == 1) {
                    double totalPrice = Double.parseDouble(cartListDto.getData().get(i).getGoods_price()) * cartListDto.getData().get(i).getGoods_num();
                    total = total + totalPrice;
                    num++;
                }
            }
            tvCartTotalPrice.setText(ResUtil.getFormatString(R.string.cart_tab_12, total + ""));
            tvCartSelectorAll.setSelected(num == cartListDto.getData().size());
        } else {
            llCartData.setVisibility(View.GONE);
            llCartNull.setVisibility(View.VISIBLE);
            fTitleTv.setText(ResUtil.getString(R.string.cart_tab_1));
        }
    }

    /**
     * 删除购物车商品
     *
     * @param id
     */
    @Override
    public void delCart(String id) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.delCart(id);
        }
    }

    /**
     * 删除购物车商品成功
     */
    @Override
    public void delCartSuccess() {
        showNormalToast(ResUtil.getString(R.string.cart_tab_11));
        getCartList();
    }


    @Override
    public void editCart(String id, String num) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.editCart(id, num);
        }
    }

    @Override
    public void editCartSuccess() {

    }

    @Override
    public void editCartError(String msg) {
        showNormalToast(msg);
        getCartList();
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
        showNormalToast(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
        getCartList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    /**
     * 全选或反选
     */
    private void setSelected() {
        List<CartListDto.DataBean> list = cartListAdapter.getAllData();
        double total = 0;
        if (tvCartSelectorAll.isSelected()) {
            //todo 已全选中
            for (int i = 0; i < list.size(); i++) {
                //todo 进行反选
                list.get(i).setSelected(0);
            }
        } else {
            //todo 未全选中
            for (int i = 0; i < list.size(); i++) {
                //todo 进行全选
                list.get(i).setSelected(1);
                double totalPrice = Double.parseDouble(list.get(i).getGoods_price()) * list.get(i).getGoods_num();
                total = total + totalPrice;
            }
        }
        tvCartTotalPrice.setText(ResUtil.getFormatString(R.string.cart_tab_12, total + ""));
        tvCartSelectorAll.setSelected(!tvCartSelectorAll.isSelected());
        cartListAdapter.notifyDataSetChanged();
    }

    /**
     * 删除购物车商品
     */
    private void delete() {
        List<CartListDto.DataBean> list = cartListAdapter.getAllData();
        String id = "";
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected() == 1) {
                num++;
                //todo 拼接id
                if (i == 0) {
                    id = list.get(i).getCart_id() + "";
                } else {
                    id = id + "," + list.get(i).getCart_id();
                }
            }
        }
        //todo 判断是否有选中的商品
        if (num == 0) {
            showNormalToast(ResUtil.getString(R.string.cart_tab_10));
            return;
        }

        //todo 请求接口
        delCart(id);

    }

    @OnClick({R.id.tv_cart_detele, R.id.iv_cart_menu, R.id.tv_cart_selector_all, R.id.tv_cart_settlement, R.id.tv_to_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cart_detele:
                //todo 删除
                delete();
                break;
            case R.id.iv_cart_menu:
                //todo 导航栏右边菜单
                new XPopup.Builder(mActicity)
                        .hasShadowBg(false)
                        .atView(ivCartMenu)
                        .hasStatusBarShadow(true) //启用状态栏阴影
                        .asAttachList(new String[]{"首页", "分类", "购物车", "我的"},
                                new int[]{R.drawable.icon_home_y, R.drawable.icon_classify_y,
                                        R.drawable.icon_shopping_cart_y, R.drawable.icon_my_y},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        //todo 点击事件
                                        if (position != 2) {
                                            MainActivity.Position = position;
                                            startActivity(new Intent(mContext, MainActivity.class));
                                            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class, CartActivity.class);
                                            finish();
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.tv_cart_selector_all:
                //todo 全选或取消全选
                setSelected();
                break;
            case R.id.tv_cart_settlement:
                //todo 结算
                settlement();
                break;
            case R.id.tv_to_home:
                //todo 返回首页
                MainActivity.Position = 0;
//                startActivity(new Intent(mContext, MainActivity.class));
                ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class, CartActivity.class);
                finish();
                break;
        }
    }

    /**
     * 结算
     */
    private void settlement() {
        List<CartListDto.DataBean> listDtos = cartListAdapter.getAllData();
        String id = "";
        int num = 0;
        for (int i = 0; i < listDtos.size(); i++) {
            if (listDtos.get(i).getSelected() == 1) {
                num++;
                if (i == 0) {
                    id = listDtos.get(i).getCart_id() + "";
                } else {
                    id = id + "," + listDtos.get(i).getCart_id();
                }
            }
        }

        if (num == 0) {
            showNormalToast(ResUtil.getString(R.string.cart_tab_10));
            return;
        }

        Intent intent = new Intent(mContext, CartSubmitOrdersActivity.class);
        intent.putExtra("cartId", id);
        startActivity(intent);
    }
}
