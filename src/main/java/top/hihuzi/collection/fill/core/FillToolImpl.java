package top.hihuzi.collection.fill.core;

import top.hihuzi.collection.cache.ClassCache;
import top.hihuzi.collection.cache.ParameterCache;
import top.hihuzi.collection.cache.TypeCache;
import top.hihuzi.collection.fill.common.Invoke;
import top.hihuzi.collection.fill.common.ValueHandleCache;
import top.hihuzi.collection.fill.constant.FillConfig;
import top.hihuzi.collection.utils.StrUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * tips HttpServletRequest-->MAP    是否舍弃空值 并且舍弃str特定字段
 *
 * @author: hihuzi 2018/9/23 16:03
 */
public class FillToolImpl {

    /**
     * tips 缓存
     *
     * @parameter: HttpServletRequest request
     * @parameter: config config
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/9/24 9:36
     */
    public Map fillDefault(HttpServletRequest request, FillConfig config, String... key) {

        Map map = new HashMap(key.length);
        List<String> exclude = null;
        if (StrUtils.isNNoE(key)) {
            exclude = Arrays.asList(key);
        }
        Enumeration pars = request.getParameterNames();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement().toString().trim();
            String value = request.getParameter(name);
            if (StrUtils.isNNoEC(exclude)) {
                if (!exclude.contains(name)) {
                    if (StrUtils.isNNoE(value)) {
                        map.put(name, value);
                    } else {
                        if (config.getSaveStyleEnum().getHaving()) {
                            map.put(name, value);
                        }
                    }
                }
            } else {
                if (StrUtils.isNNoE(value)) {
                    map.put(name, value);
                } else {
                    if (config.getSaveStyleEnum().getHaving()) {
                        map.put(name, value);
                    }
                }

            }
        }
        return map;
    }

    /**
     * tips HttpServletRequest--> obj
     *
     * @parameter: E
     * @parameter: HttpServletRequest
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    public <E> E requestFillEntityDefault(HttpServletRequest request, E e, FillConfig config) throws Exception {

        Enumeration pars = request.getParameterNames();
        Class clazz = e.getClass();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement().toString().trim();
            String value = request.getParameter(name);
            if (StrUtils.isNNoE(value)) {
                TypeCache cache = ClassCache.getCache(clazz, name);
                if (null != cache) {
                    ValueHandleCache.invokeValueCache(e, cache.getMethodSet(), value, cache.getType(), config);
                } else {
                    Invoke.injectionParameters(e, name, value, config);
                }
            } else {
                if (config.getSaveStyleEnum().getHaving()) {
                    TypeCache cache = ClassCache.getCache(clazz, name);
                    if (null != cache) {
                        ValueHandleCache.invokeValueCache(e, cache.getMethodSet(), value, cache.getType(), config);
                    } else {
                        Invoke.injectionParameters(e, name, value, config);
                    }
                }
            }
        }
        return e;
    }

    /**
     * tips  对MAP数据装填--> 对象
     *
     * @notice: 忽略掉不在对象中的属性
     * @parameter: map
     * @parameter: E
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    public <E> E mapFillEntity(Map map, E e, FillConfig config) throws Exception {

        Iterator iterator = map.entrySet().iterator();
        Class clazz = e.getClass();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (StrUtils.isNNoE(value)) {
                TypeCache cache = ClassCache.getCache(clazz, name);
                if (null != cache) {
                    ValueHandleCache.invokeValueCache(e, cache.getMethodSet(), value, cache.getType(), config);
                } else {
                    Invoke.injectionParameters(e, name, value, config);
                }
            } else {
                if (null != config && config.getSaveStyleEnum().getHaving()) {
                    TypeCache cache = ClassCache.getCache(clazz, name);
                    if (null != cache) {
                        ValueHandleCache.invokeValueCache(e, cache.getMethodSet(), value, cache.getType(), config);
                    } else {
                        Invoke.injectionParameters(e, name, value, config);
                    }
                }
            }
        }
        return e;
    }


    /**
     * tips E --> Map  针对E与map进行填充
     *
     * @author:hihuzi 2018/6/26 14:51
     */
    public <E> Map fillMapDefault(E e, Map map, FillConfig config) throws Exception {

        Map<String, TypeCache> caches = ClassCache.getCache(e.getClass());
        if (null != caches) {
            Iterator<Map.Entry<String, TypeCache>> iterator = caches.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry typeCache = iterator.next();
                TypeCache cache = (TypeCache) typeCache.getValue();
                Method methodGet = cache.getMethodGet();
                methodGet.setAccessible(true);
                Object invoke = methodGet.invoke(e);
                if (config.getSaveStyleEnum().getHaving() && null != invoke) {
                    invoke = ValueHandleCache.processingTimeType(cache.getParamtertype(), config, invoke);
                    map.put(typeCache.getKey(), invoke);
                }
            }
        } else {
            Class<?> clazz = e.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                Method method = clazz.getMethod(StrUtils.achieveGetFunction(declaredField.getName()));
                Object invoke = method.invoke(e);
                ClassCache.get().add(e.getClass(), declaredField.getName(), type);
                if (config.getSaveStyleEnum().getHaving() && null != invoke) {
                    invoke = ValueHandleCache.processingTimeType(type, config, invoke);
                    map.put(declaredField.getName(), invoke);
                }
            }
        }
        return map;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 (针对数据库)与实体类名有区别 value -->t
     *
     * @parameter: List<String>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    public <E> List<E> listToEntityDefault(List<String> list, E e, FillConfig config) throws Exception {

        List<E> result = new ArrayList<>();
        List<String> field = new ArrayList<>();
        List<String> fieldsMap = new ArrayList<>();
        Class clazz = e.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                field.add(fields[i].getName());
                ClassCache.get().add(e.getClass(), fields[i].getName());
            }
        }
        int i = 0;
        Integer[] sort = config.getSortStyleEnum().getSort();
        if (StrUtils.isNNoEE(config) && StrUtils.isNNoEE(sort) && 0 != sort.length) {
            for (Integer integer : sort) {
                if (integer < field.size() && i <= field.size()) {
                    fieldsMap.add(field.get(integer));
                } else {
                    fieldsMap.add("");
                }
                i++;
            }
        } else {
            fieldsMap = field;
        }

        Map map = new HashMap(list.size());
        i = 0;
        for (String fields : fieldsMap) {
            if (i < list.size()) {
                map.put(fields, list.get(i));
                i++;
            }
        }
        Object obj = e.getClass().getDeclaredConstructor().newInstance();
        obj = mapFillEntity(map, obj, config);
        result.add((E) obj);
        return result;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 针对数据库与实体类名有区别 key-value -->e
     *
     * @parameter: List<Map>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    public <E> List<E> listFillEntity(List<Map> list, E t, FillConfig config) throws Exception {

        List<E> result = new ArrayList<>();
        List<String> fieldsMap = new ArrayList<>();
        List<Map> entityMaps = new ArrayList<>();
        Class clazz = t.getClass();
        Map<String, TypeCache> cache = ClassCache.getCache(clazz);
        if (null != cache) {
            fieldsMap = new ArrayList<>(cache.keySet());
        } else {
            for (; Object.class != clazz; clazz = clazz.getSuperclass()) {
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fieldsMap.add(fields[i].getName());
                    ClassCache.get().add(clazz, fields[i].getName());
                }
            }
        }
        for (Map map : list) {
            Iterator iterator = map.entrySet().iterator();
            Map transition = new HashMap(list.size());
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String names = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                if (StrUtils.isNNoE(value)) {
                    for (String fields : fieldsMap) {
                        if (isEquals(names, fields)) {
                            transition.put(fields, value);
                        }
                    }
                }
            }
            entityMaps.add(transition);
        }
        for (Map map : entityMaps) {
            Object obj = t.getClass().getDeclaredConstructor().newInstance();
            obj = mapFillEntity(map, obj, config);
            result.add((E) obj);
        }
        return result;
    }

    /**
     * tips 数据库的元组转对象
     *
     * @notice: 对象属性和表 遵循驼峰或者下划线命名
     * @author: hihuzi 2019/2/11 9:53
     */
    public <E> Boolean listToClassDefault(List<Map> list, FillConfig config, E... e) throws Exception {

        for (Map map : list) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String names = String.valueOf(entry.getKey());
                String values = String.valueOf(entry.getValue());
                for (E es : e) {
                    if (null == es) {
                        es = (E) es.getClass().getDeclaredConstructor().newInstance();
                    }
                    ParameterCache pCache = ClassCache.getPCache((Class<?>) es, names);

                    if (null != pCache) {
                        Map<String, TypeCache> ptCache = pCache.getCache();
                        Iterator it = ptCache.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entrys = (Map.Entry) iterator.next();
                            String name = String.valueOf(entrys.getKey());
                            TypeCache cache = (TypeCache) entrys.getValue();
                            if (null != ptCache) {
                                ValueHandleCache.invokeValueCache(es, cache.getMethodSet(), values, cache.getType(), config);
                            } else {
                                System.out.println("表的名称存在 属性名称不存在!!!");
                            }
                        }
                    } else {

                        Class<?> aClass = es.getClass();
                        Field[] declaredFields = aClass.getDeclaredFields();
                        for (Field field : declaredFields) {
                            if (isEquals(names, field.getName())) {
                                Invoke.injectionParameters(es, field.getName(), values, config);
                                ClassCache.get().add((Class<?>) es, field.getName(), null, names);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isEquals(String names, String name) {

        return name.toLowerCase().equals(names.replaceAll("[_|-|]", "").toLowerCase());
    }

}
