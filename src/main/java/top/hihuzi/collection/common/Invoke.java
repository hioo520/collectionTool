package top.hihuzi.collection.common;

import top.hihuzi.collection.cache.ClassCache;
import top.hihuzi.collection.cache.ParameterCache;
import top.hihuzi.collection.cache.SecondCache;
import top.hihuzi.collection.config.Config;
import top.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * tips 对对象反射调用set方法配置数据
 *
 * @author: hihuzi 2018/7/19 10:29
 */

public class Invoke {


    /**
     * 遍历父类 所有 获取属性
     *
     * @parameter: E e
     * @parameter: String name
     * @parameter: String value
     * @parameter: Config config
     * @return:
     * @author: hihuzi 2018/6/22 9:22
     */
    public static <E> void injectionParameters(E e, String name, String value, Config config) throws
            Exception {

        Class clazz = e.getClass();
        Class<?> paramtertype = null;
        try {
            paramtertype = clazz.getDeclaredField(name).getType();
        } catch (NoSuchFieldException e0) {
        }
        if (StrUtils.isNNoEE(paramtertype)) {
            putValue(e, name, value, paramtertype, config);
        } else {
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    paramtertype = clazz.getDeclaredField(name).getType();
                } catch (NoSuchFieldException e1) {
                }
                if (StrUtils.isNNoEE(paramtertype)) {
                    putValue(e, name, value, paramtertype, config);
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
     * @parameter: Config config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */

    private static <E> void putValue(E e, String name, String value, Class<?> paramtertype, Config config) throws Exception {

        if (StrUtils.isNoEE(paramtertype)) {
            paramtertype = e.getClass().getDeclaredField(name).getType();
        }
        Method method = e.getClass().getMethod(StrUtils.achieveSetFunction(name), paramtertype);
        method.setAccessible(true);
        ClassCache.get().add(e.getClass(), name, paramtertype);
        ValueHandleCache.invokeValue(e, method, value, paramtertype.getSimpleName(), config, null);
    }

    /**
     * tips 无线递归上级找属性(表和对象属性匹配)
     *
     * @author: hihuzi 2019/2/12 14:06
     */
    public static <E> Map<String, ParameterCache> tableNameMatchParameter(Map list, E... e) {

        if (!isBeingCache(e)) {
            for (E es : e) {
                Class<?> clazz = es.getClass();
                for (Object obj : list.keySet()) {
                    for (; Object.class != clazz; clazz = clazz.getSuperclass()) {
                        for (Field field : clazz.getDeclaredFields()) {
                            if (StrUtils.isEquals(String.valueOf(obj), field.getName())) {
                                ClassCache.get().add(es.getClass(), field.getName(), null, String.valueOf(obj));
                                break;
                            }
                        }
                    }
                    clazz = es.getClass();
                }
            }
        }
        Map<String, ParameterCache> map = SecondCache.getCache(StrUtils.splicingObjectName(e));
        if (null == map) {
            map = new HashMap(e.length);
            for (E es : e) {
                Map<String, ParameterCache> pCache = ClassCache.getPCache(es.getClass());
                map.putAll(pCache);
            }
            SecondCache.addCache(StrUtils.splicingObjectName(e), map);
        }
        return map;
    }

    /**
     * tips 初级缓存
     *
     * @author: hihuzi 2019/2/14 13:00
     */
    private static <E> boolean isBeingCache(E... e) {


        for (E es : e) {
            Map<String, ParameterCache> pCache = ClassCache.getPCache(es.getClass());
            if (null == pCache) return false;
        }
        return true;
    }

    /**
     * tips 只针对时间类型 和字符串类型 转化
     *
     * @author: hihuzi 2018/10/10 19:30
     */
    public static Object processingTimeType(Class<?> type, Config config, Object obj) {

        if (ValueHandleCache.TypeEnum.DATE.getValue().equals(type.getSimpleName())) {
            if (null == obj) return null;
            return config.getDateStyleEnum().getFormartStyle().format(obj);
        }
        if (ValueHandleCache.TypeEnum.STRING.getValue().equals(type.getSimpleName())) {
            if (config.getSaveStyleEnum().getHaving()) {
                return obj;
            } else {
                return "".equals(String.valueOf(obj).trim()) ? null : obj;
            }
        }
        return obj;
    }

    /**
     * tips 只针对时间类型 和字符串类型 转化
     *
     * @author: hihuzi 2018/10/10 19:30
     */
    public static Object processTimeType(Class<?> type, Config config, Object obj) {

        if (ValueHandleCache.TypeEnum.DATE.getValue().equals(type.getSimpleName())) {
            if (null == obj) return null;
            return config.getDateStyleEnum().getFormartStyle().format(obj);
        }
        if (ValueHandleCache.TypeEnum.STRING.getValue().equals(type.getSimpleName())) {
            if (config.getSaveStyleEnum().getHaving()) {
                return obj;
            } else {
                return "".equals(String.valueOf(obj).trim()) ? null : obj;
            }
        }
        return obj;
    }

}
