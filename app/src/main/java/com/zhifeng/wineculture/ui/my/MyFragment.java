package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.config.GlideUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyAction;
import com.zhifeng.wineculture.modules.UserInfoDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.MyView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.data.MySp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 我的fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/28 14:41
 * @Version: 1.0
 */

public class MyFragment extends UserBaseFragment<MyAction> implements MyView {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvId)
    TextView tvId;
    @BindView(R.id.tvMobile)
    TextView tvMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        ImmersionBar.setStatusBarView(getActivity(), topView);
        binding();
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            getUserInfo();
        }
    }

    @Override
    protected MyAction initAction() {
        return new MyAction((RxAppCompatActivity) mActivity, this);
    }

    @Override
    protected void initialize() {

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

    @Override
    public void getUserInfo() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getUserInfo();
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoDto userInfoDto) {
        UserInfoDto.DataBean dataBean = userInfoDto.getData();
        GlideUtil.setImageCircle(mContext, dataBean.getAvatar(), iv, R.drawable.icon_avatar);
        tvName.setText(dataBean.getRealname());
        tvId.setText(String.valueOf(dataBean.getId()));
        String mobile = dataBean.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tvMobile.setText(mobile);
    }

    @Override
    public void noLogin() {
        loadDiss();
        showToast(R.string.my_nologin);
        MainActivity.Position = 0;
        MySp.clearAllSP(mContext);
        jumpActivityNotFinish(mContext, LoginActivity.class);
    }

    @Override
    public void onError(String message, int code) {
        showToast(message);
    }

    @OnClick({R.id.tvMyOrder, R.id.tv_obligation, R.id.tv_toBeShipped, R.id.tv_toBeReceived,
            R.id.tv_toBeComment, R.id.tv_service, R.id.tv_memberCenter, R.id.tv_myteam,
            R.id.tv_popularize, R.id.tv_proxy, R.id.tv_myCollect, R.id.tv_myFootprint,
            R.id.tv_myComments, R.id.tv_myAddress, R.id.tv_aboutWineCulture, R.id.iv_booking_goods})
    public void onViewClicked(View view) {
        int position = -1;
        switch (view.getId()) {
            case R.id.tvMyOrder:
                //todo 我的订单
                position = 0;
                break;
            case R.id.tv_obligation:
                //todo 待付款
                position = 1;
                break;
            case R.id.tv_toBeShipped:
                //todo 待发货
                position = 2;
                break;
            case R.id.tv_toBeReceived:
                //todo 待收货
                position = 3;
                break;
            case R.id.tv_toBeComment:
                //todo 待评价
                position = 4;
                break;
            case R.id.tv_service:
                //todo 售后
                jumpActivityNotFinish(mContext, ServiceActivity.class);
                break;
            case R.id.tv_memberCenter:
                //todo 会员中心
                jumpActivityNotFinish(mContext, MemberCenterActivity.class);
                break;
            case R.id.tv_myteam:
                //todo 我的团队
                jumpActivityNotFinish(mContext, MyTeamActivity.class);
                break;
            case R.id.tv_popularize:
                //todo 我要推广
                jumpActivityNotFinish(mContext, PopularizeActivity.class);
                break;
            case R.id.tv_proxy:
                //todo 代理预约
//                jumpActivityNotFinish(mContext, ProxyActivity.class);
                showToast("此功能待开发");
                break;
            case R.id.tv_myCollect:
                //todo 我的收藏
                jumpActivityNotFinish(mContext, MyCollectActivity.class);
                break;
            case R.id.tv_myFootprint:
                //todo 我的足迹
                jumpActivityNotFinish(mContext, MyFootPrintActivity.class);
                break;
            case R.id.tv_myComments:
                //todo 我的评论
                jumpActivityNotFinish(mContext, MyCommentsActivity.class);
                break;
            case R.id.tv_myAddress:
                //todo 收货地址
                jumpActivityNotFinish(mContext, AddressListActivity.class);
                break;
            case R.id.tv_aboutWineCulture:
                //todo 关于酒文化
                jumpActivityNotFinish(mContext, AboutWineCultureActivity.class);
                break;
            case R.id.iv_booking_goods:
                //todo 我要预约商品
                jumpActivityNotFinish(mContext, BookingGoodsActivity.class);
                break;
        }
        if (position != -1) {
            //todo 我的订单
            Intent i = new Intent(mContext, MyOrderActivity.class);
            i.putExtra("position", position);
            startActivity(i);
        }
    }
}
