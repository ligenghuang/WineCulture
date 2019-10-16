package com.zhifeng.wineculture.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideApp;
import com.lgh.huanglib.util.cusview.richtxtview.ImageLoader;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.GoodsDetailAction;
import com.zhifeng.wineculture.adapters.BannerGoods;
import com.zhifeng.wineculture.modules.GoodsDetailDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.cart.CartActivity;
import com.zhifeng.wineculture.ui.impl.GoodsDetailView;
import com.zhifeng.wineculture.ui.my.AddressListActivity;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.listener.AppBarStateChangeListener;
import com.zhifeng.wineculture.utils.sku.BaseSkuModel;
import com.zhifeng.wineculture.utils.sku.ItemClickListener;
import com.zhifeng.wineculture.utils.sku.ProductModel;
import com.zhifeng.wineculture.utils.sku.Sku;
import com.zhifeng.wineculture.utils.sku.UiData;
import com.zhifeng.wineculture.utils.sku.adapter.SkuAdapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

public class GoodsDetailActivity extends UserBaseActivity<GoodsDetailAction> implements GoodsDetailView {

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
    int inventory;//库存
    String goodsId;
    GoodsDetailDto.DataBean dataBean;
    String goodsName = "";
    String service;
    int sku_id = -1;
    int cart_number = 1;
    boolean isCollection = false;

    /**
     * 轮播图所需参数
     */
    BannerGoods banner;

    List<String> imgs = new ArrayList<>();
    List<String> tips = new ArrayList<>();
    List<String> url = new ArrayList<>();

    private int comment_count;

    private ProductModel testData;
    UiData mUiData;

    boolean isBuy = false;
    int IsBuy = 2;

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
    protected GoodsDetailAction initAction() {
        return new GoodsDetailAction(this, this);
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

        goodsId = String.valueOf(getIntent().getIntExtra("goods_id", 0));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        //轮播图
        banner = new BannerGoods();
        bannerMain.setAdapter(banner);

//        goodsDetailCommentListAdapter = new GoodsDetailCommentListAdapter(mContext);
//        rvComment.setLayoutManager(new LinearLayoutManager(mContext));
//        rvComment.setAdapter(goodsDetailCommentListAdapter);

        loadDialog();
        getGoodsDetail();
        loadView();

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

    /**
     * 获取商品详情
     */
    @Override
    public void getGoodsDetail() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getGoodsDetail(goodsId);
        }
    }

    /**
     * 获取商品详情成功
     *
     * @param goodsDetailDto
     */
    @Override
    public void getGoodsDetailSucces(GoodsDetailDto goodsDetailDto) {
        loadDiss();
        addFootPrint();
        dataBean = goodsDetailDto.getData();
        tvGoodsOriginalPrice.setText("￥" + dataBean.getOriginal_price());//原价
        tvGoodsOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tvGoodsPrice.setText("￥" + dataBean.getPrice());//价格
        tvGoodsName.setText(dataBean.getGoods_name());//商品名称
        goodsName = dataBean.getGoods_name();
        fTitleTv.setText(goodsName);
        setBanner(dataBean.getImg());
        tvGoodsSales.setText(ResUtil.getFormatString(R.string.goods_detail_tab_15, dataBean.getNumber_sales() + ""));//销量
        tvGoodsStock.setText(ResUtil.getFormatString(R.string.goods_detail_tab_16, dataBean.getStock() + ""));//库存
        double freight = Double.parseDouble(dataBean.getShipping_price());
        tvGoodsFreight.setText(freight == 0 ? ResUtil.getString(R.string.goods_detail_tab_7) : "￥" + dataBean.getShipping_price());//运费
        isCollection = dataBean.getCollection() == 1;
        tvGoodsAttention.setText(ResUtil.getString(isCollection ? R.string.goods_detail_tab_17 : R.string.goods_detail_tab_4));
        tvGoodsAddress.setText(dataBean.getAddress());
        comment_count = dataBean.getComment_count();
        tvGoodsCommentCount.setText(ResUtil.getFormatString(R.string.goods_detail_tab_12, comment_count + ""));//评价数量
//        goodsDetailCommentListAdapter.refresh(dataBean.getCommentlist());//评价列表
        content = dataBean.getContent();//图片详情
        content_param = dataBean.getContent_param();//产品参数
        setSelectedLin(Position);
        //todo 初始化规格
        initUiData(dataBean);
//        service = dataBean.getService();
    }

    @Override
    public void addFootPrint() {
        if(CheckNetwork.checkNetwork2(mContext)){
            baseAction.addFootPrint(goodsId);
        }
    }

    /**
     * 取消关注或关注
     */
    @Override
    public void deleteOrAddCollection() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.deleteOrAddCollection(goodsId + "");
        }
    }

    /**
     * 取消关注或关注成功
     */
    @Override
    public void deleteOrAddCollection(String msg) {
        loadDiss();
        showNormalToast(msg);
        isCollection = !isCollection;
        tvGoodsAttention.setText(ResUtil.getString(isCollection ? R.string.goods_detail_tab_17 : R.string.goods_detail_tab_4));
    }

    /**
     * 加入购物车
     *
     * @param sku_id
     * @param cart_number
     */
    @Override
    public void addCart(int sku_id, int cart_number) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.addCart(sku_id, cart_number);
        }
    }


    /**
     * 立即购买
     *
     * @param sku_id
     * @param cart_number
     */
    @Override
    public void buyNow(int sku_id, int cart_number) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.buyNow(sku_id, cart_number);
        }
    }

    /**
     * 立即购买成功  跳转至下单页面
     *
     * @param cartId
     */
    @Override
    public void buyNowSuccess(int cartId) {
        if (mUiData.getBottomSheetDialog() != null) {
            mUiData.getBottomSheetDialog().dismiss();
        }
        loadDiss();
        L.e("lgh_goods","isBuy  = "+isBuy);
        if (isBuy){
            //todo 2019 10 10 订单页面
            startActivity(TemporaryActivity.class, "cartId", String.valueOf(cartId));
        }else {
            showNormalToast(ResUtil.getString(R.string.goods_detail_tab_33));
        }

    }

    /**
     * 失败
     *
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        showNormalToast(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

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

    /**
     * 初始化规格
     *
     * @param dataBean
     */
    private void initUiData(GoodsDetailDto.DataBean dataBean) {
        mUiData = new UiData();
        GoodsDetailDto.DataBean.SpecBean specBean = dataBean.getSpec();
        List<GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean> goodsSkuBeans = specBean.getGoods_sku();
        List<GoodsDetailDto.DataBean.SpecBean.SpecAttrBean> specAttrBeans = specBean.getSpec_attr();
        // 设置组合数据"1;2726;95"=“型号；颜色；尺寸”
        testData = new ProductModel();

        for (int i = 0; i < specAttrBeans.size(); i++) {
            GoodsDetailDto.DataBean.SpecBean.SpecAttrBean specAttrBean = specAttrBeans.get(i);
            ProductModel.AttributesEntity group01 = new ProductModel.AttributesEntity();
            group01.setName(specAttrBean.getSpec_name());
            for (int j = 0; j < goodsSkuBeans.size(); j++) {
                GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean goodsSkuBean = goodsSkuBeans.get(j);
                String str = goodsSkuBean.getSku_attr().substring(2, goodsSkuBean.getSku_attr().lastIndexOf("\""));
                L.d("lgh_sku", "sku = " + str);
                L.d("lgh_sku", "specAttrBean.getSpec_id() = " + specAttrBean.getSpec_id());
                if (str.equals(specAttrBean.getSpec_id() + "")) {
                    for (int k = 0; k < specAttrBean.getRes().size(); k++) {
                        GoodsDetailDto.DataBean.SpecBean.SpecAttrBean.ResBean resBean = specAttrBean.getRes().get(k);
                        L.d("lgh_sku", "resBean.getAttr_id() = " + resBean.getAttr_id());
                        L.d("lgh_sku", "goodsSkuBean.getSku_attr1() = " + goodsSkuBean.getSku_attr1());
                        if (String.valueOf(resBean.getAttr_id()).equals(goodsSkuBean.getSku_attr1())) {
                            L.d("lgh_sku", "sku = " + str);
                            testData.getProductStocks().put(goodsSkuBean.getSku_attr1(), new BaseSkuModel(Double.parseDouble(goodsSkuBean.getPrice()), Double.parseDouble(goodsSkuBean.getGroupon_price()),
                                    goodsSkuBean.getInventory(), goodsSkuBean.getVirtual_sales(),
                                    goodsSkuBean.getSku_id(), resBean.getAttr_name()));
                            group01.getAttributeMembers().add(j, new ProductModel.AttributesEntity.AttributeMembersEntity(1, goodsSkuBean.getSku_id(), resBean.getAttr_name(), goodsSkuBean.getPrice(), goodsSkuBean.getSku_attr1()));
                        }
                    }
                }
            }
            testData.getAttributes().add(0, group01);//第一组
        }

    }

    /**
     * @param productModel
     * @param IsBuy        判断 0加入购物车、1立即购买 、2查看规格
     */
    private void showBottomSheetDialog(ProductModel productModel) {

        if (mUiData.getBottomSheetDialog() == null) {
            mUiData.getSelectedEntities().clear();
            mUiData.getAdapters().clear();
            View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
            ImageView ivClose = view.findViewById(R.id.iv_close);
            TextView subtract = view.findViewById(R.id.tv_item_goods_subtract);
            TextView add = view.findViewById(R.id.tv_item_goods_add);
            TextView tvGoodsNum = view.findViewById(R.id.et_item_goods_num);
            TextView save = view.findViewById(R.id.save);
            LinearLayout llList = (LinearLayout) view.findViewById(R.id.ll_list);//列表
            tvGoodsNum.setText(cart_number + "");
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //关闭
                    mUiData.getBottomSheetDialog().dismiss();
                }
            });

            /**
             * 减
             */
            subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inventory == -1) {
                        showNormalToast(ResUtil.getString(R.string.goods_detail_tab_28));
                    } else {
                        if (cart_number == 1) {
                            cart_number = 1;
                            tvGoodsNum.setText(cart_number + "");
                            showNormalToast(ResUtil.getString(R.string.cart_tab_7));
                        } else {
                            cart_number = cart_number - 1;
                            tvGoodsNum.setText(cart_number + "");
                        }
                        L.e("lgh_cart", "subtract = " + tvGoodsNum);
                    }
                }
            });

            /**
             * 加
             */
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inventory == -1) {
                        showNormalToast(ResUtil.getString(R.string.goods_detail_tab_28));
                    } else {
                        if (cart_number >= inventory) {
                            showNormalToast(ResUtil.getString(R.string.cart_tab_8));
                        } else {
                            cart_number = cart_number + 1;
                            tvGoodsNum.setText(cart_number + "");
                        }
                    }
                    L.e("lgh_cart", "add = " + cart_number);
                }
            });
            /***********************************商品数量 end*****************************************/
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sku_id == -1) {
                        showNormalToast(ResUtil.getString(R.string.goods_detail_tab_31));
                    } else {
                        L.e("lgh_buy","IsBuy   =   "+IsBuy);
                        switch (IsBuy) {
                            case 0:
                                //加入购物车
                                addCart(sku_id, cart_number);
                                break;
                            case 1:
                                //立即购买
                                buyNow(sku_id, cart_number);
                                break;
                            case 2:
                                //选择规格
                                mUiData.getBottomSheetDialog().dismiss();
                                break;
                        }
                    }
                }
            });
            //添加list组
            for (int i = 0; i < testData.getAttributes().size(); i++) {
                View viewList = getLayoutInflater().inflate(R.layout.bottom_sheet_group, null);
                TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
                RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
                SkuAdapter skuAdapter = new SkuAdapter(testData.getAttributes().get(i).getAttributeMembers());
                mUiData.getAdapters().add(skuAdapter);
                int item = 3;//设置列数
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, item);
                recyclerViewBottom.setLayoutManager(gridLayoutManager);
                recyclerViewBottom.setAdapter(skuAdapter);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
                llList.addView(viewList, params);

                tvTitle.setText(testData.getAttributes().get(i).getName());
            }
            // SKU 计算
            mUiData.setResult(Sku.skuCollection(testData.getProductStocks()));
            L.d("lgh_sku", "mUiData.setResult  = " + mUiData.getResult().toString());
            for (String key : mUiData.getResult().keySet()) {
                L.d("lgh_sku", "key = " + key + " value = " + mUiData.getResult().get(key));
            }
            //设置点击监听器
            for (SkuAdapter adapter : mUiData.getAdapters()) {
                ItemClickListener listener = new ItemClickListener(mUiData, adapter, handler);
                adapter.setOnClickListener(listener);
            }
            // 初始化按钮
            for (int i = 0; i < mUiData.getAdapters().size(); i++) {
                for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                    if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null
                            || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0
                    ) {
                        entity.setStatus(0);
                    }
                }
            }
            //设置价格
            mUiData.setBottomSheetDialog(new BottomSheetDialog(this));
            mUiData.getBottomSheetDialog().setContentView(view);
            View parent = (View) view.getParent();//获取ParentView
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            view.measure(0, 0);
            behavior.setPeekHeight(view.getMeasuredHeight());
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
            params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            parent.setLayoutParams(params);
            mUiData.getBottomSheetDialog().show();
        } else {
            mUiData.getBottomSheetDialog().show();
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
//                    tv_selected.setText("选择的sku组合："+msg.obj.toString());
                    //原价
                    tvGoodsOriginalPrice.setText("￥" + msg.obj.toString());
                    break;
                case 2:
//                    tv_stock.setText("组合的库存："+msg.obj.toString());
                    L.d("lgh_sku", "inventory  = " + msg.obj.toString());
                    inventory = Integer.parseInt(msg.obj.toString());
                    tvGoodsStock.setText(ResUtil.getFormatString(R.string.goods_detail_tab_16, msg.obj.toString() + ""));//库存
                    break;
                case 3:
//                    tv_price.setText("按钮的价格："+msg.obj.toString());
                    L.d("lgh_sku", "price  = " + msg.obj.toString());
                    tvGoodsPrice.setText("￥" + msg.obj.toString());
                    break;
                case 4:
                    sku_id = Integer.parseInt(msg.obj.toString());
                    break;
                case 5:
                    L.d("lgh_sku", "tvGoodsSpec  = " + msg.obj.toString());
                    tvGoodsSpec.setText(ResUtil.getFormatString(R.string.goods_detail_tab_26, msg.obj.toString()));
                    break;
                case 6:
                    //todo 销量
                    tvGoodsSales.setText(ResUtil.getFormatString(R.string.goods_detail_tab_15, msg.obj.toString() + ""));//销量
                    break;
                case 7:
                    tvGoodsOriginalPrice.setText("￥" + dataBean.getOriginal_price());//原价
                    tvGoodsPrice.setText("￥" + dataBean.getPrice());//价格
                    inventory = -1;
                    tvGoodsSales.setText(ResUtil.getFormatString(R.string.goods_detail_tab_15, dataBean.getNumber_sales() + ""));//销量
                    tvGoodsStock.setText(ResUtil.getFormatString(R.string.goods_detail_tab_16, dataBean.getStock() + ""));//库存
                    break;
            }
        }
    };

    /**
     * 设置图片轮播
     *
     * @param banners
     */
    private void setBanner(List<GoodsDetailDto.DataBean.ImgBean> banners) {
        //设置轮播图
        if (banners.size() != 0) {
            bannerMain.setVisibility(View.VISIBLE);
            imgs = new ArrayList<>();
            tips = new ArrayList<>();
            url = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                GoodsDetailDto.DataBean.ImgBean bannersBean = banners.get(i);
                imgs.add(bannersBean.getPicture());
                tips.add("");
                url.add(bannersBean.getPicture());
            }
            bannerMain.setAutoPlayAble(true);
            bannerMain.setData(imgs, tips);
            bannerMain.startAutoPlay();
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

    @OnClick({R.id.f_right_iv, R.id.tv_goods_service, R.id.tv_goods_cart, R.id.tv_goods_buy, R.id.iv_to_up_top
            , R.id.tv_goods_attention, R.id.tv_goods_spec, R.id.tv_goods_comment_all, R.id.tv_goods_add_cart,R.id.tv_goods_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_address:
                //todo 配送地址
                Intent intent = new Intent(mContext, AddressListActivity.class);
                intent.putExtra("isGoods", true);
                startActivityForResult(intent, 200);
                break;
            case R.id.f_right_iv:
                //todo 导航栏右边菜单
                new XPopup.Builder(mActicity)
                        .hasShadowBg(false)
                        .atView(fRightIv)
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
                                            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class, GoodsDetailActivity.class);
                                            finish();
                                        } else {
                                            jumpActivityNotFinish(mContext, CartActivity.class);
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.tv_goods_attention:
                //todo 取消关注或关注
                deleteOrAddCollection();
                break;
            case R.id.tv_goods_service:
                //todo 客服
                break;
            case R.id.tv_goods_spec:
                //todo 商品规格
                isBuy = false;
                IsBuy = 2;
                showBottomSheetDialog(testData);
                break;
            case R.id.tv_goods_comment_all:
                Intent i = new Intent(mContext, GoodsCommentsActivity.class);
                i.putExtra("goodsId", goodsId);
                i.putExtra("comment_count", String.valueOf(comment_count));
                startActivity(i);
                break;
            case R.id.tv_goods_cart:
                //todo 购物车
                jumpActivityNotFinish(mContext,CartActivity.class);
                break;
            case R.id.tv_goods_buy:
                //todo 立即购买
                isBuy = true;
                IsBuy = 1;
                showBottomSheetDialog(testData);
                break;
            case R.id.tv_goods_add_cart:
                //todo 加入购物车
                isBuy = false;
                IsBuy = 0;
                showBottomSheetDialog(testData);
                break;
            case R.id.iv_to_up_top:
                //todo 回到顶部
                scrollToTop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (data != null) {
                //配送地址
                String address = data.getStringExtra("address");
                tvGoodsAddress.setText(address);
            }
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
