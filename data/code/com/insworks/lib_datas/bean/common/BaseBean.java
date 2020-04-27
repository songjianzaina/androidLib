package com.insworks.lib_datas.bean.common;

import com.insworks.lib_datas.utils.JsonUtil;

import java.io.Serializable;

/**
 * 数据实体基类
 * <p/>
 * Created by jiangyujiang on 16/5/31.
 */
public class BaseBean  {

    /*将JavaBean转成json文本*/
    public String toJson() {
        return JsonUtil.beanToJson(this);
    }
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