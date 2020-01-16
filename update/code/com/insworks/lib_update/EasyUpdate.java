package com.insworks.lib_update;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.image.ImageLoader;
import com.inswork.lib_cloudbase.utils.AppUtil;
import com.inswork.lib_cloudbase.utils.WebViewUtil;
import com.insworks.lib_base.base.BaseDialog;
import com.insworks.lib_base.base.BaseDialogFragment;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.interceptor.CloudCallBack;
import com.insworks.lib_update.bean.MineUpdateBean;
import com.insworks.lib_update.net.UserApi;
import com.zhouyou.http.exception.ApiException;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_update
 * @ClassName: EasyUpdate
 * @Author: Song Jian
 * @CreateDate: 2019/7/18 11:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/18 11:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 应用更新入口类
 */
public class EasyUpdate {

    private static EasyUpdate easyUpdate;
    private static FragmentActivity activity;
    private OnUpdateClickListener listener;

    private static void testInitialize() {

        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyUpdate.init() 初始化！");
    }

    /**
     * 初始化
     *
     * @param activity
     */
    public static EasyUpdate init(FragmentActivity activity) {

        EasyUpdate.activity = activity;
        if (easyUpdate == null) {
            synchronized (EasyUpdate.class) {
                if (easyUpdate == null) {
                    easyUpdate = new EasyUpdate();
                }
            }
        }
        return easyUpdate;
    }

    /**
     * 应用更新
     *
     * @param
     * @param appDownloadUrl
     */
    private void updateApk(boolean isForce, String title, String updateInfo, final String appDownloadUrl, final OnUpdateClickListener listener) {
        testInitialize();
        BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(activity);
        builder.setContentView(R.layout.lib_update_app_dialog)
                .setCancelable(false)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                .setText(R.id.tv_title, title)
                .setText(R.id.tv_update_info, updateInfo);
        //初始化控件
        final NumberProgressBar numberProgressBar = (NumberProgressBar) builder.findViewById(R.id.npb);
        if (isForce) {
            //强制更新
            builder.findViewById(R.id.ll_close).setVisibility(View.INVISIBLE);
        }
        builder.setOnClickListener(R.id.btn_ok, new BaseDialog.OnClickListener() {
            @Override
            public void onClick(final Dialog dialog, final View view) {
                //点击升级
                if (listener != null) {
                    listener.onUpdateClick(dialog, view);
                } else {
                    //开始下载
                    downloadApk(dialog, view, appDownloadUrl, numberProgressBar);
                }


            }
        }).setOnClickListener(R.id.iv_close, new BaseDialog.OnClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                //点击关闭
                dialog.dismiss();
            }
        }).show();


    }

    /**
     * 应用更新  自定义升级跳转
     *
     * @param
     * @param
     */
    public void updateApk(boolean isForce, String title, String updateInfo, final OnUpdateClickListener listener) {

        updateApk(isForce, title, updateInfo, "", listener);
    }

    /**
     * 应用更新  采用内部下载进行自动安装
     *
     * @param
     * @param
     */
    public void updateApk(boolean isForce, String title, String updateInfo, String appDownloadUrl) {

        updateApk(isForce, title, updateInfo, appDownloadUrl, null);
    }

    //是否检测到有最新版本 用于在 我的 页面进行判断
    public boolean needUpdate = false;

    /**
     * 应用更新  采用默认github远程更新方式
     *
     * @param
     * @param
     */
    public void updateApk() {

        UserApi.getUpdateJson(new CloudCallBack<MineUpdateBean>() {
            @Override
            public void onSuccess(MineUpdateBean mineUpdateBean) {
                if (mineUpdateBean == null) {
                    return;
                }
                if (mineUpdateBean.isUpdateIgnoreVersion()) {
                    //忽略版本号大小 直接更新 优先级最高
                    updateApkByType(mineUpdateBean);
                    needUpdate = true;
                } else {
                    //否则 则按照版本号顺序来判断
                    if (mineUpdateBean.getNewVersion() > AppUtil.getVersionCode(activity)) {
                        //有新版本号 则更新
                        updateApkByType(mineUpdateBean);
                        needUpdate = true;
                    } else {
                        needUpdate = false;
                    }
                }
            }
        });
    }

    /**
     * 判断更新类型 进行更新
     *
     * @param mineUpdateBean
     */
    private void updateApkByType(MineUpdateBean mineUpdateBean) {
        if (mineUpdateBean.getType().equals("download")) {
            //下载文件方式
            updateApk(mineUpdateBean.isIsForce(), mineUpdateBean.getTitle(), mineUpdateBean.getUpdateInfo(), mineUpdateBean.getDownload().getApkURL());
        } else {
            //跳转到第三方页面的方式
            updateApk(mineUpdateBean.getJumpThird().getPicUrl(), mineUpdateBean.getJumpThird().getPayURL());
        }
    }

    /**
     * 应用更新  图片+第三方安装链接的形式
     *
     * @param
     * @param
     */
    public void updateApk(String picUrl, final String apkUrl) {
        testInitialize();
        final BaseDialogFragment.Builder builder = new BaseDialogFragment.Builder(activity);
        builder.setContentView(R.layout.lib_update_layout_qtopays)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE);
        final ImageView update_pic = (ImageView) builder.findViewById(R.id.update_pic);
        ImageLoader.loadImage(update_pic, picUrl);
        builder.setOnClickListener(R.id.update_pic, new BaseDialog.OnClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                WebViewUtil.startActivity(apkUrl);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 请求网络 下载apk
     *
     * @param dialog
     * @param view
     * @param appDownloadUrl
     * @param numberProgressBar
     */
    private void downloadApk(final Dialog dialog, final View view, String appDownloadUrl, final NumberProgressBar numberProgressBar) {
        UserApi.downloadApk(appDownloadUrl, new CloudCallBack() {
            @Override
            public void onStart() {
                view.setEnabled(false);
                numberProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void update(int progress, boolean done) {
                numberProgressBar.setProgress(progress);
                if (done) {
                    //下载完毕
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(ApiException e, String msg) {
                Toast.makeText(activity, "请求错误,请稍后再试", Toast.LENGTH_SHORT).show();
                view.setEnabled(true);
            }

            @Override
            public void onCompleted(String path) {
                Toast.makeText(activity, "下载成功 保存路径为:" + path, Toast.LENGTH_SHORT).show();
                LogUtil.d("apk下载成功 保存路径为:" + path);
                //下载完成  获取apk路径进行安装
                AppUtil.installApk(path);
            }

            @Override
            public void onSuccess(Object o) {

            }
        });
    }

    public interface OnUpdateClickListener {
        void onUpdateClick(Dialog dialog, View view);
    }


}
