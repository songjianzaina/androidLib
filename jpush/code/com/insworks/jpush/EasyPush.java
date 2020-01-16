package com.insworks.jpush;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.inswork.lib_cloudbase.R;
import com.insworks.jpush.receiver.OnJpushCallBack;
import com.insworks.jpush.utils.TagAliasOperatorHelper;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Clevo on 2016/10/11.
 */

public class EasyPush {

    private static EasyPush easyPush;
    private static Context application;
    private String jumpAction;
    private OnJpushCallBack listener;

    private EasyPush() {

    }

    public static EasyPush getInstance(Context app) {
        synchronized (EasyPush.class) {
            if (easyPush == null) {
                synchronized (EasyPush.class) {
                    easyPush = new EasyPush();
                    application = app;
                }
            }
        }
        return easyPush;
    }

    public EasyPush init() {
        JPushInterface.init(application);
        return this;
    }

    public EasyPush setDebugMode(boolean flag) {
        JPushInterface.setDebugMode(true);
        return this;
    }

    public String getRegistrationID() {
        return JPushInterface.getRegistrationID(application);
    }

    public EasyPush setAlias(String alias) {
        JPushInterface.setAliasAndTags(application, alias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;
    }

    public EasyPush setTags(Set<String> tags) {
        JPushInterface.setAliasAndTags(application, null, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;
    }

    /**
     * 同时设置别名与标签。
     * 注：
     * 这个接口是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置。
     *
     * @param alias null 此次调用不设置此值。（注：不是指的字符串"null"）
     *              "" （空字符串）表示取消之前的设置。
     *              每次调用设置有效的别名，覆盖之前的设置。
     *              有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     *              限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     * @param tags  null 此次调用不设置此值。（注：不是指的字符串"null"）
     *              空数组或列表表示取消之前的设置。
     *              每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     *              有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     *              限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，但总长度不得超过7K字节。（判断长度需采用UTF-8编码）
     */
    public EasyPush setAliasAndTags(String alias, Set<String> tags) {
        JPushInterface.setAliasAndTags(application, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;

    }

    public EasyPush removeAlias() {
        JPushInterface.setAliasAndTags(application, "", null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;
    }

    public EasyPush removeTags() {
        JPushInterface.setAliasAndTags(application, null, new LinkedHashSet<String>(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;
    }

    public EasyPush removeAllAliasAndTags() {
        JPushInterface.setAliasAndTags(application, "", new LinkedHashSet<String>(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("EasyPush", "Set tag and alias success");
                        break;
                    default:
                        Log.d("EasyPush", "Set tag and alias fail, " + "errorcode:" + i);
                }
            }
        });
        return this;
    }

    /**
     * 停止推送服务
     */
    public EasyPush stopPush() {
        JPushInterface.stopPush(application);
        return this;
    }

    /**
     * 恢复推送服务。
     */
    public EasyPush resumePush() {
        JPushInterface.resumePush(application);
        return this;
    }

    /**
     * 用来检查 Push Service 是否已经被停止
     *
     * @return
     */
    public boolean isPushStopped() {
        return JPushInterface.isPushStopped(application);
    }

    /**
     * 清除所有 JPush 展现的通知
     */
    public EasyPush clearAllNotifications() {
        JPushInterface.clearAllNotifications(application);
        return this;
    }

    /**
     * 清除指定某个通知。
     *
     * @param notificationId 此 notificationId 来源于intent参数 JPushInterface.EXTRA_NOTIFICATION_ID，可参考文档 接收推送消息Receiver
     */
    public EasyPush clearNotificationById(int notificationId) {
        JPushInterface.clearNotificationById(application, notificationId);
        return this;
    }

    /**
     * 设置允许推送时间
     * 默认情况下用户在任何时间都允许推送。即任何时候有推送下来，客户端都会收到，并展示。开发者可以调用此 API 来设置允许推送的时间。如果不在该时间段内收到消息，当前的行为是：推送到的通知会被扔掉。
     *
     * @param weekDays  0表示星期天，1表示星期一，以此类推。 （7天制，Set集合里面的int范围为0到6）
     *                  Sdk1.2.9 – 新功能:set的值为null,则任何时间都可以收到消息和通知，set的size为0，则表示任何时间都收不到消息和通知.
     * @param startHour 允许推送的开始时间 （24小时制：startHour的范围为0到23）
     * @param endHour   允许推送的结束时间 （24小时制：endHour的范围为0到23）
     */
    public EasyPush setPushTime(Set<Integer> weekDays, int startHour, int endHour) {
        JPushInterface.setPushTime(application, weekDays, startHour, endHour);
        return this;

    }

    /**
     * 设置通知静默时间
     * 开发者可以调用此 API 来设置静音时段。如果在该时间段内收到消息，则：不会有铃声和震动。
     *
     * @param startHour   静音时段的开始时间 - 小时 （24小时制，范围：0~23 ）
     * @param startMinute 静音时段的开始时间 - 分钟（范围：0~59 ）
     * @param endHour     静音时段的结束时间 - 小时 （24小时制，范围：0~23 ）
     * @param endMinute   静音时段的结束时间 - 分钟（范围：0~59 ）
     */
    public EasyPush setSilenceTime(int startHour, int startMinute, int endHour, int endMinute) {
        JPushInterface.setSilenceTime(application, startHour, startMinute, endHour, endMinute);
        return this;
    }

    /**
     * 设置保留最近通知条数
     *
     * @param maxNum
     */
    public EasyPush setLatestNotificationNumber(int maxNum) {
        JPushInterface.setLatestNotificationNumber(application, maxNum);
        return this;
    }

    public EasyPush setJumpActivityAction(String jumpAction) {

        this.jumpAction = jumpAction;
        return this;
    }

    public String getJumpActivityAction() {
        return jumpAction;
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    public EasyPush setStyleBasic(int iconRes) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(application);
        builder.statusBarDrawable = iconRes;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
        return this;
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    public EasyPush setStyleCustom(int customLayout, int IconRes) {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(application, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = IconRes;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);

        return this;
    }


    public EasyPush setAddActionsStyle() {
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(application);
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(10, builder);
        return this;
    }

    /**
     * 设置手机号
     *
     * @param sequence
     * @param mobileNumber
     */
    public EasyPush setPhoneNumber(int sequence, String mobileNumber) {
        TagAliasOperatorHelper.getInstance().handleAction(application, sequence, mobileNumber);
        return this;
    }

    /**
     * 设置回调
     *
     * @param listener
     * @return
     */
    public EasyPush setOnPushListener(OnJpushCallBack listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 获取监听对象  考虑到生命周期的问题  暂时不使用回调
     * @return
     */
    public OnJpushCallBack getOnPushListener() {
        return listener;
    }
}
