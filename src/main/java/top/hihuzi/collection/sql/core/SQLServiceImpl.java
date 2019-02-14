package top.hihuzi.collection.sql.core;

import top.hihuzi.collection.cache.ClassCache;
import top.hihuzi.collection.cache.ParameterCache;
import top.hihuzi.collection.cache.SecondCache;
import top.hihuzi.collection.cache.TypeCache;
import top.hihuzi.collection.common.ValueHandleCache;
import top.hihuzi.collection.sql.constant.SQLConfig;
import top.hihuzi.collection.sql.factory.SQLMethodFactory;
import top.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tips sql+增强工具(带缓存)
 *
 * @author: hihuzi 2019/2/14 9:02
 */
public abstract class SQLServiceImpl extends SQLMethodFactory {

    /**
     * tips 数据库的元组转对象
     *
     * @notice: 对象属性和表 遵循驼峰或者下划线命名
     * @author: hihuzi 2019/2/11 9:53
     */
    <E> Object listToEntityDefault(List<Map> list, SQLConfig config, E... e) throws Exception {

        List<Map> lm = new ArrayList<>(list.size());
        Object newClazz = null;
        Map<String, List<E>> m = null;
        Map<String, ParameterCache> tableNameMatchParameter = tableNameMatchParameter(config,list, e);
        switch (config.getReturnEnum()) {
            case DEFAULT:
            case LISR:
                for (Map map : list) {
                    Map map1 = new HashMap(map.size());
                    for (Object obj : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) obj;
                        String names = String.valueOf(entry.getKey());
                        String values = String.valueOf(entry.getValue());
                        try {
                            TypeCache typeCache = tableNameMatchParameter.get(names).getCache().get(names);
                            map1.put(typeCache.getParamterName(), values);
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                    lm.add(map1);
                }
                return lm;
            default:
                m = new HashMap<>(e.length);
                break;
        }
        for (E es : e) {
            Class<?> clazz = es.getClass();
            for (Map map : list) {
                newClazz = clazz.getDeclaredConstructor().newInstance();
                for (Object obj : map.entrySet()) {
                    Map.Entry entry = (Map.Entry) obj;
                    String names = String.valueOf(entry.getKey());
                    String values = String.valueOf(entry.getValue());
                    ParameterCache pCache = ClassCache.getPCache(clazz, names);
                    if (null != pCache) {
                        Map<String, TypeCache> ptCache = pCache.getCache();
                        TypeCache cache = ptCache.get(names);
                        ValueHandleCache.invokeValue(newClazz, cache.getMethodSet(), values, null, config,cache.getType());
                    } else {
                        continue;
                    }
                }
                List<E> lis = m.get(newClazz.getClass().getSimpleName());
                if (null != lis) {
                    lis.add((E) newClazz);
                } else {
                    List<E> li = new ArrayList<>(list.size());
                    li.add((E) newClazz);
                    m.put(newClazz.getClass().getSimpleName(), li);
                }
            }
        }
        switch (config.getReturnEnum()) {
            case MAP:
                return m;
            case FILL_LIST:
                int i = 0;
                try {
                    for (E es : e) {
                        config.getReturnEnum().getList()[i].addAll(m.get(es.getClass().getSimpleName()));
                        i++;
                    }
                } catch (Exception ex) {
                    System.out.println("从新配置list顺序有误");
                    return false;
                }
                return true;
            case FILL_CLASS:
                return m.get(e[0].getClass().getSimpleName());
            default:
                return null;
        }
    }

    /**
     * tips 无线递归上级找属性(表和对象属性匹配)
     *
     * @author: hihuzi 2019/2/12 14:06
     */
    <E> Map<String, ParameterCache> tableNameMatchParameter(SQLConfig config,List<Map> list, E... e) {

        if (!isBeingCache(e)) {
            for (E es : e) {
                Class<?> clazz = es.getClass();
                for (Object obj : list.get(0).keySet()) {
                    for (; Object.class != clazz; clazz = clazz.getSuperclass()) {
                        for (Field field : clazz.getDeclaredFields()) {
                            if (StrUtils.isEquals(String.valueOf(obj), field.getName())) {
                                ClassCache.get().add(StrUtils.splicingObjectName(e), field.getName(), String.valueOf(obj));
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

    private <E> boolean isBeingCache(E... e) {

        for (E es : e) {
            Map<String, ParameterCache> pCache = ClassCache.getPCache(es.getClass());
            if (null == pCache) return false;
        }
        return true;
    }

}
