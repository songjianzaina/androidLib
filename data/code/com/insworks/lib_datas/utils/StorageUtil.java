package com.insworks.lib_datas.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.Closeable;
import java.io.File;

/**
 * 存储工具类
 */
public class StorageUtil {

    /**
     * 1MB
     */
    public static final int IO_BUFFER_SIZE = 1024 * 1024;

    private static boolean deleteDirectory(File fileDir) {
        if (fileDir == null || !fileDir.exists()) {
            return false;
        }
        boolean success = false;
        try {
            if (fileDir.isDirectory()) {
                String[] files = fileDir.list();
                for (String child : files) {
                    success = deleteDirectory(new File(fileDir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            success = fileDir.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    private static boolean deleteFile(File file) {
        try {
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }

    private static void cleanExternalStorage() {
        File storageDir = Environment.getExternalStorageDirectory();
        File[] files = storageDir.listFiles();
        if (files == null || files.length <= 0) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                deleteFile(file);
            } else if (file.isDirectory()) {
                deleteDirectory(file);
            }
        }
    }

    public static long getStorageTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long totalBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = stat.getBlockCountLong();
        } else {
            totalBlocks = stat.getBlockCount();
        }
        return blockSize * totalBlocks;
    }

    public static long getStorageAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }


}
