package com.zhifeng.wineculture.ui.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideApp;
import com.lgh.huanglib.util.cusview.richtxtview.ImageLoader;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.BaseAction;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.cart.CartActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.listener.AppBarStateChangeListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @ClassName: 商品详情页
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/29 15:39
 * @Version: 1.0
 */

public class GoodsDetailActivity extends UserBaseActivity {

    @BindView(R.id.banner_main)
    BGABanner bannerMain;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.f_right_iv)
    ImageView fRightIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_original_price)
    TextView tvGoodsOriginalPrice;
    @BindView(R.id.tv_goods_attention)
    TextView tvGoodsAttention;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_sales)
    TextView tvGoodsSales;
    @BindView(R.id.tv_goods_stock)
    TextView tvGoodsStock;
    @BindView(R.id.tv_goods_address)
    TextView tvGoodsAddress;
    @BindView(R.id.tv_goods_freight)
    TextView tvGoodsFreight;
    @BindView(R.id.tv_goods_spec)
    TextView tvGoodsSpec;
    @BindView(R.id.tv_goods_comment_count)
    TextView tvGoodsCommentCount;
    @BindView(R.id.tv_goods_comment_all)
    TextView tvGoodsCommentAll;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.tv_type_1)
    TextView tvType1;
    @BindView(R.id.tv_type_2)
    TextView tvType2;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tv_goods_service)
    TextView tvGoodsService;
    @BindView(R.id.tv_goods_cart)
    TextView tvGoodsCart;
    @BindView(R.id.tv_goods_buy)
    TextView tvGoodsBuy;
    @BindView(R.id.iv_to_up_top)
    ImageView ivToUpTop;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;

    String content;//图片详情
    String content_param;//产品参数

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<Activity>(this));
        binding();
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImmersionBar
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("GoodsDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());

    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    @Override
    protected void loadView() {
        super.loadView();
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    fTitleTv.setVisibility(View.INVISIBLE);
                    ivToUpTop.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    fTitleTv.setVisibility(View.VISIBLE);
                    ivToUpTop.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    fTitleTv.setVisibility(View.INVISIBLE);
                    ivToUpTop.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        baseAction.toRegister();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        baseAction.toUnregister();
//    }

    @OnTouch({R.id.tv_type_1, R.id.tv_type_2})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.tv_type_1:
                Position = POIONTONE;
                break;
            case R.id.tv_type_2:
                Position = POIONTTWO;
                break;
            default:
                break;
        }
        setSelectedLin(Position);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    private void setSelectedLin(int position) {
        tvType1.setSelected(false);
        tvType2.setSelected(false);
        switch (position) {
            case 0:
                tvType1.setSelected(true);
                setXRichText(content);
                break;
            case 1:
                tvType2.setSelected(true);
                setXRichText(content_param);
                break;
            default:
                break;
        }
    }

    private void setXRichText(String text) {
        try {
            xrichtext
                    .callback(new XRichText.BaseClickCallback() {
                        @Override
                        public boolean onLinkClick(String url) {
                            return true;
                        }

                        @Override
                        public void onImageClick(List<String> urlList, int position) {
                            super.onImageClick(urlList, position);
                            //todo 图片点击事件
                        }

                        @Override
                        public void onFix(XRichText.ImageHolder holder) {
                            super.onFix(holder);
                            //仅仅是文本的话不会进这边 holder.getPosition()
//                                choseRlLoadingMusic.setVisibility(View.VISIBLE);
                            L.e("XRichText", "w " + holder.getWidth() + " h " + holder.getHeight());
                            holder.setStyle(XRichText.Style.CENTER);
                            L.e("XRichText2", "w " + holder.getWidth() + " h " + holder.getHeight());
                            //设置宽高
                        }

                    })
                    .imageDownloader(new ImageLoader() {
                        @Override
                        public Bitmap getBitmap(String url) throws IOException {
                            L.e("lgh", "url  = " + url);
                            try {
                                Bitmap myBitmap = GlideApp.with(mContext)
                                        .asBitmap() //必须
                                        .load(url)
                                        .placeholder(R.drawable.icon_good_detail)
                                        .error(R.drawable.icon_good_detail)
                                        .submit()
                                        .get();
                                int screen_width = getWindowManager().getDefaultDisplay().getWidth();
                                int image_width = myBitmap.getWidth();
                                int image_height = myBitmap.getHeight();
                                int widget_height = screen_width * image_height / image_width;
                                myBitmap = Bitmap.createScaledBitmap(myBitmap, screen_width, widget_height, false);
//
                                return myBitmap;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return BitmapFactory.decodeResource(getResources(), R.drawable.icon_good_detail);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                return BitmapFactory.decodeResource(getResources(), R.drawable.icon_good_detail);
                            }
                        }
                    })
                    .text(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.f_right_iv, R.id.tv_goods_service, R.id.tv_goods_cart, R.id.tv_goods_buy, R.id.iv_to_up_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.f_right_iv:
                //todo 导航栏右边菜单
                new XPopup.Builder(mActicity)
                        .hasShadowBg(false)
                        .atView(fRightIv)
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
                                            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class, GoodsDetailActivity.class);
                                            finish();
                                        } else {
                                            jumpActivityNotFinish(mContext, CartActivity.class);
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.tv_goods_service:
                //todo 客服
                break;
            case R.id.tv_goods_cart:
                //todo 购物车
                break;
            case R.id.tv_goods_buy:
                //todo 立即购买
                break;
            case R.id.iv_to_up_top:
                //todo 回到顶部
                scrollToTop();
                break;
        }
    }

    /**
     * 滚动到顶部
     */
    public void scrollToTop() {
        //拿到 appbar 的 behavior,让 appbar 滚动
        ViewGroup.LayoutParams layoutParams = appBar.getLayoutParams();
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            //拿到下方tabs的y坐标，即为我要的偏移量
            float y = coordinatorLayout.getY();
            appBarLayoutBehavior.setTopAndBottomOffset(0);
            appBar.setExpanded(true);
            nestedScrollView.scrollTo(0, (int) y);
        }
    }
}
