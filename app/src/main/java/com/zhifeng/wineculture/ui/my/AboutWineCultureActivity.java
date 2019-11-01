package com.zhifeng.wineculture.ui.my;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideApp;
import com.lgh.huanglib.util.cusview.richtxtview.ImageLoader;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.AboutWineCultureAction;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.ui.impl.AboutWineCultureView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

/**
 * @ClassName:
 * @Description: 关于酒文化
 * @Author: Administrator
 * @Date: 2019/9/28 18:05
 */
public class AboutWineCultureActivity extends UserBaseActivity<AboutWineCultureAction> implements AboutWineCultureView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_about_wine_culture;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        getAbout();
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
                .addTag("AboutWineCultureActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_aboutwineculture);
    }

    @Override
    protected AboutWineCultureAction initAction() {
        return new AboutWineCultureAction(this, this);
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

    /**
     * 关于酒文化
     */
    @Override
    public void getAbout() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAbout();
        }
    }

    /**
     * 关于酒文化成功
     *
     * @param generalDto
     */
    @Override
    public void getAboutSuccess(GeneralDto generalDto) {
        loadDiss();
        setXRichText(generalDto.getData());
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
}
