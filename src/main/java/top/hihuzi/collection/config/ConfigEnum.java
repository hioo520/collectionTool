package top.hihuzi.collection.config;

import top.hihuzi.collection.sql.config.SQLBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * tips 规则菜单
 *
 * @author: hihuzi 2019/2/14 9:51
 */
public interface ConfigEnum<E> {

    /**
     * tips: 时间规则定制
     *
     * @notice:默认 yyyy-MM-dd
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum DateStyleEnum {
        /**
         * tips  默认时间风格规则
         */
        DEFAULT("");

        private String value;

        /**
         * tips 多线程并发时启用
         */
        public static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>();

        DateStyleEnum(String value) {

            this.value = value;
        }

        public SimpleDateFormat getFormartStyle() {

            return dateFormat.get();
        }

        public DateStyleEnum setFormartStyle(String formartStyle) {

            SimpleDateFormat format = dateFormat.get();
            if (null == format || !formartStyle.equals(format.toPattern())) {
                dateFormat.set(new SimpleDateFormat(formartStyle));
            }
            return this;
        }
    }

    /**
     * tips: 排序规则定制
     *
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum SortStyleEnum {
        /**
         * 默认排序规则
         */
        DEFAULT(new Integer[]{});

        private Integer[] sort;

        SortStyleEnum(Integer[] sort) {

            this.sort = sort;
        }

        public Integer[] getSort() {

            return sort;
        }

        public SortStyleEnum setSort(Integer[] sort) {

            this.sort = sort;
            return this;
        }
    }

    /**
     * tips: 返回值(value)规则
     *
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum SaveStyleEnum {
        /**
         * 所有都保存
         */
        DEFAULT(true),

        /**
         * tips: 对 (null or "")不处理 都保存
         *
         * @notice: NULL
         * @author: hihuzi 2018/9/29 17:21
         **/
        REMOVE_NULL_EMPTY(false);

        private Boolean isHaving;

        SaveStyleEnum(Boolean having) {

            this.isHaving = having;
        }

        /**
         * tips: 判断是否存在Null empty "" "   "
         *
         * @author: hihuzi 2018/9/30 8:52
         **/
        public Boolean getHaving() {

            return isHaving;
        }
    }


    /**
     * tips: 返回数据泛型类型
     *
     * @notice: 通用枚举
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum ReturnEnum {
        /**
         * 默认和LIST返回一致
         */
        DEFAULT(null),
        /**
         * 返回"list<map>"
         */
        LISR(null),
        /**
         * 返回"map<e.getClass().getSimpleName(),list<E>>"
         */
        MAP(null),
        /**
         * 返回 根据传入的list进行填充
         */
        FILL_LIST(null),

        /**
         * 返回 根据传入的list进行填充成单个对象
         */
        FILL_CLASS(null);


        private List[] list;

        ReturnEnum(List[] list) {

            this.list = list;
        }

        /**
         * @author: hihuzi 2018/9/30 8:52
         **/
        public List[] getList() {

            return list;
        }

        public ReturnEnum setList(List... list) {

            this.list = list;
            return this;
        }}

    /**
     * tips: 返回类型枚举
     *
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum ReturnStyleEnum {
        /**
         * value=1: 返回类型:List<Map>
         *
         * @author: hihuzi
         */
        DEFAULT,
        /**
         * value=0: 返回类型:List<Map>
         *
         * @author: hihuzi
         */
        LIST_MAP,
        /**
         * value=2: 返回类型:Map
         *
         * @author: hihuzi
         */
        MAP,
        /**
         * value=3: 返回类型:Set
         *
         * @author: hihuzi
         */
        SET;

    }


    /**
     * tips: 自定义返回Key
     *
     * @notice: 通用枚举
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum ReturnNameEnum {
        /**
         * RenameKey="0":默认 属性名输出
         * <p>
         * RenameKey="XXXX":义可以的自定头缀
         *
         * @author: hihuzi
         */
        DEFAULT("0"),
        /**
         * RenameKey="1":首字母大写
         *
         * @author: hihuzi
         */
        INITIAL_CAPITAL("1"),
        /**
         * RenameKey="2":全小写
         *
         * @author: hihuzi
         */
        LOWER_CASE("2"),
        /**
         * RenameKey="3":全大写
         *
         * @author: hihuzi
         */
        UPPER_CASE("3"),
        /**
         * RenameKey="XXXX":义可以的自定头缀
         *
         * @author: hihuzi
         */
        CUSTOM_SUFFIX("");

        private String key;

        ReturnNameEnum(String key) {

            this.key = key;
        }

        public String getKey() {

            return key;
        }

        public ReturnNameEnum setKey(String key) {

            this.key = key;
            return this;
        }
    }

    /**
     * tips SQL+ 规则配置
     *
     * @author: hihuzi 2019/2/15 10:04
     */
    enum SQLEeum {
        DEFAULT(null);

        private SQLBean bean;

        SQLEeum(SQLBean bean) {

            this.bean = bean;
        }

        public SQLEeum set(SQLBean bean) {

            this.bean = bean;
            return this;
        }

    }


}
/**
 * tips SQL+ 规则配置
 *
 * @author: hihuzi 2019/2/15 10:04
 * <p>
 * tips 唯一性 用于此条SQL 缓存(不设置存在数据紊乱(情况:出现同样检索只是SQL不同时缓存的SQL容易被覆盖))
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 待查询的class对象
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 名称的表昵称 提取表的昵称作为 属性的前缀
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 存在重复属性(也就是重复的列名)
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 需要检索出来的属性(也就是待显示的列名)
 * @notice: 没有配置时 是查找全部
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 唯一性 用于此条SQL 缓存(不设置存在数据紊乱(情况:出现同样检索只是SQL不同时缓存的SQL容易被覆盖))
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 待查询的class对象
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 名称的表昵称 提取表的昵称作为 属性的前缀
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 存在重复属性(也就是重复的列名)
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 需要检索出来的属性(也就是待显示的列名)
 * @notice: 没有配置时 是查找全部
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 唯一性 用于此条SQL 缓存(不设置存在数据紊乱(情况:出现同样检索只是SQL不同时缓存的SQL容易被覆盖))
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 待查询的class对象
 * @author: hihuzi 2019/2/15 10:13
 * <p>
 * tips 名称的表昵称 提取表的昵称作为 属性的前缀
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 存在重复属性(也就是重复的列名)
 * @author: hihuzi 2019/2/15 10:15
 * <p>
 * tips 需要检索出来的属性(也就是待显示的列名)
 * @notice: 没有配置时 是查找全部
 * @author: hihuzi 2019/2/15 10:15
 *//*

enum SQLBeanEnum {

    */
/**
 * tips 唯一性 用于此条SQL 缓存(不设置存在数据紊乱(情况:出现同样检索只是SQL不同时缓存的SQL容易被覆盖))
 *
 * @author: hihuzi 2019/2/15 10:13
 *//*

    UNIQUE(null),
    */
/**
 * tips 待查询的class对象
 *
 * @author: hihuzi 2019/2/15 10:13
 *//*

    CLASS(null),

    */
/**
 * tips 名称的表昵称 提取表的昵称作为 属性的前缀
 *
 * @author: hihuzi 2019/2/15 10:15
 *//*

    NICKNAME(null),

    */
/**
 * tips 存在重复属性(也就是重复的列名)
 *
 * @author: hihuzi 2019/2/15 10:15
 *//*

    REPEAT(null),
    */
/**
 * tips 需要检索出来的属性(也就是待显示的列名)
 *
 * @notice: 没有配置时 是查找全部
 * @author: hihuzi 2019/2/15 10:15
 *//*

    DISPLAY(null);

    private String[] key;

    SQLBeanEnum(String... key) {

        this.key = key;
    }

    public Map getMap() {

        Map map = new HashMap();
        Class[] clazz = new Class[CLASS.key.length];
        Map nickname = new HashMap(CLASS.key.length);
        for (int i = 0; i < CLASS.key.length; i++) {
            try {
                clazz[i] = Class.forName(SQLBeanEnum.CLASS.key[i]);
                nickname.put(SQLBeanEnum.CLASS.key[i], SQLBeanEnum.NICKNAME.key[i]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        List unique = Arrays.asList(UNIQUE.key);
        List repeat = Arrays.asList(REPEAT.key);
        List display = Arrays.asList(DISPLAY.key.length);
        map.put(UNIQUE, unique);
        map.put(CLASS, clazz);
        map.put(NICKNAME, nickname);
        map.put(REPEAT, repeat);
        map.put(DISPLAY, display);
        return map;

    }

    public String[] get() {

        return key;
    }

    public SQLBeanEnum set(String... key) {

        this.key = key;
        return this;
    }

    public <E> SQLBeanEnum set(E... e) {

        String[] key = new String[e.length];
        for (int i = 0; i < e.length; i++) {
            key[i] = e[i].getClass().getName();
        }
        this.key = key;
        return this;
    }

}
*/
