package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CommentAction;
import com.zhifeng.wineculture.adapters.ImagePickerAdapter;
import com.zhifeng.wineculture.modules.CommentDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.ui.impl.CommentView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.dialog.PicturesDialog;
import com.zhifeng.wineculture.utils.imageloader.GlideImageLoader;
import com.zhifeng.wineculture.utils.photo.PicUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentActivity extends UserBaseActivity<CommentAction> implements CommentView, ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_TAKE = 102;
    public static final int REQUEST_CODE_ALBUM = 103;
    public static int REQUEST_SELECT_TYPE = -1;//选择的类型
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.f_right_tv)
    TextView fRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivGoods)
    ImageView ivGoods;
    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String order_id;
    private String goods_id;
    private String sku_id;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private final int maxImgCount = 9;               //允许选择图片最大数
    private ImagePickerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected CommentAction initAction() {
        return new CommentAction(this, this);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
        order_id = String.valueOf(getIntent().getIntExtra("order_id", 0));
        goods_id = getIntent().getStringExtra("goods_id");
        sku_id = getIntent().getStringExtra("sku_id");
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        initImagePicker();
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
                .addTag("CommentActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(R.string.my_mycomments);
        fRightTv.setText(R.string.mycomment_publish);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);                         //保存文件的高度。单位像素
    }

    @Override
    public void postComment() {
        String star_rating = "1";
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showNormalToast(R.string.mycomment_commentContent);
            return;
        }
        List<String> str = new ArrayList<>();
        if (selImageList.size() > 0) {
            for (int i = 0; i < selImageList.size(); i++) {
                str.add("data:image/gif;base64," + PicUtils.imageToBase64(selImageList.get(i).path));
            }
        }
        CommentDto orderComment = new CommentDto(order_id, goods_id, sku_id, content, str);
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.postComment(orderComment.toString());
        }
    }

    @Override
    public void postCommentSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getMsg());
        finish();
    }

    @Override
    public void onError(String message, int code) {
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

    private void takePhoto() {
        // 直接调起相机
        /**
         * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
         *
         * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
         *
         * 如果实在有所需要，请直接下载源码引用。
         */
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 打开相册
     */
    private void takeUserGally() {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
        Intent intent1 = new Intent(mContext, ImageGridActivity.class);
        /* 如果需要进入选择的时候显示已经选中的图片，
         * 详情请查看ImagePickerActivity
         * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    /**
     * 选择图片
     */
    public void showSelectDiaLog() {
        PicturesDialog dialog = new PicturesDialog(this, R.style.MY_AlertDialog);
        dialog.setOnClickListener(new PicturesDialog.OnClickListener() {
            @Override
            public void onCamera() {
                takePhoto();
            }

            @Override
            public void onPhoto() {
                takeUserGally();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == IMAGE_ITEM_ADD) {//添加图片
            showSelectDiaLog();
        } else {//打开预览
            Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    @OnClick(R.id.f_title_tv)
    public void onClick(View view) {
        postComment();
    }
}
