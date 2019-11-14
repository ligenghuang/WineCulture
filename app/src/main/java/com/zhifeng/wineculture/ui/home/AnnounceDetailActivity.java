package com.zhifeng.wineculture.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideApp;
import com.lgh.huanglib.util.cusview.richtxtview.ImageLoader;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AnnounceDetailAction;
import com.zhifeng.wineculture.modules.AnnounceDetailDto;
import com.zhifeng.wineculture.ui.impl.AnnounceDetailView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

/**
 * @ClassName: 公告详情
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/11/14 16:43
 * @Version: 1.0
 */

public class AnnounceDetailActivity extends UserBaseActivity<AnnounceDetailAction> implements AnnounceDetailView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;

    int id;

    @Override
    public int intiLayout() {
        return R.layout.activity_announce_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected AnnounceDetailAction initAction() {
        return new AnnounceDetailAction(this, this);
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
                .addTag("AnnounceDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());

    }

    @Override
    protected void init() {
        super.init();
        mContext = this;
        mActicity = this;

        id = getIntent().getIntExtra("id", 0);

        getAnnounceDetail();
    }

    /**
     * 获取公告详情
     */
    @Override
    public void getAnnounceDetail() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAnnounce(id + "");
        }
    }

    /**
     * 获取公告详情成功
     * @param detailDto
     */
    @Override
    public void getAnnounceDetailSuccess(AnnounceDetailDto detailDto) {
        loadDiss();
        AnnounceDetailDto.DataBean dataBean = detailDto.getData();
        fTitleTv.setText(dataBean.getTitle());
        setXRichText(dataBean.getDesc());
    }

    /**
     * 失败
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

    /**
     * 设置富文本数据
     * @param text
     */
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
}
