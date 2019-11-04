package com.zhifeng.wineculture.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.wineculture.R;
import com.zhifeng.wineculture.actions.MyAction;
import com.zhifeng.wineculture.modules.AccountDto;
import com.zhifeng.wineculture.modules.UserInfoDto;
import com.zhifeng.wineculture.ui.MainActivity;
import com.zhifeng.wineculture.ui.impl.MyView;
import com.zhifeng.wineculture.ui.loginandregister.LoginActivity;
import com.zhifeng.wineculture.utils.base.UserBaseFragment;
import com.zhifeng.wineculture.utils.data.MySp;
import com.zhifeng.wineculture.utils.dialog.PicturesDialog;
import com.zhifeng.wineculture.utils.imageloader.GlideImageLoader;
import com.zhifeng.wineculture.utils.photo.PicUtils;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

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
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_TAKE = 102;
    public static final int REQUEST_CODE_ALBUM = 103;
    public static int REQUEST_SELECT_TYPE = -1;//选择的类型
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    ArrayList<ImageItem> images = null;
    private int maxImgCount = 1;               //允许选择图片最大数


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

        initImagePicker();
    }

    /**
     * 修改头像
     *
     * @param path
     */
    @Override
    public void updataAvatar(String path) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.updataAvatar(path);
        }
    }

    /**
     * 修改头像成功
     *
     * @param url
     */
    @Override
    public void updataAvatarSuccess(String url) {
        loadDiss();
        GlideUtil.setImageCircle(mContext, url, iv, R.drawable.icon_avatar);
        MySp.setAvatar(mContext,url);
        images = new ArrayList<>();
        selImageList = new ArrayList<>();
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
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getUserInfo();
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoDto userInfoDto) {
        UserInfoDto.DataBean dataBean = userInfoDto.getData();
        GlideUtil.setImageCircle(mContext, dataBean.getAvatar(), iv, R.drawable.icon_avatar);
        tvName.setText(dataBean.getRealname());
        tvId.setText(ResUtil.getFormatString(R.string.my_id,String.valueOf(dataBean.getId())));
        MySp.setMobile(mContext, dataBean.getMobile());
        MySp.setName(mContext,dataBean.getRealname());
        MySp.setAvatar(mContext,dataBean.getAvatar());
        String mobile = dataBean.getMobile();
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tvMobile.setText(mobile);
        if (MySp.iSLoginLive(mContext)) {
            String json = MySp.getUserList(mContext);
            L.e("lgh_user", "userJson  = " + json);
            List<AccountDto> list = new ArrayList<>();
            if (!TextUtils.isEmpty(json)) {
                list = new Gson().fromJson(json, new TypeToken<List<AccountDto>>() {
                }.getType());
            }
            AccountDto userDto = new AccountDto();
            userDto.setToken(MySp.getAccessToken(mContext));
            userDto.setAvatar(dataBean.getAvatar());
            userDto.setRealName(dataBean.getRealname());
            userDto.setMobile(dataBean.getMobile());
            L.e("lgh_user", "token  = " + MySp.getAccessToken(mContext));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMobile().equals(userDto.getMobile())) {
                    list.set(i, userDto);
                    L.e("lgh_user", "user  = " + userDto.getToken());
                    MySp.setUserList(mContext, new Gson().toJson(list));
                    return;
                }
            }
            if (list.size() >= 3) {
                list.set(0, userDto);
            } else {
                list.add(userDto);
            }
            MySp.setUserList(mContext, new Gson().toJson(list));
        }
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
            R.id.tv_myComments, R.id.tv_myAddress, R.id.tv_aboutWineCulture, R.id.iv_booking_goods, R.id.iv, R.id.iv_setting})
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
                jumpActivityNotFinish(mContext, MyCommentsListActivity.class);
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
                //todo 我要预约领商品
                jumpActivityNotFinish(mContext, BookingGoodsActivity.class);
                break;
            case R.id.iv_setting:
                //todo 安全中心
                jumpActivityNotFinish(mContext, SecurityActivity.class);
                break;
            case R.id.iv:
                //todo 修改头像
                showSelectDiaLog();
                break;
        }
        if (position != -1) {
            //todo 我的订单
            Intent i = new Intent(mContext, MyOrderActivity.class);
            i.putExtra("position", position);
            startActivity(i);
        }
    }


    /**
     * 选择图片
     */
    public void showSelectDiaLog() {
        PicturesDialog dialog = new PicturesDialog(mActivity, R.style.MY_AlertDialog);
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
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 打开相册
     */
    private void takeUserGally() {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent1 = new Intent(mContext, ImageGridActivity.class);
        /* 如果需要进入选择的时候显示已经选中的图片，
         * 详情请查看ImagePickerActivity
         * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);                         //保存文件的高度。单位像素
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    //todo 请求接口
                    updataAvatar("data:image/gif;base64," + PicUtils.imageToBase64(selImageList.get(0).path));
//                    updataAvatar("data:image/gif;base64,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");
                }
            }
        }
//        else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
//            //预览图片返回
//            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
//                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
//                if (images != null) {
//                    selImageList.clear();
//                    selImageList.addAll(images);
//                    adapter.setImages(selImageList);
//                }
//            }
//        }
    }


    /**********************************选择图片 end*********************************************/
}
