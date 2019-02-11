package top.hihuzi.collection.cache;

import java.util.HashMap;
import java.util.Map;

public class ParameterCache {

    /**
     * 缓存class 全限定名 参数类型 参数
     * 第一个 String: class 全限定名
     * 第二个String: class 属性名
     * cache--->"Map<class 全限定名称,Map<属性名称,[各个属性的方法,属性类型]>>"
     *
     * @author: hihuzi 2019/2/11 11:14
     */
    public static Map<String, TypeCache> paramCache = null;

    /**
     * tips 构造器实例化对象
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: Class<?> paramtertype
     * @author: hihuzi 2019/2/11 11:14
     */
    public ParameterCache(Class<?> clazz, String paramterName, Class<?> paramtertype) {


        Map<String, TypeCache> caches = ClassCache.getCache(clazz.getClass());
        if (null != caches) {
            Map<String, TypeCache> cache = ClassCache.getCache(clazz);
            if (null == paramCache) {
                paramCache = new HashMap<>(clazz.getDeclaredFields().length);
            }
            paramCache.putAll(cache);
        } else {
            ClassCache.get().add(clazz.getClass(), paramterName, paramtertype);
        }

    }

    /**
     * tips 构造器实例化对象
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: Class<?> paramtertype
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 23:45
     */
    public static ParameterCache add(Class<?> clazz, String paramterName, Class<?> paramtertype) {

        return new ParameterCache(clazz, paramterName, paramtertype);
    }

    public Map<String, TypeCache> getCache() {

        return paramCache;
    }


}
