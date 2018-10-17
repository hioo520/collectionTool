package com.collectionTool.pick.test;

import com.collectionTool.cache.ClassCache;
import com.collectionTool.cache.TypeCache;
import com.collectionTool.pick.PickFactory;
import com.collectionTool.pick.constant.PickBase;
import com.collectionTool.pick.constant.PickConfig;
import org.junit.Test;

import java.util.*;

/**
 * tips 测试工具
 *
 * @author: hihuzi 2018/7/20 8:42
 */
public class PickFactoryTest {


    @Test
    public void pick() throws Exception {

        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            TestBean userPost = new TestBean();
            userPost.setName("你好师姐");
            userPost.setId(12345 * i + "");
            userPost.setEmail(null);
            userPost.setAddress("    ");
            list.add(userPost);
        }
         /**tips 默认转态*/
        List<Map> batch0 = PickFactory.batch().pick(list, "id", "name", "email", "address");
        batch0.forEach(map -> System.out.println(map));
         /**tips 和 默认一样 首字母大写*/
        List<Map> batch = PickFactory.batch().pick(list, new PickConfig(
                PickBase.ReturnNameEnum.INITIAL_CAPITAL), "id", "name", "email", "date");
        batch.forEach(map -> System.out.println(map));
/**         tips 空值丢掉(null 或者 "" "  ") 并且全部大写*/

        List<Map> batch3 = PickFactory.batch().pick(list, new PickConfig(
                PickBase.ReturnNameEnum.UPPER_CASE,
                PickBase.SaveStyleEnum.REMOVE_NULL_EMPTY), "id", "name", "email", "date");
        batch3.forEach(map -> System.out.println(map));
         /**tips 空值不丢掉 并且全部小写*/
        List<Map> batch2 = PickFactory.batch().pick(list, new PickConfig(
                PickBase.ReturnNameEnum.LOWER_CASE), "id", "name", "email", "date", "address");
        batch2.forEach(map -> System.out.println(map));

         /**tips 空值不丢掉 重新命名Key*/
        List<Map> batch4 = PickFactory.batch().pick(list, new PickConfig(
                PickBase.ReturnNameEnum.CUSTOM_SUFFIX.setKey("我就是我!!")), "id", "name", "email", "date", "address");
        batch4.forEach(map -> System.out.println(map));

        /**tips 时间格式化*/
        List<Map> batch5 = PickFactory.batch().pick(list, new PickConfig(
                PickBase.DateStyleEnum.DEFAULT.setFormartStyle("yyyy-MM-dd")), "date");
        batch5.forEach(map -> System.out.println(map));

    }

    /**
     * tips 同一对象集合 返回选定字段 返回value(去重)
     *
     * @author: hihuzi 2018/4/30 15:49
     */
    @Test
    public void pickValue() throws Exception {

        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestBean userPost = new TestBean();
            userPost.setName("你好师姐" + i);
            userPost.setId(12345 * i + "");
            userPost.setEmail(null);
            userPost.setAddress("    ");
            list.add(userPost);
        }
         /**tips 默认设置*/
        Set batch1 = PickFactory.batch().pickValue(list, "id", "name", "email");
        System.out.println(Arrays.asList(batch1).toString());
        /**tips (去掉 NUll 和 "" or "      ")*/
        Set batch = PickFactory.batch().pickValue(list, new PickConfig(
                PickBase.SaveStyleEnum.REMOVE_NULL_EMPTY), "id", "name", "email", "address");
        System.out.println(Arrays.asList(batch).toString());

    }

    /**
     * tips 单个对象 返回选定字段
     *
     * @author: hihuzi 2018/4/30 15:49
     */
    @Test
    public void pickSingle() throws Exception {

        TestBean bean = new TestBean();
        bean.setName("你好师姐");
        bean.setId(UUID.randomUUID().toString());
        bean.setEmail("");
        bean.setAddress(UUID.randomUUID().toString().substring(32) + "@163.com");
         /**tips 默认 保留 空值*/
        Map batch0 = PickFactory.batch().pickValue(bean, "id", "name", "email", "date", "address");
        System.out.println(batch0.toString());
         /**tips 保留 空值*/
        Map batch1 = PickFactory.batch().pickValue(bean, new PickConfig(
                PickBase.ReturnNameEnum.DEFAULT), "id", "name", "email", "date", "address");
        System.out.println(batch1.toString());
         /**tips 舍弃 空值*/
        Map batch = PickFactory.batch().pickValue(bean, new PickConfig(
                PickBase.SaveStyleEnum.REMOVE_NULL_EMPTY), "id", "name", "email", "date", "address");
        System.out.println(batch.toString());
        Map<String, Map<String, TypeCache>> classCache = ClassCache.cache;
        classCache.forEach((s, typeCacheMap) -> System.err.println(typeCacheMap.size()));
    }

    /**
     * tips 单个对象 返回选定字段
     *
     * @author: hihuzi 2018/4/30 15:49
     */
    @Test
    public void pickMap() throws Exception {

        Map bean = new HashMap();
        bean.put("id", UUID.randomUUID());
        bean.put("name", "你好师姐");
        bean.put("age", "");
        bean.put("email", "54465@163.com");
        /**tips 默认 保留 空值*/
        Map batch0 = PickFactory.batch().pickMap(bean, "id", "name", "email", "age");
        System.out.println(batch0.toString());
        /**tips 保留 空值*/
        Map batch1 = PickFactory.batch().pickMap(bean, new PickConfig(
                PickBase.ReturnNameEnum.DEFAULT), "id", "name", "email", "age");
        System.out.println(batch1.toString());
        /**tips 舍弃 空值*/
        Map batch = PickFactory.batch().pickMap(bean, new PickConfig(
                PickBase.SaveStyleEnum.REMOVE_NULL_EMPTY), "id", "name", "email", "age");
        System.out.println(batch.toString());
        Map<String, Map<String, TypeCache>> classCache = ClassCache.cache;
        classCache.forEach((s, typeCacheMap) -> System.err.println(typeCacheMap.size()));
    }

}