//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.lib_net.net.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransMapToBeanUtils {
    public TransMapToBeanUtils() {
    }

    public static Map<String, Object> beanToMap(Object object) throws Exception {
        Map<String, Object> map = new HashMap();
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }

        return map;
    }

    public static Object mapToBean(Map<String, String> map, Class cls) {
        Object object = null;

        try {
            object = cls.newInstance();
            Iterator var3 = map.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                Field temFiels = cls.getDeclaredField(key);
                temFiels.setAccessible(true);
                temFiels.set(object, map.get(key));
            }
        } catch (InstantiationException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        } catch (NoSuchFieldException var8) {
            var8.printStackTrace();
        }

        return object;
    }
}
