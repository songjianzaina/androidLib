package com.insworks.imageoption.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.OnImagePickerResultListener;
import com.lzy.imagepicker.view.CropImageView;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.imageoption.imagepicker
 * @ClassName: ImagePickerOption
 * @Author: Song Jian
 * @CreateDate: 2019/6/11 11:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/11 11:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class ImagePickerOption {

    protected final ImagePicker imagePicker;
    private Activity activity;
    private OnImagePickerResultListener resultListener;
    private boolean isDirectCamera=false;

    public ImagePickerOption(Activity activity) {
        this.activity = activity;
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        //默认单选模式
        imagePicker.setMultiMode(false);
        //默认开启相机
        imagePicker.setShowCamera(true);
        //默认关闭裁剪模式
        imagePicker.setCrop(false);
        //默认关闭矩形区域保存
        imagePicker.setSaveRectangle(false);
        //设置图片保存宽高  该值若偏小 图片模糊  默认800x800
        imagePicker.setOutPutX(800);
        imagePicker.setOutPutY(800);
    }


    /**
     * 是否按照矩形区域保存图片  该选项若开启 那么圆形裁剪会冲突
     *
     * @param
     */
    public ImagePickerOption setSaveRectangle(boolean isOpen) {
        imagePicker.setSaveRectangle(isOpen);
        return this;
    }

    /**
     * 是否开启相机功能
     *
     * @param
     */
    public ImagePickerOption setShowCamera(boolean isShow) {
        imagePicker.setShowCamera(isShow);
        return this;
    }

    /**
     * 设置图片多选
     *
     * @param selectLimit
     */
    public ImagePickerOption setMultiMode(int selectLimit) {
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(selectLimit);
        return this;
    }

    /**
     * 设置矩形裁剪
     *
     * @param width
     * @param height
     */
    public ImagePickerOption setRectangleCrop(int width, int height) {
        //开启裁剪模式
        imagePicker.setCrop(true);
        //设置裁剪样式
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, activity.getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, activity.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        return this;
    }

    /**
     * 设置圆形裁剪
     *
     * @param radius
     */
    public ImagePickerOption setCircleCrop(int radius) {
        //开启裁剪模式
        imagePicker.setCrop(true);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        //圆角弧度
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, activity.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        return this;
    }

    /**
     * 设置图片保存宽高
     * @param x
     * @param y
     */
    public ImagePickerOption setOutputXY(int x, int y) {
        //设置图片保存宽高  该值若偏小 图片模糊  默认800x800
        imagePicker.setOutPutX(x);
        imagePicker.setOutPutY(y);
        return this;
    }
    /**
     * 设置是否直接打开相机  默认打开图册
     *
     * @param
     */
    public ImagePickerOption setDirectOpenCamera(boolean isDirect) {
        isDirectCamera = isDirect;
        return this;
    }

    /**
     * 进入图片选择界面
     */
    public void start() {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        ImageGridActivity.setOnResultListener(resultListener);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, isDirectCamera); // 是否是直接打开相机
        activity.startActivity(intent);
    }

    /**
     * 结果回调
     * @param resultListener
     */
    public ImagePickerOption setOnResultListener(OnImagePickerResultListener resultListener) {

        this.resultListener = resultListener;
        return this;
    }


}
