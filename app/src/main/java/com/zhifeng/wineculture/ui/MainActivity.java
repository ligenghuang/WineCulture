package com.zhifeng.wineculture.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.base.MyFragmentPagerAdapter;
import com.lgh.huanglib.util.cusview.CustomViewPager;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.ui.cart.CartFragment;
import com.zhifeng.wineculture.ui.classify.ClassifyFragment;
import com.zhifeng.wineculture.ui.home.HomeFragment;
import com.zhifeng.wineculture.ui.my.MyFragment;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.data.MySp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnTouch;

public class MainActivity extends UserBaseActivity {

    private static final String TAG = "MainActivity";
    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;
    private static final int POIONTTHREE = 2;
    private static final int POIONTFOUR = 3;

    HomeFragment homeFragment;
    ClassifyFragment classifyFragment;
    CartFragment cartFragment;
    MyFragment myFragment;

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.my_pager)
    CustomViewPager myPager;
    @BindView(R.id.lin_1)
    LinearLayout lin1;
    @BindView(R.id.lin_2)
    LinearLayout lin2;
    @BindView(R.id.lin_3)
    LinearLayout lin3;
    @BindView(R.id.lin_4)
    LinearLayout lin4;
    @BindView(R.id.activity_first_main_device)
    FrameLayout activityFirstMainDevice;

    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int fragmentSize = 4;
    // 是否能够退出
    private boolean isBack = false;

    // 上次按退出的时间
    private long downTime;


    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
        //解决布局被底部虚拟按键遮住问题
        initSystemBarTint();
    }

    /**
     * 隐藏底部虚拟按钮
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);

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
        mImmersionBar
//                .statusBarView(R.id.top_view)
                .fullScreen(true)
                .navigationBarWithKitkatEnable(false)
                .statusBarDarkFont(true)
                .addTag("main")  //给上面参数打标记，以后可以通过标记恢复
                .init();

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
                    homeFragment = new HomeFragment();
                    if (Position != POIONTONE) {
                        homeFragment.setUserVisibleHint(false);//
                    }

                    fragments.add(homeFragment);
                    break;
                case POIONTTWO://
                    classifyFragment = new ClassifyFragment();
                    if (Position != POIONTTWO) {
                        classifyFragment.setUserVisibleHint(false);//
                    }
                    fragments.add(classifyFragment);
                    break;
                case POIONTTHREE:
                    cartFragment = new CartFragment();
                    if (Position != POIONTTHREE) {
                        cartFragment.setUserVisibleHint(false);//
                    }
                    fragments.add(cartFragment);
                    break;
                case POIONTFOUR://
                    myFragment = new MyFragment();
                    if (Position != POIONTFOUR) {
                        myFragment.setUserVisibleHint(false);
                    }
                    fragments.add(myFragment);
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

    /**
     * 状态栏改变
     *
     * @param isBlack
     * @param bgColor R.color.mine
     */
    public void changeStatusBar(boolean isBlack, int bgColor) {
        mImmersionBar.statusBarDarkFont(isBlack).statusBarColor(bgColor).init();
    }

    @OnTouch({R.id.lin_1, R.id.lin_2, R.id.lin_3, R.id.lin_4})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.lin_1:
                Position = POIONTONE;
                break;
            case R.id.lin_2:
                //todo 判断是否已经登录
//                if (!MySp.iSLoginLive(mContext)) {
//                    jumpActivityNotFinish(mContext, LoginActivity.class);
//                    return false;
//                }
                Position = POIONTTWO;
                break;
            case R.id.lin_3:
                //todo 判断是否已经登录
//                if (!MySp.iSLoginLive(mContext)) {
//                    jumpActivityNotFinish(mContext, LoginActivity.class);
//                    return false;
//                }
                Position = POIONTTHREE;
                break;
            case R.id.lin_4:
//                if (!MySp.iSLoginLive(mContext)) {
//                    jumpActivityNotFinish(mContext, LoginActivity.class);
//                    return false;
//                }
                Position = POIONTFOUR;
                break;
            default:
                break;
        }
        changeStatusBar(true, R.color.transparent);
        setSelectedLin(Position);
        myPager.setCurrentItem(Position, false);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPager.setCurrentItem(Position, false);
        setSelectedLin(Position);
    }

    public void setSelectedLin() {
        myPager.setCurrentItem(Position, false);
        setSelectedLin(Position);
    }

    /**
     * 选择
     *
     * @param position
     */
    public void setSelectedLin(int position) {
        lin1.setSelected(false);
        lin2.setSelected(false);
        lin3.setSelected(false);
        lin4.setSelected(false);
        //设置状态栏黑色字体与图标
//        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mImmersionBar.statusBarDarkFont(true);
        switch (position) {
            case 0:
                lin1.setSelected(true);
                break;
            case 1:
                lin2.setSelected(true);
                break;
            case 2:
                lin3.setSelected(true);
                break;
            case 3:
                lin4.setSelected(true);
//                mImmersionBar.statusBarDarkFont(false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                if (!isBack) {
                    showNormalToast(R.string.main_exit);
                    downTime = event.getDownTime();
                    isBack = true;
                    return true;
                } else {
                    if (event.getDownTime() - downTime <= 2000) {
                        ActivityStack.getInstance().removeAll();
                        Process.killProcess(Process.myPid());
                    } else {
                        showNormalToast(R.string.main_exit);
                        downTime = event.getDownTime();
                        return true;
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
