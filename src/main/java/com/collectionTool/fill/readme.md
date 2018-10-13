**XML 对象填充器**

```
    /**
     * tips HttpServletRequest-->MAP
     * 默认 方法一 保存空值
     *
     * @Author:hihuzi 2018/6/14 14:51
     */
    @Test
    public void parameterFillMap() {

        TestBean bean = new TestBean();
        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        Map map = FillFactory.batch().parameterFillMap(request);
        System.out.println(Arrays.asList(map).toString());
    }
```
#####[{longMax=12.3, intMin=   , doubleMin=, stringMax=你好师姐!!!, integerMax=123456}]
```
    /**
     * tips HttpServletRequest-->MAP
     * 方法二 保存空值 并且舍弃str特定字段
     *
     * @Author:hihuzi 2018/7/23 15:05
     */
    @Test
    public void parameterFillMap1() {

        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        Map map = FillFactory.batch().parameterFillMap(request);
        Map map0 = FillFactory.batch().parameterFillMap(request, "stringMax");
       map.forEach((o, o2) -> System.out.print(o+"="+o2));
        System.out.println("");
       map0.forEach((o, o2) -> System.out.print(o+"="+o2));
    }
```
#####longMax=12.3 intMin=   doubleMin= stringMax=你好师姐!!! integerMax=123456
#####longMax=12.3 intMin=   doubleMin= integerMax=123456
```
  /**
     * tips HttpServletRequest-->MAP
     * 方法三 是否舍弃空值 并且舍弃str特定字段(默认舍弃空值)
     * 方法四 是否舍弃空值 并且舍弃str特定字段(默认保存空值)
     *
     * @Author:hihuzi 2018/7/23 15:05
     */
    @Test
    public void parameterFillMap2() {

        request.setParameter("integerMax", "123456");
        request.setParameter("stringMax", "你好师姐!!!");
        request.setParameter("longMax", "12.3");
        request.setParameter("intMin", "   ");
        request.setParameter("doubleMin", "");
        Map map1 = (Map) FillFactory.batch().parameterFillMap(request, FillConstent.NullWhetherSave);// 舍弃空值
        Map map = (Map) FillFactory.batch().parameterFillMap(request, FillConstent.NullWhetherSave.setValue(1), "longMax");
        Map map0 = FillFactory.batch().parameterFillMap(request, "doubleMin");
        map1.forEach((o, o2) -> System.out.print(o+"="+o2+" "));
        System.out.println("");
        map.forEach((o, o2) -> System.out.print(o+"="+o2+" "));
    System.out.println("");
        map0.forEach((o, o2) -> System.out.print(o+"="+o2+" "));
    }
```
#####longMax=12.3 stringMax=你好师姐!!! integerMax=123456 
#####stringMax=你好师姐!!! integerMax=123456 
#####longMax=12.3 intMin=    stringMax=你好师姐!!! integerMax=123456 
```
    /**
     * tips HttpServletRequest--> obj
     *
     * @Author:hihuzi 2018/6/14 14:50
     */
    @Test
    public void parameterFillEntity() throws Exception {

        request.setParameter("booleanMax", "true");
        request.setParameter("byteMax", "1");
        request.setParameter("shortMax", "129");
        request.setParameter("integerMax", "123456");
        request.setParameter("longMax", "132542435");
        request.setParameter("floatMax", "12.99");
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
        TestBean map1 = FillFactory.batch().parameterFillEntity(request, new TestBean());
        System.out.println(Arrays.asList(map1).toString());
    }
```
#####[TestBean(booleanMax=true, byteMax=1, shortMax=129, integerMax=123456, longMax=132542435, floatMax=12.99, doubleMax=3.55, stringMax=你好师姐!!!, bigdecimalMax=9825485.61551, dateMax=Wed Dec 12 00:00:00 CST 2012, booleanMin=true, charMin=a, byteMin=2, shortMin=5, intMin=55, longMin=555, floatMin=0.9, doubleMin=1.94)]
```
    @Test
    public void mapFillEntity() throws Exception {

        Map map = new HashMap();
        map.put("booleanMax", "true");
        map.put("byteMax", "1");
        map.put("shortMax", "129");
        map.put("integerMax", "123456");
        map.put("longMax", "132542435");
        map.put("floatMax", "12.99");;
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
        TestBean bean = FillFactory.batch().mapFillEntity(map, new TestBean());
        System.out.println(bean.toString());
    }
```
#####TestBean(booleanMax=true, byteMax=1, shortMax=129, integerMax=123456, longMax=132542435, floatMax=12.99, doubleMax=3.55, stringMax=你好师姐!!!, bigdecimalMax=9825485.6, dateMax=Wed Dec 12 00:00:00 CST 2012, booleanMin=true, charMin=a, byteMin=2, shortMin=5, intMin=55, longMin=555, floatMin=0.9, doubleMin=1.94)
```
    /**
     * tips List<Map> --> E --> List<E>
     *
     * @Author:hihuzi 2018/6/26 14:51
     */
    @Test
    public void listToEntity() throws Exception {

        List list = new ArrayList();
        Map map = new HashMap();
        map.put("booleanMax", "true");
        map.put("byteMax", "1");
        map.put("shortMax", "129");
        map.put("integerMax", "123456");
        map.put("longMax", "132542435");
        map.put("floatMax", "12.99");;
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
        List<TestBean> bean = FillFactory.batch().listToEntity(list, new TestBean());
        System.out.println(bean.get(0).toString());
    }
````
#####TestBean(booleanMax=true, byteMax=1, shortMax=129, integerMax=123456, longMax=132542435, floatMax=12.99, doubleMax=3.55, stringMax=你好师姐!!!, bigdecimalMax=9825485.6, dateMax=Wed Dec 12 00:00:00 CST 2012, booleanMin=true, charMin=a, byteMin=2, shortMin=5, intMin=55, longMin=555, floatMin=0.9, doubleMin=1.94)
````
    /**
     * tips list<String> --> E --> list<E> 针对数据库与实体类名有区别
     *
     * @Author:hihuzi 2018/6/26 14:51
     */

    @Test
    public void listFillEntity() throws Exception {

        List list = new ArrayList();
        list.add( "true");
        list.add( "1");
        list.add( "129");
        list.add( "123456");
        list.add( "132542435");
        list.add( "12.99");;
        list.add( "3.55");
        list.add( "你好师姐!!!");
        list.add( "9825485.6");
        list.add( "2012-12-12");
        list.add( "true");
        list.add( "a");
        list.add( "2");
        list.add( "5");
        list.add( "55");
        list.add( "555");
        list.add( "0.9");
        list.add( "1.94");
        List<TestBean> bean = FillFactory.batch().listFillEntity(list, new TestBean());
        System.out.println(bean.get(0).toString());
    }
````
#####TestBean(booleanMax=true, byteMax=1, shortMax=129, integerMax=123456, longMax=132542435, floatMax=12.99, doubleMax=3.55, stringMax=你好师姐!!!, bigdecimalMax=9825485.6, dateMax=Wed Dec 12 00:00:00 CST 2012, booleanMin=true, charMin=a, byteMin=2, shortMin=5, intMin=55, longMin=555, floatMin=0.9, doubleMin=1.94)
#加入缓存 一百万 耗时1秒 一千万 耗时30
#引入缓存   ClassCache.get().add( e.getClass(), name,paramtertype);
#调用缓存1.ClassCache.get().add(e.getClass(), name, paramtertype);
#调用缓存2.ClassCache.get().add(e.getClass(), name);