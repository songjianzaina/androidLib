package com.insworks.lib_datas.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * <pre>
 * json数据解析工具
 * 如果要解析的json数据根元素是一个对象，则使用{@link #json2Bean(String, Class)}
 * 如果要解析的json数据根元素是一个集合，则使用{@link #json2Bean(String, Type)}
 * </pre>
 */
public class JsonUtil {

    /**
     * 用于解析json的类
     */
    private static Gson GSON = new Gson();

    /**
     * 把json字符串转换为JavaBean
     *
     * @param json      json字符串
     * @param beanClass JavaBean的Class
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> beanClass) {
        T bean = null;
        try {
            bean = GSON.fromJson(json, beanClass);
        } catch (Exception e) {
            Log.i("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
        }
        return bean;
    }

    /**
     * 把json字符串转换为JavaBean。如果json的根节点就是一个集合，则使用此方法
     * <p>
     * type参数的获取方式为：Type type = new TypeToken<集合泛型>(){}.getType();
     *
     * @param json json字符串
     * @return type 指定要解析成的数据类型
     */
    public static <T> T jsonToBean(String json, Type type)  {
        T bean = null;
        try {
            bean = GSON.fromJson(json, type);
        } catch (Exception e) {
            Log.i("JsonUtil", "解析json数据时出现异常\njson = " + json, e);
        }
        return bean;
    }


    /**
     * JavaBean对象转json字符串
     *
     * @param object
     * @return
     */
    public static String beanToJson(Object object) {
        String jsonStr = "";
        try {
            jsonStr = GSON.toJson(object);
        } catch (Exception e) {
            Log.i("JsonUtil", "Bean转json数据时出现异常", e);
        }
        return jsonStr;
    }

// 以下是fastjson的方式
//    /*将JavaBean转成json文本*/
//    public String toJson() {
//        return JSON.toJSONString(this);
//    }
//
//    /**
//     * 将Json文本转JavaBean
//     *
//     * @param json
//     * @return
//     */
//    public Object fromJson(String json) {
//        return JSON.parseObject(json, getClass());
//    }
}
