# hihuzi_collection_tool

#### 集合工具提取和填充器
#使用方法-->FillFactory.beach().需要方法()
#使用方法-->PickFactoryTest.beach().需要方法()
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
        /**
         * tips 从对象集合中取特定字段
         *
         * @parameter: List<E> list
         * @parameter: String[] parameter
         * @return: List<Map>
         * @author: hihuzi 2018/7/12 8:03
         */
        <E> List<Map> pick(List<E> list, String... parameter) throws Exception;
    
        /**
         * tips 从对象集合中取特定字段(带控制返回值)
         *
         * @parameter: List<E> list
         * @parameter: PickConfig config
         * @parameter: String[] parameter
         * @return: List<Map>
         * @author: hihuzi 2018/7/12 8:03
         */
        <E> List<Map> pick(List<E> list, PickConfig config, String... parameter) throws Exception;
    
        /**
         * tips 从对象集合中取特定字段的value(带控制返回值)(去重)
         *
         * @parameter: List<E> list
         * @parameter: String[] parameter
         * @return: Set<String>
         * @author: hihuzi 2018/4/30 15:49
         */
        <E> Set pickValue(List<E> list, String... parameter) throws Exception;
    
        /**
         * tips 从对象集合中取特定字段的value(带控制返回值)(去重)
         *
         * @parameter: List<E> list
         * @parameter: PickConfig enmu
         * @parameter: String[] parameter
         * @return: Set<String>
         * @author: hihuzi 2018/4/30 15:49
         */
        <E> Set<String> pickValue(List<E> list, PickConfig config, String... parameter) throws Exception;
    
        /**
         * tips 单个对象取出特定字段
         *
         * @parameter: E e
         * @parameter: String[] key
         * @return: Map
         * @author: hihuzi 2018/4/30 15:49
         */
        <E> Map pickValue(E e, String... key) throws Exception;
    
        /**
         * tips 单个对象 返回选定字段(带控制返回)
         *
         * @parameter: E e
         * @parameter: PickConfig config
         * @parameter: String[] key
         * @return: Map
         * @author: hihuzi 2018/4/30 15:49
         */
        <E> Map pickValue(E e, PickConfig config, String... key) throws Exception;
    
        /**
         * tips 从集合中取出特定key
         *
         * @parameter: Map map
         * @parameter: String[] key
         * @return: Map
         * @author: hihuzi 2018/8/3 17:09
         */
        Map pickMap(Map map, String... key);
    
        /**
         * tips 从集合中取出特定Key(带返回控制)
         *
         * @parameter: Map map
         * @parameter: PickConfig config
         * @parameter: String[] key
         * @return: Map
         * @author: hihuzi 2018/8/3 17:09
         */
        Map pickMap(Map map, PickConfig config, String... key);