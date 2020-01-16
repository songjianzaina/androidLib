package com.insworks.imageoption;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_log.LogUtil;
import com.insworks.imageoption.utils.BitmapUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.OnImagePickerResultListener;

import java.util.ArrayList;


public class PhotoTestActivity extends Activity {

    protected ImageView oldPic;
    protected ImageView newPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_photo_activity_main);
        oldPic = (ImageView) findViewById(R.id.old_pic);
        newPic = (ImageView) findViewById(R.id.new_pic);

    }

    public void compress(View view) {

        //显示压缩图片
        newPic.setImageBitmap(EasyPhoto.init(this).scaleCompress(BitmapUtil.convertViewToBitmap(oldPic)));
    }


    public void save(View view) {
//        AndPermission.with(this)
//                .permission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE})
//                .onGranted(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        //保存图片到本地
//                        EasyPhoto.init(PhotoTestActivity.this).savePicToGallery(BitmapUtil.convertViewToBitmap(oldPic), new MediaScannerConnection.MediaScannerConnectionClient() {
//                            @Override
//                            public void onMediaScannerConnected() {
//                                LogUtil.d("连接扫描");
//                            }
//
//                            @Override
//                            public void onScanCompleted(String path, Uri uri) {
//                                LogUtil.d("扫描完成");
//
//                            }
//                        });
//                    }
//                })
//                .start();



    }

    public void openGallery(View view) {

        EasyPhoto.init(PhotoTestActivity.this).imagePicker().setCircleCrop(140).setOnResultListener(new OnImagePickerResultListener() {
            @Override
            public void onResult(ArrayList<ImageItem> images, ImagePicker imagePicker) {
                LogUtil.d("图片回调 一共选择了"+images.size()+"张图片");
                if (images!=null&&images.size()>0) {
                    imagePicker.getImageLoader().displayImage(PhotoTestActivity.this, images.get(0).path, newPic, 80, 100);
                }
            }
        }).start();
    }

}
