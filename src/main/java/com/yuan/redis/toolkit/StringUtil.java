package com.yuan.redis.toolkit;

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
}
