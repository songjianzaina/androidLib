package com.insworks.imageoption.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_photo
 * @ClassName: BitmapUtil
 * @Author: Song Jian
 * @CreateDate: 2019/6/3 15:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/3 15:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: bitmap工具类
 */
public class BitmapUtil {
    /**
     * view转bitmap 第一种方式
     *
     * @param view
     * @param bitmapWidth
     * @param bitmapHeight
     * @return
     */
    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    /**
     * view转bitmap 第二种方式
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }
}
