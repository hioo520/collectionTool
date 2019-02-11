package top.hihuzi.collection.cache;

import top.hihuzi.collection.utils.Constants;

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

    /**
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 表名
     * cache--->"Map<class 全限定名称,Map<表名称,[各个属性的方法,属性类型]>>"
     */

    public static Map<String, Map<String, ParameterCache>> paramCache = null;

    public static ClassCache get() {

        return CacheClazz.CLASS_CACHE;
    }

    /**
     * tips 判断是否已经缓存了
     *
     * @notice: 这样设计是为了可以值传一值(对来自表 - 0 - Constants.TABLE的数据判断无效)
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
     * tips 从缓存中取出数据(ParameterCache)
     *
     * @parameter: clazz
     * @parameter: paramterName
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 17:32
     */
    public static ParameterCache getPCache(Class<?> clazz, String paramterName) {

        if (paramCache == null || paramCache.get(clazz.getName()) == null) {
            return null;
        }
        return paramCache.get(clazz.getName()).get(paramterName);
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
     * tips 从缓存中取出数据(TypeCache)
     *
     * @parameter: clazz
     * @parameter: paramterName
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 17:32
     */
    public static Map<String, ParameterCache> getPCache(Class<?> clazz) {

        if (paramCache == null || paramCache.get(clazz.getName()) == null) {
            return null;
        }
        return paramCache.get(clazz.getName());
    }

    /**
     * tips 加入缓存机制
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(Class<?> clazz, String paramterName) {

        add(clazz, paramterName, null);
    }

    /**
     * tips 加入缓存机制
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(Class<?> clazz, String paramterName, Class<?> paramtertype, String tableName) {

        Map<String, ParameterCache> parameterCacheMap = null;
        if (null != paramCache) {
            if (paramCache.containsKey(clazz.getName() + Constants.TABLE)) {
                parameterCacheMap = paramCache.get(clazz.getName() + Constants.TABLE);
            } else {
                parameterCacheMap = new HashMap(1);
            }
        } else {
            paramCache = new HashMap<>(20);
            parameterCacheMap = new HashMap(1);
        }
        joinTheCache(clazz, paramterName, parameterCacheMap, paramtertype, tableName);
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

        Map<String, TypeCache> typeCacheMap = null;
        if (cache != null) {
            if (cache.containsKey(clazz.getName())) {
                typeCacheMap = cache.get(clazz.getName());
            } else {
                typeCacheMap = new HashMap(1);
            }
        } else {
            cache = new HashMap<>(20);
            typeCacheMap = new HashMap(1);
        }
        joinTheCache(clazz, paramterName, typeCacheMap, paramtertype);
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
     * tips 加入缓存(表)
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: "Map<String, TypeCache>" cacheTypeMap
     * @parameter: String tableName
     * @parameter: Class<?> paramtertype
     * @return:
     * @author: hihuzi 2018/9/24 18:09
     */
    private void joinTheCache(Class<?> clazz, String paramterName, Map<String, ParameterCache> paramterMap,
                              Class<?> paramtertype, String tableName) {

        paramterMap.put(tableName, ParameterCache.add(clazz, paramterName, paramtertype));
        paramCache.put(clazz.getName() + Constants.TABLE, paramterMap);
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
