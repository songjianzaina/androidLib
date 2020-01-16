package com.insworks.imageoption.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.imageoption.utils
 * @ClassName: PathManager
 * @Author: Song Jian
 * @CreateDate: 2019/6/3 17:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/3 17:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 文件路径快速获取工具类
 */
public class PathManager {



    /**
     * 获取默认data目录下的NoBackupFiles目录
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getDataNoBackupFilesDir(Context context) {
        //  /data/data/包名/no_backup
        return context.getNoBackupFilesDir().getAbsolutePath();
    }


    /**
     * 获取默认data目录下的files目录
     *
     * @param context
     * @return
     */
    public static String getDataFilesDir(Context context) {
        //  /data/data/包名/files
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取默认data目录
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDataDir(Context context) {
        //  /data/data/包名
        return context.getDataDir().getAbsolutePath();
    }

    /**
     * 获取data目录下的codecache目录
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getDataCodeCacheDir(Context context) {
        //  /data/data/包名/code_cache
        return context.getCodeCacheDir().getAbsolutePath();
    }

    /**
     * 获取data目录下的缓存目录 用户清除缓存会将该目录下数据清除
     *
     * @param context
     * @return
     */
    public static String getDataCacheDir(Context context) {
        //  /data/data/包名/cache
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取指定data目录 用户清除数据会将该目录下数据清除 相当于sharepeference
     *
     * @param context
     * @return
     */
    public static String getDataDir(Context context, String childName) {
        //  /data/data/包名/childName
        return context.getDir(childName, Context.MODE_PRIVATE).getAbsolutePath();
    }


    /**
     * 获取Cache目录
     *
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Cache
        return context.getExternalCacheDir().getAbsolutePath();
    }



    /**
     * 获取Obb目录
     *
     * @param context
     * @return
     */
    public static String getObbDir(Context context) {
        //  /storage/emulated/0/Android/obb/包名
        return context.getObbDir().getAbsolutePath();
    }


    /**
     * 获取Ringtones目录
     *
     * @param context
     * @return
     */
    public static String getRingtonesDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Ringtones
        return context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * 获取Podcasts目录
     *
     * @param context
     * @return
     */
    public static String getPodcastsDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Podcasts
        return context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * 获取Music目录
     *
     * @param context
     * @return
     */
    public static String getMusicDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Music
        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取Alarms目录
     *
     * @param context
     * @return
     */
    public static String getAlarmsDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Alarms
        return context.getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * 获取pictures目录
     *
     * @param context
     * @return
     */
    public static String getPicturesDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Pictures
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }


    /**
     * 获取Movies目录
     *
     * @param context
     * @return
     */
    public static String getMoviesDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Movies
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }


    /**
     * 获取Notifications目录
     *
     * @param context
     * @return
     */
    public static String getNotificationsDir(Context context) {
        //  /storage/emulated/0/Android/data/包名/files/Notifications
        return context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }
}
