package com.hihuzi.collection.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * tips class 字典 饿汉式(内部类懒加载)线程安全行
 *
 * @author: hihuzi 2018/9/23 23:37
 */
public class ClassCache {

    /**
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 属性名
     * cache--->"Map<class 全限定名称,Map<属性名称,[各个属性的方法,属性类型]>>"
     */

    public static Map<String, Map<String, TypeCache>> cache = null;

    public final static ClassCache get() {

        return CacheClazz.CLASS_CACHE;
    }

    /**
     * tips 判断是否已经缓存了
     *
     * @notice: 这样设计是为了可以值传一值
     * @parameter: Class<?> clazz
     * @parameter: String[] paramterName
     * @return: Boolean
     * @author: hihuzi 2018/9/24 17:32
     */
    public static Boolean isHaving(Class<?> clazz, String... paramterName) {

        if (paramterName.length == 0) {
            return cache.get(clazz.getName()) != null;
        } else {
            return cache.get(clazz.getName()).get(paramterName) != null;
        }
    }

    /**
     * tips 从缓存中取出数据(TypeCache)
     *
     * @parameter: clazz
     * @parameter: paramterName
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 17:32
     */
    public static TypeCache getCache(Class<?> clazz, String paramterName) {

        if (cache == null || cache.get(clazz.getName()) == null) {
            return null;
        }
        return cache.get(clazz.getName()).get(paramterName);
    }

    /**
     * tips 从缓存中取出数据(TypeCache)
     *
     * @parameter: clazz
     * @parameter: paramterName
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 17:32
     */
    public static Map<String, TypeCache> getCache(Class<?> clazz) {

        if (cache == null || cache.get(clazz.getName()) == null) {
            return null;
        }
        return cache.get(clazz.getName());
    }

    /**
     * tips 加入缓存机制
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(Class<?> clazz,
                    String paramterName) {

        if (cache != null) {
            if (cache.containsKey(clazz.getName())) {
                Map<String, TypeCache> typeCacheMap = cache.get(clazz.getName());
                joinTheCache(clazz, paramterName, typeCacheMap, null);
            } else {
                Map<String, TypeCache> typeCacheMap = new HashMap(1);
                joinTheCache(clazz, paramterName, typeCacheMap, null);
            }
        } else {
            cache = new HashMap<>(20);
            Map<String, TypeCache> typeCacheMap = new HashMap(1);
            joinTheCache(clazz, paramterName, typeCacheMap, null);
        }
    }

    /**
     * tips 加入缓存机制
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: Class<?> paramtertype
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(Class<?> clazz,
                    String paramterName,
                    Class<?> paramtertype) {

        if (cache != null) {
            if (cache.containsKey(clazz.getName())) {
                Map<String, TypeCache> typeCacheMap = cache.get(clazz.getName());
                joinTheCache(clazz, paramterName, typeCacheMap, paramtertype);
            } else {
                Map<String, TypeCache> typeCacheMap = new HashMap(1);
                joinTheCache(clazz, paramterName, typeCacheMap, paramtertype);
            }
        } else {
            cache = new HashMap<>(20);
            Map<String, TypeCache> typeCacheMap = new HashMap(1);
            joinTheCache(clazz, paramterName, typeCacheMap, paramtertype);
        }
    }

    /**
     * tips 加入缓存(工具)
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: "Map<String, TypeCache>" cacheTypeMap
     * @parameter: Class<?> paramtertype
     * @return:
     * @author: hihuzi 2018/9/24 18:09
     */
    private void joinTheCache(Class<?> clazz, String paramterName, Map<String, TypeCache> cacheTypeMap, Class<?> paramtertype) {

        cacheTypeMap.put(paramterName, TypeCache.add(clazz, paramterName, paramtertype));
        cache.put(clazz.getName(), cacheTypeMap);
    }

    /**
     * tips 内部类(延时加载)
     *
     * @author: hihuzi 2018/9/24 17:16
     */
    private static class CacheClazz {

        private static final ClassCache CLASS_CACHE = new ClassCache();

    }

}
