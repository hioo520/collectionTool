package com.collectionTool.fill;


import com.collectionTool.fill.constant.StuffConfig;
import com.collectionTool.fill.core.FillTool;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * tips 填充工具工厂
 *
 * @author: hihuzi 2018/9/23 16:03
 */
public interface FillFactory {

    FillTool FILL_TOOL = null;

    /**
     * tips 获取工具
     *
     * @author: hihuzi 2018/9/23 22:59
     */
    static FillTool batch() {

        if (FILL_TOOL == null) {
            return new FillTool();
        }
        return FILL_TOOL;
    }

    /**
     * tips HttpServletRequest-->MAP
     * 默认 方法一 保存空值
     *
     * @parameter: HttpServletRequest request
     * @return: Map
     * @author: hihuzi 2018/6/14 14:51
     */
    Map fill(HttpServletRequest request);

    /**
     * tips HttpServletRequest-->MAP
     * 方法二 保存空值 并且舍弃str特定字段
     *
     * @parameter: HttpServletRequest request
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/7/23 15:05
     */
    Map fill(HttpServletRequest request, String... key);

    /**
     * tips HttpServletRequest-->MAP
     * 方法三 是否舍弃空值 并且舍弃str特定字段(默认舍弃空值)
     *
     * @parameter: HttpServletRequest
     * @parameter: StuffConfig
     * @return: Map
     * @author: hihuzi 2018/7/23 15:05
     */
    Map fill(HttpServletRequest request, StuffConfig config);

    /**
     * tips HttpServletRequest-->MAP
     * 方法四 是否舍弃空值 并且舍弃str特定字段(默认保存空值)
     *
     * @parameter: HttpServletRequest request
     * @parameter: StuffConfig config
     * @parameter: String[] key
     * @return: Map
     * @author: hihuzi 2018/7/23 15:05
     */

    Map fill(HttpServletRequest request, StuffConfig config, String... key);

    /**
     * tips HttpServletRequest--> obj
     *
     * @parameter: HttpServletRequest request
     * @parameter: E e
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    <E> E fillEntity(HttpServletRequest request, E e) throws Exception;

    /**
     * tips HttpServletRequest--> obj
     *
     * @parameter: HttpServletRequest request
     * @parameter: E e
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    <E> E fillEntity(HttpServletRequest request, E e, StuffConfig config) throws Exception;

    /**
     * tips 对MAP数据装填--> 对象
     *
     * @parameter: Map map
     * @parameter: E e
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    <E> E fillEntity(Map map, E e) throws Exception;

    /**
     * tips 对MAP数据装填--> 对象
     *
     * @parameter: Map map
     * @parameter: E e
     * @return: E
     * @author: hihuzi 2018/6/14 14:50
     */
    <E> E fillEntity(Map map, E e, StuffConfig config) throws Exception;

    /**
     * tips List<Map> --> E --> List<E>
     *
     * @parameter: List<Map> list
     * @parameter: E e
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */

    <E> List<E> fillEntity(List<Map> list, E e) throws Exception;

    /**
     * tips list<String> --> E --> list<E> 针对数据库与实体类名有区别
     *
     * @parameter: List<String> list
     * @parameter: E e
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */
    <E> List<E> fillEntity(List<Map> list, E e, StuffConfig config) throws Exception;

    /**
     * tips E --> Map  针对E的属性属性值填充到map
     *
     * @parameter: E e
     * @parameter: map map
     * @return: map
     * @author: hihuzi 2018/6/26 14:51
     */
    <E> Map fillMap(E e, Map map) throws Exception;

    /**
     * tips E --> Map  针对E的属性属性值填充到map
     *
     * @notice: 属性值为空的舍弃
     * @parameter: E e
     * @parameter: map map
     * @return: map
     * @author: hihuzi 2018/6/26 14:51
     */
    <E> Map fillMap(E e, Map map, StuffConfig config) throws Exception;

    /**
     * tips list<String> --> E --> list<E> 针对数据库与实体类名有区别
     *
     * @parameter: List<String> list
     * @parameter: E e
     * @return: List<E>
     * @author: hihuzi 2018/6/26 14:51
     */

    <E> List<E> listToEntity(List<String> list, E e) throws Exception;

    /**
     * tips E --> Map  针对E的属性属性值填充到map
     *
     * @notice: 属性值为空的舍弃
     * @parameter: E e
     * @parameter: map map
     * @return: map
     * @author: hihuzi 2018/6/26 14:51
     */
    <E> List<E> listToEntity(List<String> list, E e, StuffConfig config) throws Exception;
}
