package top.hihuzi.collection.fill.core;

import top.hihuzi.collection.fill.constant.FillConfig;
import top.hihuzi.collection.fill.FillFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * tips 填充工具
 *
 * @author: hihuzi 2018/9/23 16:24
 */
public class FillTool extends FillToolImpl implements FillFactory {


    /**
     * tips HttpServletRequest-->MAP    保存空值
     *
     * @parameter: HttpServletRequest request
     * @return: Map
     * @author: hihuzi 2018/6/14 14:51
     */
    @Override
    public Map fill(HttpServletRequest request) {

        Map map = fillDefault(request, new FillConfig());
        FillConfig.reset();
        return map;
    }

    /**
     * tips HttpServletRequest-->MAP   str 去掉没用的字段
     *
     * @parameter: HttpServletRequest
     * @parameter: String[]
     * @return: Map
     * @author: hihuzi 2018/6/14 14:51
     */
    @Override
    public Map fill(HttpServletRequest request, String... str) {

        Map map = fillDefault(request, new FillConfig(), str);
        FillConfig.reset();
        return map;
    }

    /**
     * tips HttpServletRequest-->MAP   是否舍弃空值  默认舍弃空字符
     *
     * @parameter: HttpServletRequest request
     * @parameter: FillConfig config
     * @return: Map
     * @author: hihuzi 2018/6/14 14:51
     */
    @Override
    public Map fill(HttpServletRequest request, FillConfig config) {

        Map map = fillDefault(request, config);
        FillConfig.reset();
        return map;
    }

    /**
     * tips HttpServletRequest-->MAP    是否舍弃空值 并且舍弃str特定字段
     *
     * @parameter: HttpServletRequest request
     * @parameter: FillConfig config
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/6/14 14:51
     */
    @Override
    public Map fill(HttpServletRequest request, FillConfig config, String... key) {

        Map map = fillDefault(request, config, key);
        FillConfig.reset();
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
    @Override
    public <E> E fillEntity(HttpServletRequest request, E e) throws Exception {

        E entity = requestFillEntityDefault(request, e, new FillConfig(FillConfig.SaveStyleEnum.DEFAULT));
        FillConfig.reset();
        return entity;
    }

    /**
     * tips HttpServletRequest--> obj
     *
     * @parameter: E
     * @parameter: HttpServletRequest
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    @Override
    public <E> E fillEntity(HttpServletRequest request, E e, FillConfig config) throws Exception {

        E entity = requestFillEntityDefault(request, e, config);
        FillConfig.reset();
        return entity;
    }

    /**
     * tips tips 对MAP数据装填--> 对象
     *
     * @parameter: map
     * @parameter: E
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    @Override
    public <E> E fillEntity(Map map, E e) throws Exception {
        if (null == map) return null;
        E entity = mapFillEntity(map, e, new FillConfig(FillConfig.SaveStyleEnum.DEFAULT));
        FillConfig.reset();
        return entity;
    }

    /**
     * tips tips 对MAP数据装填--> 对象
     *
     * @parameter: map
     * @parameter: E
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    @Override
    public <E> E fillEntity(Map map, E e, FillConfig config) throws Exception {
        if (null == map) return null;
        E entity = mapFillEntity(map, e, config);
        FillConfig.reset();
        return entity;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 针对数据库与实体类名有区别 key-value -->e
     *
     * @parameter: List<Map>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> List<E> fillEntity(List<Map> list, E e) throws Exception {
        if (null == list) return null;
        List<E> entitys = listFillEntity(list, e, new FillConfig(FillConfig.SaveStyleEnum.DEFAULT));
        FillConfig.reset();
        return entitys;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 针对数据库与实体类名有区别 key-value -->e
     *
     * @parameter: List<Map>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> List<E> fillEntity(List<Map> list, E e, FillConfig config) throws Exception {
        if (null == list) return null;
        List<E> entitys = listFillEntity(list, e, config);
        FillConfig.reset();
        return entitys;
    }

    /**
     * tips E --> Map  针对E的属性属性值填充到map
     *
     * @parameter: E e
     * @parameter: map map
     * @return: map
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> Map fillMap(E e, Map map) throws Exception {
        if (null == e) return null;
        Map map1 = fillMapDefault(e, map, new FillConfig(FillConfig.SaveStyleEnum.DEFAULT));
        FillConfig.reset();
        return map1;
    }

    /**
     * tips E --> Map  针对E的属性属性值填充到map
     *
     * @parameter: E e
     * @parameter: map map
     * @return: map
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> Map fillMap(E e, Map map, FillConfig config) throws Exception {
        if (null == e) return null;
        Map map1 = fillMapDefault(e, map, config);
        FillConfig.reset();
        return map1;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 (针对数据库)与实体类名有区别 value -->t
     *
     * @parameter: List<String>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> List<E> listToEntity(List<String> list, E e) throws Exception {
        if (null == list) return null;
        List<E> es = listToEntityDefault(list, e, new FillConfig(FillConfig.SaveStyleEnum.DEFAULT));
        FillConfig.reset();
        return es;
    }

    /**
     * tips tips 对LIST数据装填--> 对象 (针对数据库)与实体类名有区别 value -->t
     *
     * @parameter: List<String>
     * @parameter: E
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    @Override
    public <E> List<E> listToEntity(List<String> list, E e, FillConfig config) throws Exception {
        if (null == list) return null;
        List<E> es = listToEntityDefault(list, e, config);
        FillConfig.reset();
        return es;
    }

}
