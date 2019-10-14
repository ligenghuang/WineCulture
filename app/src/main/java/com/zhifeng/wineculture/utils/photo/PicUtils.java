package com.zhifeng.wineculture.utils.photo;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.util.L;
import com.zhifeng.wineculture.utils.Constanst;
import com.zhifeng.wineculture.utils.photo.utilFixSevent.PhotoFitSevent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.zhifeng.wineculture.utils.photo.BitmapUtil.PHOTO_DIR2;

public class PicUtils {
    public static final String TAG = "PicUtils";

    /**
     * 按比例压缩图片
     *
     * @param sourceFile 源文件
     * @param targetFile 存放文件
     * @param scale      缩放的比例
     * @throws FileNotFoundException
     */
    public static void compressPic(File sourceFile, File targetFile, int scale) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(sourceFile.getPath(), options);
        boolean isCompress = bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
        // Log.v(TAG,"compressPic_isCompress:"+isCompress);
    }

    public static int getPicHeight(String fileDirAndName) {
        BitmapFactory.Options options = getPicInfo(fileDirAndName);
        return options.outHeight;
    }

    public static int getPicWidth(String fileDirAndName) {
        BitmapFactory.Options options = getPicInfo(fileDirAndName);
        return options.outWidth;
    }

    /**
     * 最关键在此，把options.inJustDecodeBounds = true;
     * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
     */
    public static BitmapFactory.Options getPicInfo(String fileDirAndName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 此时返回的bitmap为null
        Bitmap bitmap = BitmapFactory.decodeFile(fileDirAndName, options);
        return options;
    }

    /**
     * 取出裁剪的图
     *
     * @throws FileNotFoundException scale是控制大中小（总共就分为4等分，原图就不压缩，大是4分之三，中是2分之一，小是4分之一）
     */
    public static boolean showCutPhoto(Intent data, int scale, String targetFilePath)
            throws FileNotFoundException {
        boolean isSuccess = false;
        if (data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap cutPhoto = extras.getParcelable("data");
                if (cutPhoto != null) {
                    compress50(cutPhoto);

                    FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = scale;

                    isSuccess = cutPhoto.compress(CompressFormat.JPEG, 100, fileOutputStream);// (0
                    // -
                    // 100)压缩文件
                } else {
                    isSuccess = false;
                }
            }
        }
        return isSuccess;

    }

    public static String getCompressedImgPath(String sourceImgPath) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(sourceImgPath, opts);
            opts.inJustDecodeBounds = false;

            int w = opts.outWidth;
            int h = opts.outHeight;
            float standardW = 800f;//分辨率
            float standardH = 480f;

            int zoomRatio = 1;
            if (w > h && w > standardW) {
                zoomRatio = (int) (w / standardW);
            } else if (w < h && h > standardH) {
                zoomRatio = (int) (h / standardH);
            }
            if (zoomRatio <= 0)
                zoomRatio = 1;
            opts.inSampleSize = zoomRatio;

            bmp = BitmapFactory.decodeFile(sourceImgPath, opts);

            File compressedImg = new File(sourceImgPath);
            L.e("lgh", compressedImg.getPath());
            FileOutputStream fos = new FileOutputStream(compressedImg);
            bmp.compress(CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            L.e("lgh", compressedImg.getPath());
            return compressedImg.getPath();

        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    public static ByteArrayOutputStream compress50(Bitmap cutPhoto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cutPhoto.compress(CompressFormat.JPEG, 100, baos);
        // Log.v(TAG,"_baos.toByteArray().length:"+baos.toByteArray().length);
        while (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            Log.v("compress50", "_baos.toByteArray().length:" + baos.toByteArray().length);

            baos.reset();// 重置baos即清空baos
            cutPhoto.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
            // Log.v(TAG,"_baos.toByteArray().length:"+baos.toByteArray().length);
        }
        return baos;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            if (!PHOTO_DIR2.exists()) {
                PHOTO_DIR2.mkdirs();
            }
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
//            System.out.println("复制单个文件操作出错");
            L.d("lgh_pic", "复制单个文件操作出错");
            L.d("lgh_pic", e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * 裁剪方法
     */
    public static void showPicToCutPhoto(Uri uri, Activity activity) {

        try {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // System.out.println("activityName:"+activity.getClass());
            int aspectY = (int) (PixelConvertUtil.getScreenHeight(activity) / PixelConvertUtil
                    .getScreenWidth(activity));
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", aspectY);
            // 这里裁剪图片宽高不能乘积不能大于255的平方
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);

            activity.startActivityForResult(intent, PhotoFitSevent.SELECT_CUT_PICK_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪方法
     */
    public static void showPicToCutPhoto2(File sourceFile, Activity activity) {
        try {


            //authority 就是 内容提供者的包名  注意----注意
            Uri uri = FileProvider.getUriForFile(activity, Constanst.PROVIDER, sourceFile);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            // System.out.println("activityName:"+activity.getClass());
            int aspectY = (int) (PixelConvertUtil.getScreenHeight(activity) / PixelConvertUtil
                    .getScreenWidth(activity));
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", aspectY);
            // 这里裁剪图片宽高不能乘积不能大于255的平方
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            //开启临时权限
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //重点:针对7.0以上的操作
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("return-data", false);
            intent.putExtra("noFaceDetection", false);

            intent.putExtra("outputFormat", CompressFormat.JPEG.toString());

            activity.startActivityForResult(intent, PhotoFitSevent.SELECT_CUT_PICK_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ByteArrayOutputStream doCompressByHeightAndWidth(String sourceFilePath, float targetHeight,
                                                                   float targetWidth) throws Exception {
        int zoomSacle = getZoomSacleByHeightAndWidth(sourceFilePath, targetHeight, targetWidth);

        // long beginTime = //System.currentTimeMillis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = getBitmapByZoomSacle(sourceFilePath, zoomSacle);
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        // long endTime = //System.currentTimeMillis();
        // //System.out.println(endTime-beginTime);
        // if (baos!=null) {
        // //System.out.println(baos.toByteArray().length/1024);
        // }
        return baos;
    }

    public static int getZoomSacleByHeightAndWidth(String sourceFilePath, float targetHeight,
                                                   float targetWidth) {
        int originalHeight = PicUtils.getPicHeight(sourceFilePath);
        int originalWidth = PicUtils.getPicWidth(sourceFilePath);
        float scaleWidth = originalWidth / (float) targetWidth;
        float scaleHeight = originalHeight / (float) targetHeight;
        int zoomSacle = 0;
        if (scaleWidth > scaleHeight) {
            zoomSacle = (int) scaleHeight;
        } else {
            zoomSacle = (int) scaleWidth;
        }
        if (zoomSacle < 0 || zoomSacle == 0) {
            zoomSacle = 1;
        }
        return zoomSacle;
    }

    /***
     * 指定缩放倍数
     *
     * @param sourceFilePath
     * @param zoomSacle
     * @return
     * @throws Exception
     */
    public static Bitmap getBitmapByZoomSacle(String sourceFilePath, int zoomSacle) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = zoomSacle;
        return BitmapFactory.decodeStream(new FileInputStream(sourceFilePath), null, options);
    }

    /*
     * 得到图片字节流 数组大小
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将转换后的图片输出到指定文件夹
     *
     * @param sourceFilePath
     * @param
     * @param zoomSacle
     * @return
     * @throws Exception
     */
    public static boolean doCompressByZoomSacle(String sourceFilePath, String targetFilePath, int zoomSacle)
            throws Exception {
        FileOutputStream fot = new FileOutputStream(targetFilePath);
        Bitmap bitmap = getBitmapByZoomSacle(sourceFilePath, zoomSacle);
        boolean b = bitmap.compress(CompressFormat.JPEG, 100, fot);
        bitmap.recycle();
        fot.close();
        return b;
    }


    ///////////////////////////////////////////////////////////////////////

    /**
     * 裁剪方法
     */
    public static void showPicToCutPhotoAuto(File sourceFile, Activity activity) {

        try {


            Uri uri = FileProvider.getUriForFile(activity, Constanst.PROVIDER, sourceFile);

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            // System.out.println("activityName:"+activity.getClass());
            int aspectY = (int) (PixelConvertUtil.getScreenHeight(activity) / PixelConvertUtil
                    .getScreenWidth(activity));
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", aspectY);
            // 这里裁剪图片宽高不能乘积不能大于255的平方
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);
            intent.putExtra("outputX", 350);
            intent.putExtra("outputY", 50);
            intent.putExtra("return-data", true);


            activity.startActivityForResult(intent, BitmapUtil.PIC_FROM_CUTPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("getCutPhoto----cc");
    }

    /**
     * 裁剪方法
     */
    public static void showPicToCutPhotoAutoTwo(Uri uri, Activity activity) {

        try {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // System.out.println("activityName:"+activity.getClass());
            int aspectY = (int) (PixelConvertUtil.getScreenHeight(activity) / PixelConvertUtil
                    .getScreenWidth(activity));
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", aspectY);
            intent.putExtra("scale", true);
            // 这里裁剪图片宽高不能乘积不能大于255的平方
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 200);
            intent.putExtra("outputX", 350);
            intent.putExtra("outputY", 50);
            intent.putExtra("return-data", true);

            activity.startActivityForResult(intent, BitmapUtil.PIC_FROM_CUTPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 480);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //把bitmap转换成String
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样21910
        bm.compress(CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        Log.d("lgh_b", "压缩后的大小=" + b.length);
        L.e("lgh_b", "b  = " + Base64.encodeToString(b, Base64.NO_WRAP));
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }


    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        L.e("lgh_b", "b  = " + result);
        return result;
    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap=BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            L.d("lgh_pic",e.toString());
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Bitmap bmp, String picName, Context mContext) {;
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");
            L.d("lgh_pic",file.getPath());
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            L.d("lgh_pic",e.getMessage());
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新

        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        ToastUtils.getToast().cancel();
        ToastUtils.show("图片保存成功");

    }


}
