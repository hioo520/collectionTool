package top.hihuzi.collection.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * tips class缓存器 字典 饿汉式(内部类懒加载)线程安全行
 *
 * @author: hihuzi 2018/9/23 23:37
 */
public class ClassCache {

    /**
     * tips 对象属性缓存器(用于对象填充)
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 属性名
     * cache--->"Map<class 全限定名称,Map<属性名称,[各个属性的方法,属性类型]>>"
     */
    public static Map<String, Map<String, TypeCache>> cache = null;

    /**
     * tips 表-对象属性缓存器(用于非自定义SQL)
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 表名
     * cache--->"Map<class 全限定名称,Map<表名称,[各个属性的方法,属性类型]>>"
     */
    private static Map<String, Map<String, ParameterCache>> paramCache = null;

    /**
     * tips 对象属性-表缓存器(用于自定义多SQL)
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 表名
     * cache--->"Map<class 全限定名称,Map<各个属性的方法,表名称>>"
     */
    private static Map<String, TableCache> tableCache = null;


    /**
     * tips 从缓存中取出数据(TypeCache)
     *
     * @parameter: clazz
     * @parameter: paramterName
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 17:32
     */
    public static Map<String, TypeCache> getCache(Class<?> clazz) {

        Map<String, TypeCache> result = null;
        if (cache == null || (result = cache.get(clazz.getName())) == null) return null;
        return result;
    }

    public static TypeCache getCache(Class<?> clazz, String paramterName) {

        Map<String, TypeCache> result = null;
        if (cache == null || (result = cache.get(clazz.getName())) == null) return null;
        return result.get(paramterName);
    }

    public static Map<String, ParameterCache> getPCache(Class<?> clazz) {

        if (paramCache == null || paramCache.get(clazz.getName()) == null) return null;
        return paramCache.get(clazz.getName());
    }

    public static ParameterCache getPCache(Class<?> clazz, String paramterName) {

        Map<String, ParameterCache> result = null;
        if (null == paramCache || (result = paramCache.get(clazz.getName())) == null) return null;
        return paramCache.get(clazz.getName()).get(paramterName);
    }

    public static TableCache getTCache(String sqlKey) {

        TableCache result = null;
        if (null == ClassCache.tableCache || (result = ClassCache.tableCache.get(sqlKey)) == null) return null;
        return result;
    }

    /**
     * tips 加入缓存机制 Cache
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
     * tips 加入缓存机制 cache
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
     * tips 加入缓存机制 paramCache
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(Class<?> clazz,
                    String paramterName,
                    Class<?> paramtertype,
                    String tableName) {

        Map<String, ParameterCache> parameterCacheMap = null;
        if (null != paramCache) {
            if (paramCache.containsKey(clazz.getName())) {
                parameterCacheMap = paramCache.get(clazz.getName());
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
     * tips 加入缓存机制 TableCache
     *
     * @notice: 添加规则 同一个类对象 只有一个 key 可以有多个 TypeCache 瞬时态
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @author: hihuzi 2018/9/24 18:22
     */
    public void add(String sqlKey,
                    String paramterName,
                    String tableName) {

        TableCache tableCacheMap = null;
        if (null == tableCache) {
            tableCache = new HashMap<>(20);
            tableCache.put(sqlKey, new TableCache(paramterName, tableName));
        } else {
            if (null == (tableCacheMap = tableCache.get(sqlKey))) {
                tableCache.put(sqlKey, new TableCache(paramterName, tableName));
            } else {
                Map<String, String> cache = tableCacheMap.getCache();
                cache.put(paramterName, tableName);
                tableCache.put(sqlKey, tableCacheMap);
            }

        }
    }

    /**
     * tips 加入缓存(cache)
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: "Map<String, TypeCache>" cacheTypeMap
     * @parameter: Class<?> paramtertype
     * @return:
     * @author: hihuzi 2018/9/24 18:09
     */
    private void joinTheCache(Class<?> clazz, String
            paramterName, Map<String, TypeCache> cacheTypeMap, Class<?> paramtertype) {

        cacheTypeMap.put(paramterName, TypeCache.add(clazz, paramterName, paramtertype));
        cache.put(clazz.getName(), cacheTypeMap);
    }

    /**
     * tips 加入缓存(paramCache)
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

        paramterMap.put(tableName, ParameterCache.add(clazz, paramterName, paramtertype, tableName));
        paramCache.put(clazz.getName(), paramterMap);
    }

    public static ClassCache get() {

        return CacheClazz.CLASS_CACHE;
    }

    private ClassCache() {

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
