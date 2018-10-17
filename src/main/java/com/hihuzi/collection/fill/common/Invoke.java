package com.hihuzi.collection.fill.common;

import com.hihuzi.collection.cache.ClassCache;
import com.hihuzi.collection.fill.constant.FillConfig;
import com.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Method;

/**
 * tips 对对象反射调用set方法配置数据
 *
 * @author: hihuzi 2018/7/19 10:29
 */

public class Invoke {


    /**
     * 遍历父类 所有 获取属性
     *
     * @parameter: E obj
     * @parameter: String names
     * @parameter: String value
     * @parameter: FillConfig config
     * @return:
     * @author: hihuzi 2018/6/22 9:22
     */
    public static <E> void injectionParameters(E obj, String names, String value, FillConfig config) throws
            Exception {

        Class clazz = obj.getClass();
        Class<?> paramtertype = null;
        try {
            paramtertype = clazz.getDeclaredField(names).getType();
        } catch (NoSuchFieldException e) {
        }
        if (StrUtils.isNNoEE(paramtertype)) {
            putValue(obj, names, value, paramtertype, config);
        } else {
            for (; clazz != Object.class; clazz = (Class<E>) clazz.getSuperclass()) {
                try {
                    paramtertype = clazz.getDeclaredField(names).getType();
                } catch (NoSuchFieldException e) {
                }
                if (StrUtils.isNNoEE(paramtertype)) {
                    putValue(obj, names, value, paramtertype, config);
                }
            }

        }
    }

    /**
     * tips 注入值 方法
     *
     * @parameter: E e
     * @parameter: String name
     * @parameter: String value
     * @parameter: Class<?> paramtertype
     * @parameter: FillConfig config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */

    public static <E> void putValue(E e, String name, String value, Class<?> paramtertype, FillConfig config) throws Exception {

        if (StrUtils.isNoEE(paramtertype)) {
            paramtertype = e.getClass().getDeclaredField(name).getType();
        }
        Method method = e.getClass().getMethod(StrUtils.achieveSetFunction(name), paramtertype);
        method.setAccessible(true);
        ClassCache.get().add(e.getClass(), name, paramtertype);
        ValueHandleCache.invokeValue(e, method, value, paramtertype.getSimpleName(), config);
    }

}
