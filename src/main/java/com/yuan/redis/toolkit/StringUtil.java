package com.yuan.redis.toolkit;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;

/**
 * 字符串工具
 *
 * @author yuan
 */
public class StringUtil {

    /**
     * author yuan
     * <p>
     * 判断对象中的参数(基本类型必须为包装类型)是否有为空的
     *
     * @param obj
     * @return 如果有，返回true,否则返回false
     */
    public static boolean checkObjFieldIsNull(Object obj) {
        boolean flag = false;
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) == null || f.get(obj).equals("")) {
                    flag = true;
                    return flag;
                }
            }
        } catch (IllegalAccessException e) {
            return flag;
        } finally {
            return flag;
        }
    }

    /**
     * 对象转字符串
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> classz = value.getClass();
        if (classz == int.class || classz == Integer.class) {
            return "" + value;
        } else if (classz == String.class) {
            return (String) value;
        } else if (classz == long.class || classz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 字符串转对象
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public static void main(String[] args) {
        System.out.println("commit -m \"yuan\"");
        System.out.println("branch");
        System.out.println("i'am fenzhi");
        System.out.println("branch");
    }

}
