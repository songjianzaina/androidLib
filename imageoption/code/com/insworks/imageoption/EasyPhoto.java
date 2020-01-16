package com.insworks.imageoption;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;

import com.insworks.imageoption.compress.EasyCompress;
import com.insworks.imageoption.imagepicker.ImagePickerOption;
import com.insworks.imageoption.utils.ImageUtils;

import java.io.File;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.module_copy
 * @ClassName: EasyPhoto
 * @Author: Song Jian
 * @CreateDate: 2019/6/3 14:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/3 14:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 图片操作 入口
 */
public class EasyPhoto {


    private static EasyPhoto easyPhoto;
    private static Activity activity;
    protected ImagePickerOption imagePickerOption;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyPhoto.init() 初始化！");
    }

    /**
     * 初始化
     *
     * @param activity
     */
    public static EasyPhoto init(Activity activity) {
        EasyPhoto.activity = activity;
        if (easyPhoto == null) {
            synchronized (EasyPhoto.class) {
                if (easyPhoto == null) {
                    easyPhoto = new EasyPhoto();
                }
            }
        }
        return easyPhoto;
    }

    public EasyCompress scaleCompress(File file) {
        testInitialize();
        return new EasyCompress(activity, file);
    }

    public EasyCompress scaleCompress(String filePath) {
        testInitialize();
        return new EasyCompress(activity, new File(filePath));
    }

    public File compressToFile(String filePath) {
        return compressToFile(new File(filePath));
    }

    public File compressToFile(File file) {
        testInitialize();
        return new EasyCompress(activity, file).toFile();
    }

    public Bitmap scaleCompress(Bitmap bitmap,int maxWidth,int maxHeight) {
        testInitialize();
        return ImageUtils.scaleCompress(maxWidth ,maxHeight, bitmap);
    }

    /**
     * 默认尺寸缩小1倍
     * @param bitmap
     * @return
     */
    public Bitmap scaleCompress(Bitmap bitmap) {
        testInitialize();
        return ImageUtils.scaleCompress(bitmap.getWidth()/2 ,bitmap.getHeight()/2, bitmap);
    }

    public Bitmap scaleCompress(byte[] bytes) {
        testInitialize();
        return scaleCompress(BitmapFactory.decodeByteArray(bytes,0,bytes.length),960,540);
    }


    public byte[] scaleCompressToBytes(byte[] bytes) {
        testInitialize();
        return ImageUtils.scaleCompressToBytes(960,540,BitmapFactory.decodeByteArray(bytes,0,bytes.length));
    }


    public Bitmap matrixCompress(byte[] bytes) {
        testInitialize();
        return ImageUtils.matrixCompress(bytes);
    }


    public Bitmap sampleCompress(Bitmap bitmap, int reqWidth, int reqHeight) {
        testInitialize();
        return ImageUtils.sampleCompress(bitmap,reqWidth,reqHeight);
    }


    public Bitmap sampleCompress(byte[] bytes, int reqWidth, int reqHeight) {
        testInitialize();
        return ImageUtils.sampleCompress(bytes,reqWidth,reqHeight);
    }


    public Bitmap matrixCompress(Bitmap bitmap) {
        testInitialize();
        return ImageUtils.matrixCompress(bitmap);
    }

    public Bitmap compress(Bitmap bitmap) {
        testInitialize();
        return ImageUtils.compress(bitmap);
    }

    public byte[] compressToBytes(Bitmap bitmap) {
        testInitialize();
        return ImageUtils.compressToBytes(bitmap);
    }

    public Bitmap compressToBitmap(byte[] bytes) {
        testInitialize();
        return ImageUtils.compressToBitmap(bytes);
    }

    public byte[] compressToBytes(byte[] bytes) {
        testInitialize();
        return ImageUtils.compressToBytes(bytes);
    }

    public Bitmap scaleCompress(int imageRes) {
        testInitialize();
        return ImageUtils.bmpCompress(activity.getResources(), 720, 960, imageRes);
    }

    public void savePicToGallery(File picFile, MediaScannerConnection.OnScanCompletedListener callback) {
        testInitialize();
        //解码file
        Bitmap bitmap = BitmapFactory.decodeFile(picFile.getAbsolutePath());
        //插入图库
        String filename = System.currentTimeMillis() + ".png";
        // 把文件插入到系统图库  使用 inserImage() 方法，不需要我们指定路径，会自动将图片保存至 Picture 目录下
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, filename, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //扫描图库
        MediaScannerConnection.scanFile(activity, arrayOf(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()), arrayOf("image/jpeg"), callback);
    }

    /**
     * 保存图片到本地图库
     *
     * @param
     * @param callback
     */
    public void savePicToGallery(byte[] bytes, MediaScannerConnection.OnScanCompletedListener callback) {
        testInitialize();
        savePicToGallery(BitmapFactory.decodeByteArray(bytes, 0, bytes.length), callback);
    }

    /**
     * 保存图片到本地图库
     *
     * @param bitmap
     * @param callback
     */
    public void savePicToGallery(Bitmap bitmap, MediaScannerConnection.OnScanCompletedListener callback) {
        testInitialize();
        String filename = System.currentTimeMillis() + ".png";
        // 把文件插入到系统图库  使用 inserImage() 方法，不需要我们指定路径，会自动将图片保存至 Picture 目录下
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, filename, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //扫描图库
        MediaScannerConnection.scanFile(activity, arrayOf(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()), arrayOf("image/jpeg"), callback);
    }

    /**
     * 获取相册图片或者相机照片
     *
     * @param
     * @param
     */
    public ImagePickerOption imagePicker() {
        testInitialize();
        if (imagePickerOption == null) {
            imagePickerOption = new ImagePickerOption(activity);
        }
        //默认不直接打开相机
        imagePickerOption.setDirectOpenCamera(false);
        return imagePickerOption;
    }

//    /**
//     * 获取相册图片或者相机照片
//     *
//     * @param
//     * @param
//     */
//    public ImagePickerOption openPreview() {
//        testInitialize();
//        //打开预览
//        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
//        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
//        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
//    }

    private String[] arrayOf(String child) {
        String[] arrStr = {child};
        return arrStr;
    }


}
