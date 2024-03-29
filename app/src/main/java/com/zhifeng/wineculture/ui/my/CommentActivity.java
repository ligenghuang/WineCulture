package com.zhifeng.wineculture.ui.my;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.CommentAction;
import com.zhifeng.wineculture.adapters.EvaluteAdapter;
import com.zhifeng.wineculture.modules.CommentDto;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.MyCommentListDto;
import com.zhifeng.wineculture.modules.OrderCommentListDto;
import com.zhifeng.wineculture.ui.impl.CommentView;
import com.zhifeng.wineculture.utils.base.UserBaseActivity;
import com.zhifeng.wineculture.utils.photo.PicUtils;
import com.zhifeng.wineculture.utils.photo.utilFixSevent.PhotoFitSevent;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentActivity extends UserBaseActivity<CommentAction> implements CommentView {
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.f_right_tv)
    TextView fRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String order_id;
    EvaluteAdapter evaluteAdapter;
    private int positions;
    MyCommentListDto.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
        getOrderCommentList();
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
        order_id = getIntent().getStringExtra("order_id");
        evaluteAdapter = new EvaluteAdapter();
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(evaluteAdapter);
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        evaluteAdapter.setStatusClickListener(new EvaluteAdapter.OnStatusClickListener() {
            @Override
            public void pictureOrder(OrderCommentListDto.DataBean person, int position) {
                positions = position;
                showSelectDiaLog();
            }
        });
    }

    /**
     * 图片选择
     */
    private void showSelectDiaLog() {
        final Dialog dialog = new Dialog(this, R.style.MY_AlertDialog);
        dialog.setCanceledOnTouchOutside(true);
        View inflate = LayoutInflater.from(this).inflate(R.layout.userinfo_dialog_head, null);
        TextView cancel = (TextView) inflate.findViewById(R.id.btn_cancel);
        TextView camera_tv = ((TextView) inflate.findViewById(R.id.camera_tv));
        TextView photo_tv = ((TextView) inflate.findViewById(R.id.photo_tv));
        camera_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoNoCompress();
                dialog.dismiss();
            }
        });
        photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picPhoto();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.y = 20;
            dialogWindow.setAttributes(lp);
            dialog.show();
        }

    }

    /**
     * --------------------拍照-------------
     **/

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;
    private String mCurrentPhotoPath;
    private String fileName;

    /**
     * 拍照准备（需要判断是否有权限）
     */
    public void takePhotoNoCompress() {
        boolean b = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;//判断是否有写的权限
        if (b) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},//获取照片权限
                    REQ_PERMISSION_CODE_TAKE_PHOTO);
        } else {
            mCurrentPhotoPath = PhotoFitSevent.takePhotoNoCompress(this);
        }
    }

    /**
     * 从相册获取图片
     */
    public void picPhoto() {
        boolean b = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;//判断是否有写的权限
        if (b) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},//获取照片权限
                    REQUEST_CODE_TAKE_PHOTO);
        } else {

            PhotoFitSevent.takePhoto(this);
        }
    }

    /**
     * 权限获取结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE_TAKE_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ;
                    mCurrentPhotoPath = PhotoFitSevent.takePhotoNoCompress(this);//获取权限成功后再起请求拍照功能
                } else {
                    // Permission Denied
                    Toast.makeText(CommentActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_TAKE_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoFitSevent.takePhoto(this);//获取权限成功后再起请求拍照功能
                } else {
                    // Permission Denied
                    Toast.makeText(CommentActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    /**
     * 获取路径
     *
     * @param s
     */
    private void getPhotoDir(String s) {
        if (s == null) {
            return;
        }
        fileName = s;//截取最后一段图片
        File file = new File(s);
        L.e("lgh", " 之前" + file.length() + "  " + s);
        if (file.length() / 1024 > 512) {
            fileName = PicUtils.getCompressedImgPath(s);
            L.e("lgh", " 现在 " + fileName);
        }
        List<OrderCommentListDto.DataBean> dataBeans = evaluteAdapter.getData();
        OrderCommentListDto.DataBean dataBean = dataBeans.get(positions);
        List<String> imgStrs = dataBean.getImgStrs();
        imgStrs.add(fileName);
        dataBean.setImgStrs(imgStrs);
        evaluteAdapter.replaceData(dataBeans);
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

    @Override
    public void getOrderCommentList() {
        baseAction.getOrderCommentList(order_id);
    }

    @Override
    public void getOrderCommentListSuccess(OrderCommentListDto orderCommentListDto) {
        List<OrderCommentListDto.DataBean> beans = orderCommentListDto.getData();
        evaluteAdapter.addData(beans);
    }

    @Override
    public void postComment() {
        List<CommentDto> list = new ArrayList<>();
        List<OrderCommentListDto.DataBean> dataBeans = evaluteAdapter.getData();
        for (int i = 0; i < dataBeans.size(); i++) {
            CommentDto commentDto = new CommentDto();
            OrderCommentListDto.DataBean dataBean = dataBeans.get(i);
            commentDto.setContent(dataBean.getNote());
            commentDto.setGoods_id(dataBean.getGoods_id() + "");
            commentDto.setOrder_id(order_id + "");
            commentDto.setSku_id(dataBean.getSku_id() + "");
            List<String> str = new ArrayList<>();
            for (int j = 0; j < dataBean.getImgStrs().size(); j++) {
                str.add("data:image/gif;base64," + PicUtils.imageToBase64(dataBean.getImgStrs().get(j)));
            }
            commentDto.setImg(str);
            list.add(commentDto);
        }
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.postComment(list);
        }
    }

    @Override
    public void postCommentSuccess(GeneralDto generalDto) {
        showNormalToast(generalDto.getMsg());
        finish();
    }

    @Override
    public void postCommentFail(String msg, int code) {
        loadDiss();
        showNormalToast(msg);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PhotoFitSevent.REQUEST_CODE_TAKE_PHOTO) {
//            getPhotoDir(mCurrentPhotoPath);
//        }
        L.e("fileName", "----------------------->" + fileName + "data" + data);
        L.e("resultCode" + resultCode + "  requestCode " + requestCode);
        switch (requestCode) {
            case PhotoFitSevent.REQUEST_CODE_TAKE_PHOTO:
                getPhotoDir(mCurrentPhotoPath);
                break;
            case PhotoFitSevent.SELECT_PIC_BY_PICK_PHOTO:
                if (data == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    //4.4 及以上系统使用这个方法处理图片
                    getPhotoDir(PhotoFitSevent.handleImageOnKitKat(data, this));
                    return;
                }
                //4.4 及以下系统使用这个方法处理图片
                getPhotoDir(PhotoFitSevent.handleImageBeforeKitKat(data, this));
                break;
        }
    }

    @OnClick(R.id.f_right_tv)
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.f_right_tv:
                postComment();
                break;
        }
    }
}
