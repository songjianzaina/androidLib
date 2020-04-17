package com.insworks.ushare2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.insworks.lib_log.LogUtil;
import com.insworks.ushare2.utils.AppidConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_share.bilib
 * @ClassName: XShare
 * @Author: Song Jian
 * @CreateDate: 2019/5/14 15:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/14 15:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 该类中设置需要分享的内容
 */
public class XShare {
    private static String TITLE = "您的好友推荐您来体验土豆先生";
    private static String CONTENT = "互联网时代的一款“服务平台-土豆先生";
    private static String TARGET_URL = "https://www.pgyer.com/cloudpay";
    private static String IMAGE_URL = "http://i2.hdslb.com/320_200/video/85/85ae2b17b223a0cd649a49c38c32dd10.jpg";
    private static XShare singleton;
    private static Activity context;
    private final View anchor;
    private int shareType = 2;//默认webpage形式
    private final int PICTURE = 1;
    private final int WEBPAGE = 2;
    private final int AUDIO = 3;
    private final int VIDEO = 4;
    private File file = null;
    private Bitmap bitmap = null;
    private int resId = -1;
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
    private List<String> imageList;


    /**
     * 社会分享 入口
     *
     * @param activity
     * @param clickView 锚点
     * @return
     */
    public static XShare init(Activity activity, View clickView) {
        context = activity;
        singleton = new XShare(activity, clickView);
//        if (singleton == null) {
//            synchronized (EasyData.class) {
//                if (singleton == null) {
//                }
//            }
//        }
        return singleton;
    }


    public XShare(Activity activity, View clickView) {
        anchor = clickView;

        //初始化友盟分享
        UMConfigure.init(context.getApplicationContext(), AppidConfig.readUmengKey(context)
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        //微信
        PlatformConfig.setWeixin(AppidConfig.readWECHATAPPID(context), AppidConfig.readWetchatAppKey(context));
        //qq
        PlatformConfig.setQQZone(AppidConfig.readQQAPPID(context) + "", AppidConfig.readQQAppKey(context));
    }

    /**
     * 设置分享内容 webpage形式
     *
     * @param title
     * @param content
     * @param imageUrl
     * @param targetUrl
     */
    public XShare setContent(String title, String content, String imageUrl, String targetUrl) {
        shareType = WEBPAGE;
        setContent(title, content, targetUrl);
        if (!TextUtils.isEmpty(imageUrl)) {

            IMAGE_URL = imageUrl;
        }
        return this;
    }

    /**
     * 设置分享内容
     *
     * @param title
     * @param content
     * @param
     * @param targetUrl
     */
    private XShare setContent(String title, String content, String targetUrl) {
        if (!TextUtils.isEmpty(title)) {

            TITLE = title;
        }
        if (!TextUtils.isEmpty(content)) {

            CONTENT = content;
        }
        if (!TextUtils.isEmpty(targetUrl)) {

            TARGET_URL = targetUrl;
        }
        return this;
    }

    /**
     * 设置分享内容 图片形式
     *
     * @param title
     * @param content
     * @param imageUrl
     * @param targetUrl
     */
    public XShare setImage(String title, String content, String imageUrl, String targetUrl) {
        shareType = PICTURE;

        setContent(title, content, targetUrl);
        if (!TextUtils.isEmpty(imageUrl)) {
            IMAGE_URL = imageUrl;
        }
        return this;
    }

    /**
     * 设置分享内容 图片形式
     *
     * @param title
     * @param content
     * @param targetUrl
     */
    public XShare setImage(String title, String content, String targetUrl, Bitmap targetMap) {
        shareType = PICTURE;

        setContent(title, content, targetUrl);
        if (targetMap != null) {
            bitmap = targetMap;
        }
        return this;
    }

    /**
     * 设置分享内容 图片形式
     *
     * @param title
     * @param content
     * @param targetUrl
     */
    public XShare setImage(String title, String content, String targetUrl, List<String> url) {
        shareType = PICTURE;

        setContent(title, content, targetUrl);
        if (url != null) {
            imageList = url;
        }
        return this;
    }

    public void show() {
        ShareAction shareAction = new ShareAction(context);
        try {
            if (shareType == PICTURE) {
                //分享图片
                if (imageList != null && imageList.size() > 0) {
                    UMImage[] list = new UMImage[12];
                    for (int i = 0; i < imageList.size(); i++) {
                        list[i] = new UMImage(context, imageList.get(i));
                    }
                    shareAction.withMedias(list);
                } else {

                    shareAction.withMedia(generateImage());
                }
            } else {
                //分享web
                UMWeb web = new UMWeb(TARGET_URL);
                //web信息
                web.setTitle(TITLE);//标题
                web.setThumb(generateImage());  //缩略图
                web.setDescription(CONTENT);//描述
                shareAction.withMedia(web);
            }

            shareAction.setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).open();
        } catch (Exception e) {
            LogUtil.e(e, "分享出现异常");
        }
    }


    private UMImage generateImage() {
        UMImage image;
        if (file != null) {
            image = new UMImage(context, file);

        } else if (bitmap != null) {
            image = new UMImage(context, bitmap);
        } else if (resId != -1) {

            image = new UMImage(context, resId);
        } else {
            image = new UMImage(context, IMAGE_URL);
        }
        return image;
    }
}
