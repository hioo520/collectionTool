package top.hihuzi.collection.sql.core;

import top.hihuzi.collection.cache.ClassCache;
import top.hihuzi.collection.cache.TableCache;
import top.hihuzi.collection.sql.config.SQLConfig;

import java.util.List;
import java.util.Map;

/**
 * tips sql+增强工具(带缓存)
 *
 * @author: hihuzi 2019/2/14 9:02
 */
public class SQLService extends SQLServiceImpl {

    /**
     * tips 自定义sel 查询条件 自动填充对象
     *
     * @notice: 返回值  "List<Map>" "Map<String,List<E>" "list<E>"
     * @author: hihuzi 2019/2/14 9:08
     */
    @Override
    public <E> Object listToEntity(List<Map> list, E... e) throws Exception {

        if (null == list || 0 == list.size() || null == e || 0 == e.length) return null;
        Object b = listToEntityDefault(list, new SQLConfig(), e);
        SQLConfig.reset();
        return b;
    }

    /**
     * tips 自定义sel 查询条件 自动填充对象
     *
     * @notice: 返回值  "List<Map>" "Map<String,List<E>" "list<E>"
     * @author: hihuzi 2019/2/14 9:08
     */
    @Override
    public <E> Object listToEntity(List<Map> list, SQLConfig config) throws Exception {

        if (null == list || 0 == list.size()) return null;
        Object b = listToEntityDefault(list, config, null);
        SQLConfig.reset();
        return b;
    }

    /**
     * tips 自定义sel 查询条件 自动填充对象
     *
     * @notice: 返回值  "List<Map>" "Map<String,List<E>" "list<E>"
     * @author: hihuzi 2019/2/14 9:08
     */
    @Override
    public <E> Object listToEntity(List<Map> list, SQLConfig config, E... e) throws Exception {

        if (null == list || 0 == list.size() || null == e || 0 == e.length) return null;
        Object b = listToEntityDefault(list, config, e);
        SQLConfig.reset();
        return b;
    }

    @Override
    public <E> Object config(SQLConfig config) throws Exception {

        Map sqls = config.getSqlEeum().getMap();
        TableCache caches = ClassCache.getTCache(String.valueOf(sqls.get("UNIQUE")));
        if (null == caches) {
            /* TODO 根据驼峰 加入缓存 tableCache*/
        }
        return null;
    }

}
