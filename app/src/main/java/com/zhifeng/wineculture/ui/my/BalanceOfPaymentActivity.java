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
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnTouch;

/**
 * @ClassName: 我的收支
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 10:40
 * @Version: 1.0
 */

public class BalanceOfPaymentActivity extends UserBaseActivity {

    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;
    @BindView(R.id.my_pager)
    CustomViewPager myPager;

    BalanceOfPaymentFragment balanceOfPaymentFragment1;
    BalanceOfPaymentFragment balanceOfPaymentFragment2;

    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int fragmentSize = 2;

    @Override
    public int intiLayout() {
        return R.layout.activity_balance_of_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
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
                .addTag("BalanceOfPaymentActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.member_center_tab_4));

        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < fragmentSize; i++) {
            switch (i) {
                case POIONTONE://
                    balanceOfPaymentFragment1 = new BalanceOfPaymentFragment(POIONTONE);
                    if (Position != POIONTONE) {
                        balanceOfPaymentFragment1.setUserVisibleHint(false);//
                    }

                    fragments.add(balanceOfPaymentFragment1);
                    break;
                case POIONTTWO://
                    balanceOfPaymentFragment2 = new BalanceOfPaymentFragment(POIONTTWO);
                    if (Position != POIONTTWO) {
                        balanceOfPaymentFragment2.setUserVisibleHint(false);//
                    }
                    fragments.add(balanceOfPaymentFragment2);
                    break;

                default:
                    break;
            }
        }

        fragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);

        fragmentPagerAdapter.setFragments(fragments);
        setSelectedLin(Position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myPager.setAdapter(fragmentPagerAdapter);
                myPager.setCurrentItem(Position, false);
                myPager.setOffscreenPageLimit(fragmentSize);


            }
        }, 500);

    }
    @OnTouch({R.id.tv_tab_1, R.id.tv_tab_2})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_1:
                Position = POIONTONE;
                break;
            case R.id.tv_tab_2:
                Position = POIONTTWO;
                break;

            default:
                break;
        }
        setSelectedLin(Position);
        myPager.setCurrentItem(Position, false);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    public void setSelectedLin(int position) {
        tvTab1.setSelected(false);
        tvTab2.setSelected(false);
        //设置状态栏黑色字体与图标
//        QMUIStatusBarHelper.setStatusBarLightMode(this);
        switch (position) {
            case 0:
                tvTab1.setSelected(true);
                break;
            case 1:
                tvTab2.setSelected(true);
                break;
            default:
                break;
        }
    }

}
