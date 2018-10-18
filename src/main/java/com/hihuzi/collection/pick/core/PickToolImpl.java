package com.hihuzi.collection.pick.core;


import com.hihuzi.collection.cache.ClassCache;
import com.hihuzi.collection.cache.TypeCache;
import com.hihuzi.collection.pick.constant.PickConfig;
import com.hihuzi.collection.utils.Constants;
import com.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * tips 带缓存机制
 *
 * @Author:hihuzi 2018/7/19 17:54
 */
public class PickToolImpl {

    /**
     * tips 只针对时间类型 和字符串类型 转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author: hihuzi 2018/10/10 19:30
     */
    public static Object processingTimeType(Class<?> type, PickConfig config, Object obj) {

        if (Constants.TypeEnum.DATE.getValue().equals(type.getSimpleName())) {
            return config.getDateStyleEnum().getFormartStyle().format(obj);
        }
        if (Constants.TypeEnum.STRING.getValue().equals(type.getSimpleName())) {
            if (config.getSaveStyleEnum().getHaving()) {
                return obj;
            } else {
                return "".equals(String.valueOf(obj).trim()) ? null : obj;
            }
        }
        return obj;
    }

    /**
     * TIPS 集合Map 取出选定字段 默认 value为空时 保存
     *
     * @parameter: Map map
     * @parameter: String[] strs
     * @return: Map
     * @author: hihuzi 2018/8/3 17:09
     */
    public Map batchMap(Map map, PickConfig config, String... str) {

        if (null == map || 0 == map.size()) {
            throw new IndexOutOfBoundsException("调用batch: 输入的是null! 或者为空值");
        }
        Map result = new HashMap<>(str.length);
        for (String property : str) {
            Object value = map.get(property);
            if (StrUtils.isNNoEE(value) && !"".equals(String.valueOf(value))) {
                result.put(property, value);
            } else if (config.getSaveStyleEnum().getHaving()) {
                result.put(property, "");
            }
        }
        return result;
    }

    /**
     * tips 同一对象集合 返回选定字段 集合返回
     *
     * @parameter: List<E> list
     * @parameter: String[] strs
     * @parameter: PickEnum enums
     * @return: List<Map>
     * @author: hihuzi 2018/7/12 8:03
     */
    public <T> Collection batch(List<T> list, PickConfig config, String... key) throws Exception {

        if (list == null) {
            throw new IndexOutOfBoundsException("调用batch: 输入的是null!");
        }
        Class clazz = list.get(0).getClass();
        List<Map> lists = new ArrayList<>(list.size());
        Set sets = new HashSet<>(list.size());
        Map maps = new HashMap(list.size());
        for (T t : list) {
            Map map = new HashMap<>(key.length);
            for (String property : key) {
                String name = StrUtils.achieveGetFunction(property);
                Object invoke = null;
                Method method = null;
                TypeCache cache = ClassCache.getCache(clazz, property);
                if (null != cache) {
                    method = cache.getMethodGet();
                    method.setAccessible(true);
                    invoke = method.invoke(t);
                    invoke = processingTimeType(cache.getParamtertype(), config, invoke);
                } else {
                    try {
                        method = clazz.getMethod(name);
                        method.setAccessible(true);
                        invoke = method.invoke(t);
                        invoke = processingTimeType(clazz.getDeclaredField(property).getType(), config, invoke);
                        ClassCache.get().add(t.getClass(), property);
                    } catch (Exception e) {
                        try {
                            clazz = clazz.getSuperclass();
                            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                                method = clazz.getMethod(name);
                                method.setAccessible(true);
                                invoke = method.invoke(t);
                                invoke = processingTimeType(clazz.getDeclaredField(property).getType(), config, invoke);
                                ClassCache.get().add(t.getClass(), property);
                                break;
                            }
                        } catch (Exception e0) {
                            continue;
                        }
                    }
                }
                switch (config.getReturnStyleEnum()) {
                    case DEFAULT:
                        achieveMap(map, property, invoke, config);
                        break;
                    case LIST_MAP:
                        achieveMap(map, property, invoke, config);
                        break;
                    case MAP:
                        achieveMap(maps, property, invoke, config);
                        break;
                    case SET:
                        if (invoke != null) {
                            sets.add(invoke);
                        } else if (config.getSaveStyleEnum().getHaving()) {
                            sets.add(invoke);
                        }
                        break;
                    default:
                        throw new Exception("数据输出超出范围 参考PickEnum定义" + list.toString());
                }
            }
            if (0 == config.getReturnStyleEnum().getKey() || 1 == config.getReturnStyleEnum().getKey()) {
                lists.add(map);
            }
        }
        if (null != sets && 0 != sets.size()) {
            return sets;
        }
        if (null != lists && 0 != lists.size()) {
            return lists;
        }
        if (null != maps && 0 != maps.size()) {
            lists.add(maps);
            return lists;
        }
        return null;
    }

    /**
     * tips 从新命名key
     *
     * @parameter:
     * @return:
     * @author: hihuzi 2018/9/28 16:03
     */
    private void achieveMap(Map map, String key, Object invoke, PickConfig config) {


        if (null != invoke) {
            map.put(achieveKey(key, config), invoke);
        } else if (config.getSaveStyleEnum().getHaving()) {
            map.put(achieveKey(key, config), "");
        }
    }

    /**
     * tips 从新命名key
     *
     * @parameter:
     * @return:
     * @author: hihuzi 2018/9/28 16:03
     */
    private String achieveKey(String property, PickConfig config) {

        switch (config.getReturnNameEnum()) {
            case DEFAULT:
                return property;
            case LOWER_CASE:
                return property.toLowerCase();
            case UPPER_CASE:
                return property.toUpperCase();
            case INITIAL_CAPITAL:
                return property.substring(0, 1).toUpperCase() + property.substring(1);
            case CUSTOM_SUFFIX:
                return config.getReturnNameEnum().getKey() + property;
            default:
                break;
        }
        return null;
    }

}
