package com.collectionTool.fill.test;


import com.collectionTool.cache.ClassCache;
import com.collectionTool.cache.TypeCache;
import com.collectionTool.fill.FillFactory;
import com.collectionTool.fill.constant.StuffConfig;
import com.collectionTool.fill.core.FillTool;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

/**
 * tips
 *
 * @author:hihuzi 2018/7/23 9:21
 */
public class FillFactoryTest {

    private MockHttpServletRequest request;

    private FillFactory fillTool;

    @Before
    public void setUp() {

        request = new MockHttpServletRequest();
        request.setCharacterEncoding("utf-8");
        fillTool = (FillFactory) new FillTool();
    }

    /**
     * tips HttpServletRequest-->MAP
     * 默认 方法一 保存空值
     *
     * @author:hihuzi 2018/6/14 14:51
     */
    @Test
    public void requestFillMap() {

        TestBean bean = new TestBean();
        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        Map map = FillFactory.batch().fill(request);
        System.out.println(Arrays.asList(map).toString());


    }

    /**
     * tips HttpServletRequest-->MAP
     * 方法二 保存空值 并且舍弃str特定字段
     *
     * @author:hihuzi 2018/7/23 15:05
     */
    @Test
    public void requestFillMap1() {

        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        Map map = FillFactory.batch().fill(request);
        Map map0 = FillFactory.batch().fill(request, "stringMax");
        map.forEach((o, o2) -> System.out.print(o + "=" + o2));
        System.out.println("");
        map0.forEach((o, o2) -> System.out.print(o + "=" + o2));
    }

    /**
     * tips HttpServletRequest-->MAP
     * 方法三 是否舍弃空值 并且舍弃str特定字段(默认舍弃空值)
     * 方法四 是否舍弃空值 并且舍弃str特定字段(默认保存空值)
     *
     * @author:hihuzi 2018/7/23 15:05
     */
    @Test
    public void requestFillMap2() {

        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        // tips 舍弃"" 或者 "    " 空值
        Map map1 = FillFactory.batch().fill(request, new StuffConfig(StuffConfig.SaveStyleEnum.DEFAULT));
        Map map = FillFactory.batch().fill(request, new StuffConfig(StuffConfig.SaveStyleEnum.REMOVE_NULL_EMPTY), "stringMax");
        /**tips 舍弃掉doubleMin*/
        Map map0 = FillFactory.batch().fill(request, "doubleMin");
        Map map2 = FillFactory.batch().fill(request);
        System.out.println("");
        System.out.println("舍弃\"\" 或者 \"    \" 空值");
        map1.forEach((o, o2) -> System.out.print(o + "=" + o2 + " "));
        System.out.println("");
        System.out.println("舍弃\"\" 或者 \"    \" 空值并且去掉stringMax");
        map.forEach((o, o2) -> System.out.print(o + "=" + o2 + " "));
        System.out.println("");
        System.out.println("舍弃掉doubleMin");
        map0.forEach((o, o2) -> System.out.print(o + "=" + o2 + " "));
        System.out.println("");
        System.out.println("全部显示");
        map2.forEach((o, o2) -> System.out.print(o + "=" + o2 + " "));
    }

    /**
     * tips HttpServletRequest--> obj
     *
     * @author:hihuzi 2018/6/14 14:50
     */
    @Test
    public void fillEntity0() throws Exception {

        request.setParameter("booleanMax", "");
        request.setParameter("byteMax", "");
        request.setParameter("shortMax", "");
        request.setParameter("integerMax", "");
        request.setParameter("longMax", "");
        request.setParameter("floatMax", "");
        request.setParameter("doubleMax", "");
        request.setParameter("stringMax", "");
        request.setParameter("bigdecimalMax", "");
        request.setParameter("dateMax", "");
        request.setParameter("booleanMin", "");
        request.setParameter("charMin", "");
        request.setParameter("byteMin", "");
        request.setParameter("shortMin", "");
        request.setParameter("intMin", "");
        request.setParameter("longMin", "");
        request.setParameter("floatMin", "");
        request.setParameter("doubleMin", "");
        long start = System.currentTimeMillis();
        TestBean map1 = null;
        for (int i = 0; i < 1000000; i++) {
            map1 = FillFactory.batch().fillEntity(request, new TestBean());
        }
        long end = System.currentTimeMillis();
        System.err.println("一百万 耗时" + (end - start) / 1000);
        System.out.println(Arrays.asList(map1).toString());
    }

    /**
     * tips HttpServletRequest--> obj
     *
     * @author:hihuzi 2018/6/14 14:50
     */
    @Test
    public void fillEntity2() throws Exception {

        request.setParameter("booleanMax", "true");
        request.setParameter("byteMax", "1");
        request.setParameter("shortMax", "129");
        request.setParameter("integerMax", "123456");
        request.setParameter("longMax", "132542435");
        request.setParameter("floatMax", "12.9");
        request.setParameter("doubleMax", "3.55");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("bigdecimalMax", "9825485.61551");
        request.setParameter("dateMax", "2012-12-12");
        request.setParameter("booleanMin", "true");
        request.setParameter("charMin", "a");
        request.setParameter("byteMin", "2");
        request.setParameter("shortMin", "5");
        request.setParameter("intMin", "55");
        request.setParameter("longMin", "555");
        request.setParameter("floatMin", "0.9");
        request.setParameter("doubleMin", "1.94");
        TestBean map1 = null;
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {

        map1 = FillFactory.batch().fillEntity(request, new TestBean());
//        }
        long end = System.currentTimeMillis();
        System.err.println("一千万 耗时" + (end - start) / 1000);

        System.out.println(Arrays.asList(map1).toString());
    }

    @Test
    public void fillEntity1() throws Exception {

        Map map = new HashMap(20);
        map.put("booleanMax", "true");
        map.put("byteMax", "1");
        map.put("shortMax", "129");
        map.put("integerMax", "123456");
        map.put("longMax", "132542435");
        map.put("floatMax", "12.99");
        map.put("stringMax", "你好师姐!!!");
        map.put("bigdecimalMax", "9825485.6");
        map.put("dateMax", "2012-12-12");
        map.put("booleanMin", "true");
        map.put("charMin", "a");
        map.put("byteMin", "2");
        map.put("shortMin", "5");
        map.put("intMin", "55");
        map.put("longMin", "555");
        map.put("floatMin", "0.9");
        map.put("doubleMin", "1.94");
        TestBean bean = FillFactory.batch().fillEntity(map, new TestBean());
        Map map1 = new HashMap();
        /**tips 从对象中取出map*/
        Map map2 = FillFactory.batch().fillMap(bean, map1);
        System.out.println(bean.toString());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        map2.forEach((o, o2) -> System.out.print(o + " " + o2));
    }

    /**
     * tips List<Map> --> E --> List<E>
     *
     * @author:hihuzi 2018/6/26 14:51
     */
    @Test
    public void fillEntity() throws Exception {

        List list = new ArrayList();
        Map map = new HashMap(20);
        map.put("booleanMax", "true");
        map.put("byteMax", "1");
        map.put("shortMax", "129");
        map.put("integerMax", "123456");
        map.put("longMax", "132542435");
        map.put("floatMax", "12.99");
        ;
        map.put("doubleMax", "3.55");
        map.put("stringMax", "你好师姐!!!");
        map.put("bigdecimalMax", "9825485.6");
        map.put("dateMax", "2012-12-12");
        map.put("booleanMin", "true");
        map.put("charMin", "a");
        map.put("byteMin", "2");
        map.put("shortMin", "5");
        map.put("intMin", "55");
        map.put("longMin", "555");
        map.put("floatMin", "0.9");
        map.put("doubleMin", "1.94");
        list.add(map);
        List<TestBean> bean = FillFactory.batch().fillEntity(list, new TestBean());
        System.out.println(bean.get(0).toString());
    }

    /**
     * tips list<String> --> E --> list<E> 针对数据库与实体类名有区别
     *
     * @author:hihuzi 2018/6/26 14:51
     */

    @Test
    public void listToEntity() throws Exception {

        List list = new ArrayList();
        list.add("true");
        list.add("1");
        list.add("129");
        list.add("123456");
        list.add("132542435");
        list.add("12.99");
        list.add("3.55");
        list.add("你好师姐!!!");
        list.add("9825485.6");
        list.add("2012-12-12");
        list.add("true");
        list.add("a");
        list.add("2");
        list.add("5");
        list.add("55");
        list.add("555");
        list.add("0.9");
        list.add("1.94");
        List<TestBean> bean = FillFactory.batch().listToEntity(list, new TestBean());
        System.out.println(bean.get(0).toString());
    }

    /**
     * tips E --> Map  针对E与map进行填充
     *
     * @parameter: E e
     * @parameter: Map map
     * @parameter: E
     * @Author: hihuzi 2018/6/26 14:51
     */

    @Test
    public void fillMap() throws Exception {

        List list = new ArrayList();
        list.add("true");
        list.add("1");
        list.add("129");
        list.add("123456");
        list.add("132542435");
        list.add("12.99");
        list.add("3.55");
        list.add("你好师姐!!!");
        list.add("9825485.6");
        list.add("2012-12-12");
        list.add("true");
        list.add("a");
        list.add("2");
        list.add("5");
        list.add("55");
        list.add("555");
        list.add("0.9");
        list.add("1.94");
        List<TestBean> bean = FillFactory.batch().listToEntity(list, new TestBean());
        System.out.println(bean.get(0).toString());
        Map map = FillFactory.batch().fillMap(bean.get(0), new HashMap(1));
        System.out.println(map.toString());
        Map<String, Map<String, TypeCache>> classCache = ClassCache.cache;
        classCache.forEach((s, typeCache) -> System.err.println(typeCache.size()));
    }

}