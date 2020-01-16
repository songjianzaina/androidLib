package com.insworks.lib_log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * Created by songjian on 2018-07-19.
 * 日志打印工具类：对Logger再进行一层封装 可随时升级或替换logger库 不影响应用层
 * Android的Log等级通常有五类，按照日志级别由低到高分别是Verbose、Debug、Info、Warning、Error，其对应的log定义在system层。
 * 注：Info、Warnning、Error等级的Log在普通调试中不随意滥用。
 *
 * @version 1.0.0 加入模块分支开关功能  适用于组件化开发  指定单个或多个模块关闭log打印
 */

public class LogUtil {


    protected static String currentTag = "logger";

    static {
//    Logger.addLogAdapter(new AndroidLogAdapter());

        //在初始化过程中可以使用默认值配置初始化也可以自定义
        currentTag = "logger";
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息，默认为true
                .methodCount(2)         //（可选）要显示的方法行数，默认2行
                .methodOffset(5)        //（可选）隐藏内部方法调用到偏移量，默认5
                .tag(currentTag)   //（可选）每个日志的全局标记，默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));///根据上面的格式设置logger相应的适配器

    }

    /**
     * 总开关开关二：是否打印log 如果logger内置开关失效 本开关可以应急  默认打开
     */
    private static boolean isPrintLog = true;

    /**
     * 设置全局Tag  如果用户设置了自己的tag 那就不用默认tag
     *
     * @param logTag
     */
    public static void setLogTag(String logTag) {
        currentTag = logTag;
        //清除默认适配器
        Logger.clearLogAdapters();
        //在初始化过程中可以使用默认值配置  初始化也可以自定义
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息，默认为true
                .methodCount(2)         //（可选）要显示的方法行数，默认2行
                .methodOffset(5)        //（可选）隐藏内部方法调用到偏移量，默认5
                .tag(currentTag)   //（可选）每个日志的全局标记，默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));///根据上面的格式设置logger相应的适配器
    }

    /**
     * 设置模块
     */
    public static void setModule(int module) {


    }

    /**
     * 是否打印logger
     */
    public static void setIsPrintLog(boolean isLog) {
        isPrintLog = isLog;
    }

    /**
     * 是否打印logger
     */
    public static boolean getIsPrintLog() {
        return isPrintLog;
    }


    /**
     * 获取当前输出的Tag
     */
    public static String getCurrentTag() {
        return currentTag;
    }


    /**
     * Verbose就是冗长啰嗦的：通常表达开发调试过程中的一些详细信息，不过滤地输出所有调试信息。
     */
    public static void v(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).v(msg + "");
        }
    }

    public static void v(String msg) {
        if (isPrintLog) {
            Logger.v(msg + "");
        }
    }

    /**
     * Debug来表达调试信息：用Log.d()能输出Debug、Info、Warning、Error级别的Log信息。
     */

    public static void d(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).d(msg + "");
        }
    }

    public static void d(String msg) {
        if (isPrintLog) {
            Logger.d(msg + "");
        }
    }

    public static void d(String tag, Object... args) {
        if (isPrintLog) {
            Logger.t(tag).d(args);
        }
    }

    public static void d(Object... args) {
        if (isPrintLog) {
            Logger.d(args);
        }
    }

    public static void d(String tag, Object object) {
        if (isPrintLog) {
            Logger.t(tag).d(object);
        }
    }

    public static void d(Object object) {
        if (isPrintLog) {
            Logger.d(object);
        }
    }

    /**
     * Info来表达一些信息：用Log.i()能输出Info、Warning、Error级别的Log信息。
     */
    public static void i(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).i(msg + "");
        }
    }

    public static void i(String msg) {
        if (isPrintLog) {
            Logger.i(msg + "");
        }
    }

    /**
     * Warning表示警告：但不一定会马上出现错误，开发时有时用来表示特别注意的地方。用Log.w()能输出Warning、Error级别的Log信息。
     */
    public static void w(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).w(msg + "");
        }
    }

    public static void w(String msg) {
        if (isPrintLog) {
            Logger.w(msg + "");
        }
    }

    /**
     * Error表示出现错误：是最需要关注解决的。用Log.e()输出，能输出Error级别的Log信息。
     */
    public static void e(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).e(msg + "");
        }
    }


    public static void e(String msg) {
        if (isPrintLog) {
            Logger.e(msg + "");
        }
    }
    public static void e(Throwable t,String msg) {
        if (isPrintLog) {
            Logger.e(t,msg);
        }
    }
    public static void e(Throwable t,String msg,Object... args) {
        if (isPrintLog) {
            Logger.e(t,msg,args);
        }
    }

    public static void json(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).json(msg + "");
        }
    }

    public static void json(String msg) {
        if (isPrintLog) {
            Logger.json(msg + "");
        }
    }

    public static void xml(String tag, String msg) {
        if (isPrintLog) {
            Logger.t(tag).xml(msg + "");
        }
    }

    public static void xml(String msg) {
        if (isPrintLog) {
            Logger.xml(msg + "");
        }
    }



}