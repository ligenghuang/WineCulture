package com.zhifeng.wineculture.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.base.MyFragmentPagerAdapter;
import com.lgh.huanglib.util.cusview.CustomViewPager;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnTouch;

/**
 * @ClassName:
 * @Description: 我的订单
 * @Author: Administrator
 * @Date: 2019/9/28 18:06
 */
public class MyOrderActivity extends UserBaseActivity {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvAllOrders)
    TextView tvAllOrders;
    @BindView(R.id.tvObligation)
    TextView tvObligation;
    @BindView(R.id.tvToBeShipped)
    TextView tvToBeShipped;
    @BindView(R.id.tvToBeReceived)
    TextView tvToBeReceived;
    @BindView(R.id.tvToBeComment)
    TextView tvToBeComment;
    @BindView(R.id.vp)
    CustomViewPager vp;
    private final int position0 = 0;
    private final int position1 = 1;
    private final int position2 = 2;
    private final int position3 = 3;
    private final int position4 = 4;
    public int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
        currentPosition = getIntent().getIntExtra("position", 0);
    }

    @Override
    protected void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        //全部
        OrderFragment allOrderFragment = new OrderFragment(position0);
        allOrderFragment.setUserVisibleHint(currentPosition == position0);
        fragments.add(allOrderFragment);
        //待付款
        OrderFragment obligationFragment = new OrderFragment(position1);
        obligationFragment.setUserVisibleHint(currentPosition == position1);
        fragments.add(obligationFragment);
        //待发货
        OrderFragment toBeShippedFragment = new OrderFragment(position2);
        toBeShippedFragment.setUserVisibleHint(currentPosition == position2);
        fragments.add(toBeShippedFragment);
        //待收货
        OrderFragment toBeReceivedFragment = new OrderFragment(position3);
        toBeReceivedFragment.setUserVisibleHint(currentPosition == position3);
        fragments.add(toBeReceivedFragment);
        //待评价
        OrderFragment toBeCommentFragment = new OrderFragment(position4);
        toBeCommentFragment.setUserVisibleHint(currentPosition == position4);
        fragments.add(toBeCommentFragment);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        new Handler().postDelayed(() -> {
            vp.setCurrentItem(currentPosition);
            setSelect(currentPosition);
            vp.setOffscreenPageLimit(fragments.size());
        }, 200);
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
                .addTag("MyOrderActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_myorder);
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    private void setSelect(int position) {
        tvAllOrders.setSelected(position == position0);
        tvObligation.setSelected(position == position1);
        tvToBeShipped.setSelected(position == position2);
        tvToBeReceived.setSelected(position == position3);
        tvToBeComment.setSelected(position == position4);
    }

    @OnTouch({R.id.tvAllOrders, R.id.tvObligation, R.id.tvToBeShipped, R.id.tvToBeReceived, R.id.tvToBeComment})
    public boolean onViewClicked(View view) {
        int position = position0;
        switch (view.getId()) {
            case R.id.tvObligation:
                position = position1;
                break;
            case R.id.tvToBeShipped:
                position = position2;
                break;
            case R.id.tvToBeReceived:
                position = position3;
                break;
            case R.id.tvToBeComment:
                position = position4;
                break;
        }
        currentPosition=position;
        vp.setCurrentItem(position);
        setSelect(position);
        return  false;
    }
}
