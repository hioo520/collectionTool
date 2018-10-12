package com.collectionTool.pick.core;

import com.collectionTool.pick.PickFactory;
import com.collectionTool.pick.constant.PickBase;
import com.collectionTool.pick.constant.PickConfig;

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

        return (List<Map>) batch(list, new PickConfig(), parameter);
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

        return (List<Map>) batch(list, config, parameter);
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
    public <E> Set<String> pickValue(List<E> list, String... parameter) throws Exception {

        return (Set) batch(list, new PickConfig(
                PickBase.ReturnStyleEnum.SET,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), parameter);
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
    public <E> Set<String> pickValue(List<E> list, PickConfig config, String... parameter) throws Exception {

        return (Set) batch(list,
                config.setReturnStyleEnum(PickBase.ReturnStyleEnum.SET), parameter);
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

        List<E> list = new ArrayList<>();
        list.add(obj);
        return ((List<Map>) batch(list, new PickConfig(
                PickBase.ReturnStyleEnum.MAP,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), key)).get(0);
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

        List<E> list = new ArrayList<>();
        list.add(e);
        return ((List<Map>) (batch(list, config.setReturnStyleEnum(PickBase.ReturnStyleEnum.MAP), key))).get(0);
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

        return batchMap(map, new PickConfig(
                PickBase.ReturnStyleEnum.MAP,
                PickBase.ReturnNameEnum.DEFAULT,
                PickBase.SaveStyleEnum.DEFAULT), key);
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

        return batchMap(map, config.setReturnStyleEnum(PickBase.ReturnStyleEnum.MAP), parameter);
    }

}
