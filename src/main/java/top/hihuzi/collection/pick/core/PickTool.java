package top.hihuzi.collection.pick.core;

import top.hihuzi.collection.pick.PickFactory;
import top.hihuzi.collection.pick.constant.PickBase;
import top.hihuzi.collection.pick.constant.PickConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * tips  方法的具体实现
 *
 * @author: hihuzi 2018/7/19 17:53
 */
public class PickTool extends PickToolImpl implements PickFactory {

    /**
     * tips 从对象集合中取特定字段
     *
     * @parameter: List<E> list
     * @parameter: String[] parameter
     * @return: List<Map>
     * @author: hihuzi 2018/7/12 8:03
     */
    @Override
    public <E> List<Map> pick(List<E> list, String... parameter) throws Exception {

        if (null == list || 0 == list.size()) return null;
        List<Map> lists = (List<Map>) batch(list, new PickConfig(), parameter);
        PickConfig.reset();
        return lists;
    }

    /**
     * tips 从对象集合中取特定字段(带控制返回值)
     *
     * @parameter: List<E> list
     * @parameter: PickConfig config
     * @parameter: String[] parameter
     * @return: List<Map>
     * @author: hihuzi 2018/7/12 8:03
     */
    @Override
    public <E> List<Map> pick(List<E> list, PickConfig config, String... parameter) throws Exception {

        if (null == list || 0 == list.size()) return null;
        List<Map> lists = (List<Map>) batch(list, config, parameter);
        PickConfig.reset();
        return lists;
    }

    /**
     * tips 从对象集合中取特定字段的value(带控制返回值)(去重)
     *
     * @parameter: List<E> list
     * @parameter: String[] parameter
     * @return: Set<String>
     * @author: hihuzi 2018/4/30 15:49
     */
    @Override
    public <E> Set pickValue(List<E> list, String... parameter) throws Exception {

        if (null == list || 0 == list.size()) return null;
        Set set = (Set) batch(list, new PickConfig(
                PickBase.ReturnStyleEnum.SET,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), parameter);
        PickConfig.reset();
        return set;
    }

    /**
     * tips 从对象集合中取特定字段的value(带控制返回值)(去重)
     *
     * @parameter: List<E> list
     * @parameter: PickConfig enmu
     * @parameter: String[] parameter
     * @return: Set<String>
     * @author: hihuzi 2018/4/30 15:49
     */
    @Override
    public <E> Set pickValue(List<E> list, PickConfig config, String... parameter) throws Exception {

        if (null == list || 0 == list.size()) return null;
        Set set = (Set) batch(list,
                config.setReturnStyleEnum(PickBase.ReturnStyleEnum.SET), parameter);
        PickConfig.reset();
        return set;
    }

    /**
     * tips 单个对象取出特定字段
     *
     * @parameter: E e
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/4/30 15:49
     */
    @Override
    public <E> Map pickValue(E obj, String... key) throws Exception {
        if (null == obj) return null;
        List<E> list = new ArrayList<>();
        list.add(obj);
        Map map = ((List<Map>) batch(list, new PickConfig(
                PickBase.ReturnStyleEnum.MAP,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), key)).get(0);
        PickConfig.reset();
        return map;
    }

    /**
     * tips 单个对象 返回选定字段(带控制返回)
     *
     * @parameter: E e
     * @parameter: PickConfig config
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/4/30 15:49
     */
    @Override
    public <E> Map pickValue(E e, PickConfig config, String... key) throws Exception {
        if (null == e) return null;
        List<E> list = new ArrayList<>();
        list.add(e);
        Map map = ((List<Map>) (batch(list, config.setReturnStyleEnum(PickBase.ReturnStyleEnum.MAP), key))).get(0);
        PickConfig.reset();
        return map;
    }

    /**
     * tips 从集合中取出特定key
     *
     * @parameter: Map map
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/8/3 17:09
     */
    @Override
    public Map pickMap(Map map, String... key) {

        if (null == map || 0 == map.size()) return null;
        Map maps = batchMap(map, new PickConfig(
                PickBase.ReturnStyleEnum.MAP,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), key);
        PickConfig.reset();
        return maps;
    }

    /**
     * tips 从集合中取出特定Key(带返回控制)
     *
     * @parameter: Map map
     * @parameter: PickConfig config
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/8/3 17:09
     */
    @Override
    public Map pickMap(Map map, PickConfig config, String... parameter) {

        if (null == map || 0 == map.size()) return null;
        Map maps = batchMap(map, config.setReturnStyleEnum(PickBase.ReturnStyleEnum.MAP), parameter);
        PickConfig.reset();
        return maps;
    }

}
