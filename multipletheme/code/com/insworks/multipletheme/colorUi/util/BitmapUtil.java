package com.insworks.multipletheme.colorUi.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tanzhenxing
 * Date: 2017/11/9 下午8:15
 * Desc
 */
public class BitmapUtil {
    public static void saveBitmap(Bitmap bitmap, String bitName) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path + File.separator + bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
