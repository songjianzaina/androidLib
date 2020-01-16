package com.insworks.imageoption.compress;

import android.content.Context;
import android.graphics.Bitmap;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.module_copy
 * @ClassName: EasyCompress
 * @Author: Song Jian
 * @CreateDate: 2019/6/3 14:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/3 14:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 图片压缩类
 */
public class EasyCompress {

    protected File newFile;
    protected CompressHelper.Builder builder;
    private Context context;
    private File oldFile;

    public EasyCompress(Context context, File oldFile) {
        this.context = context;
        this.oldFile = oldFile;
    }

    /**
     * 压缩成文件
     *
     * @return
     */
    public File toFile() {
        if (builder != null && oldFile != null) {
            return builder.build().compressToFile(oldFile);
        } else {

            return CompressHelper.getDefault(context).compressToFile(oldFile);
        }
    }

    /**
     * 压缩成bitmap
     *
     * @return
     */
    public Bitmap toBitmap() {
        if (builder != null && oldFile != null) {
            return builder.build().compressToBitmap(oldFile);
        } else {

            return CompressHelper.getDefault(context).compressToBitmap(oldFile);
        }
    }


    /**
     * 设置图片最大宽度
     *
     * @param maxWidth
     * @return
     */
    public EasyCompress setMaxWidth(int maxWidth) {
        initBuilder();
        builder.setMaxWidth(maxWidth);
        return this;
    }


    /**
     * 设置图片最大高度
     *
     * @param maxHeight
     * @return
     */
    public EasyCompress setMaxHeight(int maxHeight) {
        initBuilder();
        builder.setMaxWidth(maxHeight);
        return this;
    }


    /**
     * 设置压缩后图片存放位置
     *
     * @param picPath
     * @return
     */
    public EasyCompress setDestinationDirectoryPath(String picPath) {
        initBuilder();
        builder.setDestinationDirectoryPath(picPath);
        return this;
    }


    /**
     * 设置图片压缩质量
     *
     * @param quality
     * @return
     */
    public EasyCompress setQuality(int quality) {
        initBuilder();
        builder.setQuality(quality);
        return this;
    }


    /**
     * 设置压缩后图片名称
     *
     * @param
     * @return
     */
    public EasyCompress setNewFileName(String fileName) {
        initBuilder();
        builder.setFileName(fileName);
        return this;
    }


    /**
     * 设置图片压缩格式
     *
     * @param
     * @return
     */
    public EasyCompress setCompressFormat(Bitmap.CompressFormat compressFormat) {
        initBuilder();
        builder.setCompressFormat(compressFormat);
        return this;
    }

    /**
     * 初始化配置builder
     */
    private void initBuilder() {
        if (builder == null) {
            new CompressHelper.Builder(context);
        }
    }

}
