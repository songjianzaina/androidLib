package com.insworks.imageoption.utils;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    /**
     * 质量压缩
     *
     * @param format  图片格式 jpeg,png,webp
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    public static void compress(String fromfilePathName, String tofilePathName,
                                Bitmap.CompressFormat format, int quality) {
        // File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(fromfilePathName);
        Bitmap originBitmap = BitmapFactory.decodeFile(originFile
                .getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBitmap.compress(format, quality, bos);
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File(tofilePathName));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     */
    @SuppressLint("DefaultLocale")
    public static boolean saveBitmap(String fromFilePathName,
                                     String toFilePathName) {
        Bitmap resultBitmap = BitmapFactory.decodeFile(fromFilePathName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CompressFormat compressFormat = null;

        // 判断原图片的格式进行压缩
        if (!TextUtils.isEmpty(fromFilePathName)) {
            if (fromFilePathName.toLowerCase().endsWith(".jpg")
                    || fromFilePathName.toLowerCase().endsWith(".jpeg")) {
                compressFormat = CompressFormat.JPEG;
            } else if (fromFilePathName.toLowerCase().endsWith(".png")) {
                compressFormat = CompressFormat.PNG;
            } else {
                compressFormat = CompressFormat.JPEG;
            }
        }

        boolean res = resultBitmap.compress(compressFormat, 100, bos);// 保存压缩后图片
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File(toFilePathName));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 保存bitmp到指定路径
     */
    @SuppressLint("DefaultLocale")
    public static boolean saveBitmap(Bitmap resultBitmap,
                                     String dir, String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CompressFormat compressFormat = null;
        compressFormat = CompressFormat.JPEG;

        boolean res = resultBitmap.compress(compressFormat, 100, bos);// 保存压缩后图片
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File(dir, filename));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @SuppressLint("NewApi")
    public static Bitmap bmpCompress(int maxWidht, int maxtHeight,
                                     String fromFilePathName) {

        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(fromFilePathName, options);
        int height = options.outHeight;
        int width = options.outWidth;

        float widRat = 0, heiRat = 0;
        if (maxWidht > 0) {
            widRat = width / maxWidht;
        }
        if (maxtHeight > 0) {
            heiRat = height / maxtHeight;
        }
        int ratio = (int) Math.max(widRat, heiRat); // 原图的最小边长
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = ratio; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(fromFilePathName, options); // 解码文件
        Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth()
                + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

    @SuppressLint("NewApi")
    public static Bitmap bmpCompress(Resources res, int maxWidht,
                                     int maxtHeight, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        int height = bitmap.getWidth();
        int width = bitmap.getHeight();

        float widRat = 0, heiRat = 0;
        if (maxWidht > 0) {
            widRat = width / maxWidht;
        }
        if (maxtHeight > 0) {
            heiRat = height / maxtHeight;
        }
        int ratio = (int) Math.max(widRat, heiRat); // 原图的最小边长
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = ratio; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeResource(res, resId, options);
        Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth()
                + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

    /**
     * 将图片压缩到最大高度的长宽比
     */
    public static Bitmap scaleCompress(Resources res, int maxWidht,
                                       int maxtHeight, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        return scaleCompress(maxWidht,
                maxtHeight, bitmap);
    }

    /**
     * 将图片压缩到最大高度的长宽比
     */
    public static Bitmap scaleCompress(int maxWidht,
                                       int maxtHeight, Bitmap bitmap) {
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();

        float widRat = 0, heiRat = 0;
        if (maxWidht > 0) {
            widRat = width / maxWidht;
        }
        if (maxtHeight > 0) {
            heiRat = height / maxtHeight;
        }
        float ratio = Math.max(widRat, heiRat); // 原图的最小边长
        // 设置缩放比
        Bitmap result = Bitmap.createBitmap((int) (width / ratio),
                (int) (height / ratio), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        RectF rectF = new RectF(0, 0, result.getWidth(), result.getHeight());
        // // 将原图画在缩放之后的矩形上
        canvas.drawBitmap(bitmap, null, rectF, null);
        return result;
    }
    /**
     * 将图片压缩到最大高度的长宽比
     */
    public static byte[] scaleCompressToBytes(int maxWidht,
                                       int maxtHeight, Bitmap bitmap) {
        Bitmap bit = scaleCompress(maxWidht, maxtHeight, bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    /**
     * 图片压缩后转回BitMap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compress(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        byte[] bytes = baos.toByteArray();

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }

    /**
     * 图片装压缩后转字节数据
     *
     * @param bitmap
     * @return
     */
    public static byte[] compressToBytes(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(CompressFormat.PNG, 80, baos);

        return baos.toByteArray();


    }

    /**
     * 图片字节数据压缩后转字节数据
     *
     * @param
     * @return
     */
    public static byte[] compressToBytes(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return compressToBytes(bitmap);


    }

    /**
     * 图片字节数据压缩后转Bitmap
     *
     * @param
     * @return
     */
    public static Bitmap compressToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return compress(bitmap);


    }

    /**
     * 采通过Matrix压缩图片 矩阵压缩
     *
     * @param
     * @return
     */
    public static Bitmap matrixCompress(Bitmap bitmap) {
        Matrix matrix = new Matrix();

        matrix.setScale(0.5f, 0.5f);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


    }

    /**
     * 采通过Matrix压缩图片 矩阵压缩
     *
     * @param
     * @return
     */
    public static Bitmap matrixCompress(byte[] bytes) {
        Matrix matrix = new Matrix();

        matrix.setScale(0.5f, 0.5f);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


    }

    /**
     * 采样压缩
     *
     * @param
     * @return
     */
    public static Bitmap sampleCompress(Bitmap bitmap, int reqWidth, int reqHeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return sampleCompress(data, reqWidth, reqHeight);

    }

    /**
     * 采样压缩图片
     *
     * @param bytes
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap sampleCompress(byte[] bytes, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,

                reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,

                                            int reqWidth, int reqHeight) {

        final int picheight = options.outHeight;

        final int picwidth = options.outWidth;

        int targetheight = picheight;

        int targetwidth = picwidth;

        int inSampleSize = 1;

        if (targetheight > reqHeight || targetwidth > reqWidth) {

            while (targetheight >= reqHeight

                    && targetwidth >= reqWidth) {

                inSampleSize += 1;

                targetheight = picheight / inSampleSize;

                targetwidth = picwidth / inSampleSize;

            }

        }

        return inSampleSize;

    }

}
