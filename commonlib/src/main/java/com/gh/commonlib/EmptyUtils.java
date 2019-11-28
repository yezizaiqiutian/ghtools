package com.gh.commonlib;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: gh
 * time: 2017/6/9.
 * description:字符串判空工具
 */

public class EmptyUtils {

    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string) || "null".equals(string);
    }

    /**
     * 判断String是否为空
     * 当为null，设置为""
     *
     * @param string
     * @return
     */
    public static String EmptyString(String string) {
        return isEmpty(string) ? "" : string;
    }

    /**
     * 判断String是否为空
     * 当为null，设置为temp
     *
     * @param string
     * @param temp
     * @return
     */
    public static String EmptyString(String string, String temp) {
        return isEmpty(string) ? temp : string;
    }

    public static List EmptyList(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        return list;
    }

    public static <T> T EmptyEntity(T t, Class<T> clazz) {
        if (t == null) {
            try {
                t = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

}
