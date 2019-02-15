package com.hihuzi.collection.sql;


import com.hihuzi.collection.fill.TestBean;
import com.hihuzi.collection.fill.TestBeanBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import top.hihuzi.collection.sql.config.SQLConfig;
import top.hihuzi.collection.sql.factory.SQLFactory;
import top.hihuzi.collection.utils.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tips
 *
 * @author:hihuzi 2018/7/23 9:21
 */
public class SQlFactoryTest {

    private MockHttpServletRequest request;

    private static List list = new ArrayList();

    @Before
    public void setUp() {

        request = new MockHttpServletRequest();
        request.setCharacterEncoding("utf-8");
        this.request = request;
        Map map = new HashMap();
        map.put("boo_leanMax", "true");
//        map.put("by_teMax", "1");
//        map.put("shortMax", "129");
//        map.put("integerMax", "123456");
        map.put("longMax", "1");
//        map.put("floatMax", "12.9");
//        map.put("doubleMax", "3.55");
//        map.put("string_Max", "你好师姐!!!");
//        map.put("bigdecimalMax", "9825485.61551");
//        map.put("dateMax", "2012-12-12");
//        map.put("booleanMin", "true");
//        map.put("charMin", "a");
//        map.put("byteMin", "2");
//        map.put("shortMin", "5");
//        map.put("intMin", "55");
//        map.put("longMin", "555");
//        map.put("flo_atMin", "0.9");
//        map.put("doubleMin", "1.94");
        map.put("i_d", "ID_ID-ID-ID");
        for (int i = 0; i < 10000000; i++) {
            list.add(map);
        }
    }

    /**
     * tips HttpServletRequest--> obj
     *
     * @author:hihuzi 2018/6/14 14:50
     */
    @Test
    public void listToEntity() throws Exception {

        SQLConfig config = new SQLConfig();
        config.getSqlEeum()
                .UNIQUE.set(StrUtils.splicingObjectName(new TestBean(), new TestBeanBean()), "007")
                .NICKNAME.set("tb", "bb")
                .REPEAT.set("longMax")
                .DISPLAY.set("longMax");

        Object sql = SQLFactory.batch().config(config);
        List<Map> map0 = (List<Map>) SQLFactory.batch().listToEntity(list,config);



        long start = System.currentTimeMillis();
        System.out.println("测试 ---> 第一种返回结果是List<Map>");
        List<Map> map1 = (List<Map>) SQLFactory.batch().listToEntity(list,
                new TestBean(), new TestBeanBean());
        List<Map> map2 = (List<Map>) SQLFactory.batch().listToEntity(list,
                new SQLConfig(SQLConfig.ReturnEnum.LISR),
                new TestBean(), new TestBeanBean());
        List<Map> map3 = (List<Map>) SQLFactory.batch().listToEntity(list,
                new SQLConfig(SQLConfig.ReturnEnum.DEFAULT, SQLConfig.DateStyleEnum.DEFAULT.setFormartStyle("yyyy")),
                new TestBean(), new TestBeanBean());
        System.out.println("测试 ---< 第一种返回结果是List<Map>");


        System.out.println("测试 ---< 第二种返回结果是Map<String, List>");
        Map<String, List> map4 = (Map<String, List>) SQLFactory.batch().listToEntity(list,
                new SQLConfig(SQLConfig.ReturnEnum.MAP),
                new TestBean(), new TestBeanBean());
        System.out.println("测试 ---< 第二种返回结果是Map<String, List>");


        System.out.println("测试 ---< 第三种返回结果是Map<String, List>");
        List<TestBean> testBeans = new ArrayList<>();
        List<TestBeanBean> testBeanBean = new ArrayList<>();
        SQLFactory.batch().listToEntity(list,
                new SQLConfig(SQLConfig.ReturnEnum.FILL_LIST.setList(testBeans, testBeanBean)),
                new TestBean(), new TestBeanBean());
        System.out.println("测试 ---< 第三种返回结果是Map<String, List>");


        System.out.println("测试 ---< 第四种返回结果是Map<String, List>");
        List<TestBean> bean = (List<TestBean>) SQLFactory.batch().listToEntity(list,
                new SQLConfig(SQLConfig.ReturnEnum.FILL_CLASS), new TestBean());
        System.out.println("测试 ---< 第四种返回结果是Map<String, List>");
        long end = System.currentTimeMillis();
        System.err.println("------>一千万 耗时" + (end - start) / 1000 + "秒<------");
    }


}