//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.imageoption.utils;

import android.annotation.TargetApi;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build.VERSION;

public class StorageUtils {
    public StorageUtils() {
    }

    public static String getInternalStorageDirectory() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    public static long getInternalStorageAvailableSize() {
        return getStorageAvailableSize(getInternalStorageDirectory());
    }

    public static long getInternalStorageTotalSize() {
        return getStorageTotalSize(getInternalStorageDirectory());
    }

    public static int getInternalStorageUsedPercent() {
        long total = getInternalStorageTotalSize();
        long free = getInternalStorageAvailableSize();
        return (int)((total - free) * 100L / total);
    }

    public static int getInternalStorageFreedPercent() {
        long total = getInternalStorageTotalSize();
        long free = getInternalStorageAvailableSize();
        return (int)(free * 100L / total);
    }

    public static int getExternalStorageUsedPercent() {
        if (externalStorageAvailable()) {
            long total = getExternalStorageTotalSize();
            long free = getExternalStorageAvailableSize();
            return (int)((total - free) * 100L / total);
        } else {
            return 0;
        }
    }

    public static int getExternalStorageFreedPercent() {
        if (externalStorageAvailable()) {
            long total = getExternalStorageTotalSize();
            long free = getExternalStorageAvailableSize();
            return (int)(free * 100L / total);
        } else {
            return 0;
        }
    }

    public static boolean externalStorageAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static long getExternalStorageAvailableSize() {
        try {
            if (externalStorageAvailable()) {
                return getStorageAvailableSize(getExternalStorageDirectory());
            }
        } catch (Exception var1) {
        }

        return -1L;
    }

    public static long getExternalStorageTotalSize() {
        try {
            if (externalStorageAvailable()) {
                long temp = getStorageTotalSize(getExternalStorageDirectory());
                return temp == 0L ? -1L : temp;
            }
        } catch (Exception var2) {
        }

        return -1L;
    }

    @TargetApi(18)
    private static long getStorageTotalSize(String path) {
        StatFs stat = new StatFs(path);
        long totalBytes;
        if (VERSION.SDK_INT < 18) {
            totalBytes = (long)stat.getBlockSize() * (long)stat.getBlockCount();
        } else {
            totalBytes = stat.getTotalBytes();
        }

        return totalBytes;
    }

    @TargetApi(18)
    private static long getStorageAvailableSize(String path) {
        StatFs stat = new StatFs(path);
        long availableBytes;
        if (VERSION.SDK_INT < 18) {
            availableBytes = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        } else {
            availableBytes = stat.getAvailableBytes();
        }

        return availableBytes;
    }
}
