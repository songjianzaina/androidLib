package com.insworks.lib_datas.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * <p>
 * Created by jiang on 2015/10/8.
 */
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_NO_ = "yyyyMMdd";

    /**
     * 日期时间段描述
     *
     * @param timeMillis 过去的某个时间点毫秒值
     * @return 时间段描述字符串，例如：刚刚/10分钟前/1小时前...
     */
    public static String when(long timeMillis) {
        long nowMillis = System.currentTimeMillis();
        long sub = nowMillis - timeMillis;
        long min = 60 * 1000;
        long res = sub / min;
        if (res == 0) { // 一分钟内
            return "刚刚";
        }
        if (res == 30) {
            return "半小时前";
        }
        if (res < 60) {
            return res + "分钟前";
        }
        long minOfDay = 60 * 24;
        if (res < minOfDay) {
            return res / 60 + "小时前";
        }
        if (res < 2 * minOfDay) {
            return "昨天";
        }
        if (res < 3 * minOfDay) {
            return "前天";
        }
        if (isCurrentYear(timeMillis)) {
            return format("MM-dd HH:mm", timeMillis);
        }
        return format("yyyy-MM-dd HH:mm", timeMillis);
    }

    /**
     * 格式化日期
     *
     * @param timeMillis 待格式化日期毫秒值
     * @return 被格式化后的字符串, 格式参见: DateUtil.DEFAULT_DATE_FORMAT
     */
    public static String format(long timeMillis) {
        return format(DEFAULT_DATE_FORMAT, timeMillis);
    }

    /**
     * 格式化日期
     *
     * @param date 待格式化日期
     * @return 被格式化后的字符串, 格式参见：DateUtil.DEFAULT_DATE_FORMAT
     */
    public static String format(Date date) {
        return format(DATE_FORMAT, date);
    }



    public static String format(String dateStr) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format(DATE_FORMAT, date);
    }

    /**
     * 格式化日期
     *
     * @param format     日期格式模板
     * @param timeMillis 待格式化日期毫秒值
     * @return 被格式化后的字符串
     */
    public static String format(String format, long timeMillis) {
        Date date = new Date(timeMillis);
        return format(format, date);
    }

    /**
     * 格式化日期
     *
     * @param format 日期格式模板
     * @param date   待格式化日期
     * @return 被格式化后的字符串
     */
    public static String format(String format, Date date) {
        if (TextUtils.isEmpty(format)) {
            format = DEFAULT_DATE_FORMAT;
        }
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 判断日期是否在该年内
     *
     * @param timeMillis 时间毫秒值
     * @return 如果指定时间是该年的将返回true，否则返回false
     */
    public static boolean isCurrentYear(long timeMillis) {
        Date destDate = new Date(timeMillis);
        return isCurrentYear(destDate);
    }

    /**
     * 判断日期是否在该年内
     *
     * @param date 日期
     * @return 如果指定时间是该年的将返回true，否则返回false
     */
    public static boolean isCurrentYear(Date date) {
        Date now = new Date();
        String nowYear = format("yyyy", now);
        String destYear = format("yyyy", date);
        return nowYear.equalsIgnoreCase(destYear);
    }

    /**
     * 获取日期是星期几
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取今天是星期几
     *
     * @return
     */
    public static String getWeekOfDate() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    /**
     * 快速获取当前时间
     * @return "yyyy-MM-dd HH:mm:ss"格式
     */
    public static String getDate() {
        return format(System.currentTimeMillis());
    }

    /**
     * 快速获取当前日期
     * @return "yyyyMMdd"格式
     */
    public static String getCurrentDate() {
        return format(DateUtil.DATE_FORMAT_NO_, System.currentTimeMillis());
    }
    /**
     * 快速获取当月月初
     *
     * @return "yyyyMMdd"格式
     */
    public static String getFirstDay() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String firstday, lastday;
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;

    }

    /**
     * 快速获取当月月末
     *
     * @return "yyyyMMdd"格式
     */
    public static String getLastDay() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String firstday, lastday;
        // 获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday;

    }


    /**
     * 获取天数
     *
     * @return
     */
    public static int getDayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取小时
     *
     * @return
     */
    public static int getHourOfDay(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public static int getMinute(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 毫秒时间
     * Long类型时间转换成时长
     */
    public static String longFormatSting(long time) {
        long hour = time / (60 * 60 * 1000);
        long minute = (time - hour * 60 * 60 * 1000) / (60 * 1000);
        long second = (time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        return (hour == 0 ? "00" : (hour >= 10 ? hour : ("0" + hour))) + ":" + (minute == 0 ? "00" : (minute >= 10 ? minute : ("0" + minute))) + ":" + (second == 0 ? "00" : (second >= 10 ? second : ("0" + second)));
    }

    /**
     * 取出时分秒，并转化为long类型
     * 2017-12-13 15:47:43 -> 15:47:43 -> 56863000
     */
    public static long filterHms(long curTime) {
        String str = format("HH:mm:ss",curTime );
        String time[] = str.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
    }

    /**
     * 日期转换 20180711191821 转换成 今天 19:18  或者 07-11 19:18
     * @return
     */
    public static String translate(String originDate) {
        //截取几月几日 07-11
        String monthDate = originDate.substring(4, 6) + "-" + originDate.substring(6, 8);
        //截取时和分 19:18
        String hoursDate = originDate.substring(8, 10) + ":" + originDate.substring(10, 12);

        //截取字符串 判断是否属于今日
        if (originDate.substring(0, 8).equals(getCurrentDate())) {
            return "今天" + " " + hoursDate;
        } else {
            //不属于今日 则返回月份和时分
            return monthDate + " " + hoursDate;
        }
    }
}
