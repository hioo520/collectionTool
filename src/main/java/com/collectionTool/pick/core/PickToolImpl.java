package com.collectionTool.pick.core;


import com.collectionTool.cache.ClassCache;
import com.collectionTool.cache.TypeCache;
import com.collectionTool.fill.common.ValueHandleCache;
import com.collectionTool.pick.constant.PickConfig;
import com.collectionTool.utils.Constants;
import com.collectionTool.utils.StrUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * tips 带缓存机制
 *
 * @Author:hihuzi 2018/7/19 17:54
 */
public class PickToolImpl {
    /**
     * TIPS 集合Map 取出选定字段 默认 value为空时 保存
     *
     * @parameter: Map map
     * @parameter: String[] strs
     * @return: Map
     * @author: hihuzi 2018/8/3 17:09
     */
    public Map batchMap(Map map, PickConfig config, String... str) {

        if (map == null || map.size() == 0) {
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
        Class<?> clazz = list.get(0).getClass();
        List<Map> lists = new ArrayList<>(list.size());
        Set sets = new HashSet<>(list.size());
        Map maps = new HashMap(list.size());
        for (T t : list) {
            Map map = new HashMap<>(key.length);
            for (String property : key) {
                String name = StrUtils.achieveGetFunction(property);
                Object invoke = null;
                TypeCache cache = ClassCache.getCache(clazz, property);
                if (cache != null) {
                    Method method = cache.getMethodGet();
                    method.setAccessible(true);
                    invoke = method.invoke(t);
                    invoke = processingTimeType(cache.getParamtertype(), config, invoke);
                } else {
                    try {
                        Method method = clazz.getMethod(name);
                        method.setAccessible(true);
                        invoke = method.invoke(t);
                        invoke = processingTimeType(clazz.getDeclaredField(property).getType(), config, invoke);
                        ClassCache.get().add(t.getClass(), property);
                    } catch (Exception e) {
                        System.out.println("对应的实体里面没有方法: " + name);
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
            if (config.getReturnStyleEnum().getKey() == 0 || config.getReturnStyleEnum().getKey() == 1) {
                lists.add(map);
            }
        }
        if (sets != null && sets.size() != 0) {
            return sets;
        }
        if (lists != null && lists.size() != 0) {
            return lists;
        }
        if (maps != null && maps.size() != 0) {
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
    private void achieveMap(Map map, String key, Object invoke, PickConfig bean) {


        if (invoke != null) {
            map.put(achieveKey(key, bean), invoke);
        } else if (bean.getSaveStyleEnum().getHaving()) {
            map.put(achieveKey(key, bean), "");
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
                return new StringBuffer().append(property.toLowerCase()).toString();
            case UPPER_CASE:
                return new StringBuffer().append(property.toUpperCase()).toString();
            case INITIAL_CAPITAL:
                return new StringBuffer().append(property.substring(0, 1).toUpperCase()).append(property.substring(1)).toString();
            case CUSTOM_SUFFIX:
                return config.getReturnNameEnum().getKey() + property;
            default:
                break;
        }
        return null;
    }

    /**
     * tips 只针对时间类型转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author: hihuzi 2018/10/10 19:30
     */
    public static Object processingTimeType(Class<?> type, PickConfig config, Object obj) {
        if (Constants.TypeEnum.DATE.getValue().equals(type.getSimpleName())) {
            return config.getDateStyleEnum().getFormartStyle().format(obj);
        }
        return obj;
    }
}
