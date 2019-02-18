package top.hihuzi.collection.sql.core;

import top.hihuzi.collection.cache.*;
import top.hihuzi.collection.common.PublicMethod;
import top.hihuzi.collection.common.ValueHandleCache;
import top.hihuzi.collection.sql.config.SQLBean;
import top.hihuzi.collection.sql.config.SQLConfig;
import top.hihuzi.collection.sql.factory.SQLMethodFactory;
import top.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Field;
import java.util.*;

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

        SQLBean sqlBean = config.getSqlEeum().get();
        List<Map> lm = new ArrayList<>(list.size());
        Object newClazz = null;
        Map<String, List<E>> m = null;
        if (0 == e.length) {
            e = (E[]) config.getSqlEeum().get().getClazz().toArray();
        } else {
            Class[] ee = new Class[e.length];
            for (int i = 0; i < e.length; i++) {
                ee[i] = e[i].getClass();
            }
            e = (E[]) ee;
        }
        Map<String, ParameterCache> tableNameMatchParameter = tableNameMatchParameter(config, list.get(0), e);
        switch (config.getReturnEnum()) {
            case DEFAULT:
            case LISR:
                for (Map map : list) {
                    Map map0 = new HashMap(map.size());
                    for (Object obj : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) obj;
                        String names = String.valueOf(entry.getKey());
                        String values = String.valueOf(entry.getValue());
                        ParameterCache parameterCache = tableNameMatchParameter.get(names);
                        if (null != parameterCache) {
                            TypeCache typeCache = parameterCache.getCache().get(names);
                            String paramterName = typeCache.getParamterName();
                            if (sqlBean.getRepeat().contains(paramterName)) {
                                map0.put(typeCache.getClazz().getSimpleName() + "." + paramterName, PublicMethod.processTimeType(typeCache.getParamtertype(), config, values));
                            } else {
                                map0.put(paramterName, PublicMethod.processTimeType(typeCache.getParamtertype(), config, values));

                            }
                        }
                    }
                    lm.add(map0);
                }
                return lm;
            default:
                m = new HashMap<>(e.length);
                break;
        }
        String sqlKey = config.getSqlEeum().get().key();
        for (E es : e) {
            Class<?> clazz = (Class<?>) es;
            for (Map map : list) {
                newClazz = clazz.getDeclaredConstructor().newInstance();
                for (Object obj : map.entrySet()) {
                    Map.Entry entry = (Map.Entry) obj;
                    String names = String.valueOf(entry.getKey());
                    String values = String.valueOf(entry.getValue());
                    ParameterCache pCache = ClassCache.getPCache(sqlKey + ((Class) es).getSimpleName(), names);
                    if (null != pCache) {
                        Map<String, TypeCache> ptCache = pCache.getCache();
                        TypeCache cache = ptCache.get(names);
                        ValueHandleCache.invokeValue(newClazz, cache.getMethodSet(), values, null, config, cache.getType());
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
                        config.getReturnEnum().getList()[i].addAll(m.get(((Class) es).getSimpleName()));
                        i++;
                    }
                } catch (Exception ex) {
                    System.out.println("从新配置list顺序有误");
                    return false;
                }
                return true;
            case FILL_CLASS:
                return m.get(((Class) e[0]).getSimpleName());
            default:
                return null;
        }
    }

    /**
     * tips 无线递归上级找属性(表和对象属性匹配)
     *
     * @author: hihuzi 2019/2/12 14:06
     */
    <E> Map<String, ParameterCache> tableNameMatchParameter(SQLConfig config, Map list, E... e) {

        String sqlKey = config.getSqlEeum().get().key();
        Map nickname = config.getSqlEeum().get().getNickname();
        List<String> repeat = config.getSqlEeum().get().getRepeat();
        if (!isBeingCache(sqlKey)) {
            for (Object obj : list.keySet()) {
                for (E es : e) {
                    Class clazz = (Class) es;
                    for (; Object.class != clazz; clazz = clazz.getSuperclass()) {
                        for (Field field : clazz.getDeclaredFields()) {
                            String mark = String.valueOf(nickname.get(((Class) es).getName()));
                            StringBuffer nick = new StringBuffer(field.getName().length() + mark.length() + 2);
                            if (null != nickname && !"".equals(mark.trim())) {
                                if (repeat.contains(field.getName())) {
                                    nick.append(mark);
                                }
                            }
                            nick.append(field.getName());
                            if (StrUtils.isEquals(String.valueOf(obj), nick.toString())) {
                                ClassCache.get().add((Class<?>) es, field.getName(), null, String.valueOf(obj), sqlKey);
                                ClassCache.get().add(sqlKey, field.getName(), String.valueOf(obj));

                                break;
                            }
                        }
                    }
                    clazz = (Class) es;
                }
            }
        }
        Map<String, ParameterCache> map = SecondCache.getCache(sqlKey);
        if (null == map) {
            map = new HashMap(e.length);
            for (E es : e) {
                Map<String, ParameterCache> pCache = ClassCache.getPCache(sqlKey + ((Class) es).getSimpleName());
                map.putAll(pCache);
            }
            SecondCache.addCache(sqlKey, map);
        }
        return map;
    }

    private <E> boolean isBeingCache(String sqlKey) {

        TableCache pCache = ClassCache.getTCache(sqlKey);
        if (null == pCache) return false;
        return true;
    }

    String getSQLDefault(SQLBean config) {

        StringBuffer sql = new StringBuffer(500);
        String caches = SQLCache.get().getCache(config.key());
        if (null == caches) {
            int j = 0;
            for (Class clazz : config.getClazz()) {
                Map humpToLineMap = PublicMethod.getHumpToLine(clazz);
                Iterator iterator = humpToLineMap.entrySet().iterator();
                int i = 0, size = humpToLineMap.size();
                while (iterator.hasNext()) {
                    Map.Entry humpToLine = (Map.Entry) iterator.next();
                    String param = String.valueOf(humpToLine.getKey());
                    String table = String.valueOf(humpToLine.getValue());
                    String mark = String.valueOf(config.getNickname().get(clazz.getName()));
                    if (null == config.getDisplay()) {
                        if (null != config.getNickname() && !"".equals(mark.trim())) {
                            sql.append(mark + ".");
                        }
                        sql.append(table);
                        if (config.getRepeat() != null && config.getRepeat().contains(table)) {
                            sql.append(" " + mark + table);
                        }
                        if (i < size - 1)
                            sql.append(",");
                    } else if (0 != config.getDisplay().size()) {
                        if (config.getDisplay().contains(param)) {
                            if (null != config.getNickname() && !"".equals(mark.trim())) {
                                sql.append(mark + ".");
                            }
                            sql.append(table);
                            if (config.getRepeat().contains(param)) {
                                sql.append(" " + mark + table);
                            }
                            if (i < size - 1 && i <= config.getDisplay().size() - 1)
                                sql.append(",");
                        }
                    }

                    i++;
                }
                if (1 == size) break;
                if (j < config.getClazz().size() - 1)
                    sql.append(",");
                j++;

            }
        }

        SQLCache.get().addCache(config.key(), String.valueOf(sql));
        return String.valueOf(sql);
    }

}
