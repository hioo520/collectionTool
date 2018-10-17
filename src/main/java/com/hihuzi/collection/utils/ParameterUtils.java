package com.hihuzi.collection.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * tips 通用参数处理 paramter(K v)-->转换成 entity
 *
 * @notice: 没有缓冲效率比较低(可以用pick 或者fill 工具
 * @author: hihuzi  2018/6/14 10:18
 */
public class ParameterUtils {

    private final static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * tips 对请求进行参数进行装填-->MAP  空值不保存
     *
     * @author: hihuzi 2018/6/14 14:51
     */
    public static Map stuff(HttpServletRequest request) {

        Map parameter = new HashMap(10);
        Enumeration pars = request.getParameterNames();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement().toString().trim();
            String value = request.getParameter(name);
            if (value != null || "".equals(value)) {
                parameter.put(name, value);
            }

        }
        return parameter;
    }

    /**
     * tips 对请求进行参数进行装填--> 对象 空值不保存
     *
     * @author: hihuzi 2018/6/14 14:50
     */
    public static <E> E stuffEntity(HttpServletRequest request, E e) throws Exception {

        Enumeration pars = request.getParameterNames();
        Class<E> clazz = (Class<E>) e.getClass();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement().toString().trim();
            String value = request.getParameter(name);
            if (value != null || "".equals(value)) {
                injectionParameters(e, name, value);
            }
        }
        return e;
    }

    /**
     * tips 对MAP数据装填--> 对象 空值不保存
     *
     * @author: hihuzi 2018/6/14 14:50
     */
    public static <E> E stuffEntity(Map<String, String> maps, E e) throws Exception {

        Iterator iterator = maps.entrySet().iterator();
        Class<E> clazz = (Class<E>) e.getClass();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (value != null || "".equals(value.trim())) {
                injectionParameters(e, name, value);
            }
        }
        return e;
    }

    /**
     * tips tips 对MAP数据装填--> 对象  空值不保存
     *
     * @author: hihuzi 2018/6/14 14:50
     */
    public static <E> E stuffEntityDiffs(Map map, E e) throws Exception {

        Iterator iterator = map.entrySet().iterator();
        Class<E> clazz = (Class<E>) e.getClass();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (value != null || "".equals(value.trim())) {
                injectionParameters(e, name, value);
            }
        }
        return e;
    }

    /**
     * tips 随机填充
     *
     * @author: hihuzi 2018/6/23 9:28
     */
    public static <E> E stuffEntity(E e) throws Exception {

        Class<E> clazz = (Class<E>) e.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String name = String.valueOf(field.getName());
            String value = String.valueOf(UUID.randomUUID().toString().substring(0, 3));
            injectionParameters(e, name, value);
        }
        return e;
    }

    /**
     * tips  公共方法 公共注入参数
     * (针对继承的属性处理)
     *
     * @author: hihuzi 2018/6/22 9:22
     */
    private static <E> void injectionParameters(E e, String name, String value) throws Exception {

        StringBuffer setName = new StringBuffer();
        setName.append("set").append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
        Class<?> paramtertype = null;
        Class<?> clazz = e.getClass();
        try {
            paramtertype = e.getClass().getDeclaredField(name).getType();
        } catch (NoSuchFieldException ee) {
        }
        if (null != paramtertype) {
            setParameter((E) e, name, value, paramtertype);
        } else {
            for (; clazz != Object.class; clazz = (Class<E>) e.getClass().getSuperclass()) {
                try {
                    paramtertype = e.getClass().getDeclaredField(name).getType();
                } catch (NoSuchFieldException ee) {
                }
                if (null != paramtertype) {
                    setParameter(e, name, value, paramtertype);
                } else {
                    System.out.println("当前对象中无此属性" + e.getClass());
                }
            }
        }
    }

    /**
     * tips: 公共方法 对对象进行注入处理value
     *
     * @parameter: E e,
     * @parameter: Method method,
     * @parameter: String value,
     * @parameter: FillConstent enums,
     * @parameter: TypeEnum typeEnum
     * @auther: hihuzi 2018/9/26 9:18
     **/
    private static <E> void setParameter(E e, String name, String value, Class<?> paramtertype) throws Exception {

        Method method = e.getClass().getMethod(name.toString(), paramtertype);
        ParameterUtils.TypeEnum typeEnum = achieveTypeEnum(paramtertype.getSimpleName());
        if (value != null || !"".equals(value)) {
            switch (typeEnum) {
                case STRING:
                    method.invoke(e, String.valueOf(value));
                    break;
                case DATE:
                    if (DateUtils.isDate(value)) {
                        method.invoke(e, DATEFORMAT.parse(value));
                    } else {
                        System.err.println("日期处理错误[ YYYY-MM-DD ] 不能处理 [ " + value + " ] 请转换");
                    }
                    break;
                case CHAR:
                    method.invoke(e, value.toCharArray()[0]);
                    break;
                case BYTE:
                    method.invoke(e, Byte.valueOf(value));
                    break;
                case BYTE_MIN:
                    method.invoke(e, Byte.valueOf(value));
                    break;
                case LONG:
                    method.invoke(e, Long.parseLong(value));
                    break;
                case LONG_MIN:
                    method.invoke(e, Long.parseLong(value));
                    break;
                case SHORT:
                    method.invoke(e, Short.parseShort(value));
                    break;
                case SHORT_MIN:
                    method.invoke(e, Short.parseShort(value));
                    break;
                case FLOAT:
                    method.invoke(e, Float.parseFloat(value));
                    break;
                case FLOAT_MIN:
                    method.invoke(e, Float.parseFloat(value));
                    break;
                case DOUBLE:
                    method.invoke(e, Double.parseDouble(value));
                    break;
                case DOUBLE_MIN:
                    method.invoke(e, Double.parseDouble(value));
                    break;
                case INTEGER:
                    method.invoke(e, Integer.parseInt(value));
                    break;
                case INT:
                    method.invoke(e, Integer.parseInt(value));
                    break;
                case BOOLEAN:
                    method.invoke(e, Boolean.parseBoolean(value));
                    break;
                case BOOLEAN_MIN:
                    method.invoke(e, Boolean.parseBoolean(value));
                    break;
                case BIGDECIMAL:
                    method.invoke(e, new BigDecimal(value));
                    break;
                default:
                    System.out.println("类型错误" + typeEnum.toString());
            }
        } else {
            switch (typeEnum) {
                case STRING:
                    method.invoke(e, value);
                    break;
                case INT:
                    method.invoke(e, 0);
                    break;
                case FLOAT_MIN:
                    method.invoke(e, 0);
                    break;
                case LONG_MIN:
                    method.invoke(e, 0);
                    break;
                case DOUBLE_MIN:
                    method.invoke(e, 0);
                    break;
                case BOOLEAN_MIN:
                    method.invoke(e, false);
                    break;
                case SHORT_MIN:
                    method.invoke(e, (short) 0);
                    break;
                case BYTE_MIN:
                    method.invoke(e, Byte.parseByte("0"));
                    break;
                case CHAR:
                    break;
                default:
                    method.invoke(e, new Object[]{null});
                    break;
            }
        }
    }

    /**
     * tips 获取数据类型所对应的枚举类型
     *
     * @param:
     * @return:
     * @author: hihuzi 2018/9/26 12:55
     */
    private static TypeEnum achieveTypeEnum(String fieldType) {

        TypeEnum[] values = TypeEnum.values();
        for (TypeEnum value : values) {
            if (value.value.equals(fieldType)) {
                return value;
            }
        }
        return null;
    }

    /**
     * tips HttpServletRequest-->MAP    是否舍弃空值 并且舍弃str特定字段
     *
     * @author: hihuzi 2018/6/14 14:51
     */
    public Map stuff(HttpServletRequest request, String... str) {

        Map map = new HashMap(5);
        List<String> exclude = null;
        if (StrUtils.isNNoE(str)) {
            exclude = Arrays.asList(str);
        }
        Enumeration pars = request.getParameterNames();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement().toString();
            String value = request.getParameter(name);
            if (StrUtils.isNNoEC(exclude)) {
                if (!exclude.contains(name)) {
                    if (StrUtils.isNNoE(value)) {
                        map.put(name, value);
                    }
                }
            } else {
                if (StrUtils.isNNoE(value)) {
                    map.put(name, value);
                }

            }
        }
        return map;
    }

    /**
     * tips 数据类型转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author:hihuzi 2018/9/24 19:30
     */
    public enum TypeEnum {
        /**
         * 定义各种数据类型
         */
        BOOLEAN(1, "Boolean"), BYTE(2, "Byte"), SHORT(3, "Short"),
        INTEGER(4, "Integer"), LONG(5, "Long"), FLOAT(6, "Float"),
        DOUBLE(7, "Double"), STRING(8, "String"), BIGDECIMAL(9, "BigDecimal"),
        DATE(10, "Date"), CHAR(11, "char"), INT(12, "int"),
        BOOLEAN_MIN(13, "boolean"), BYTE_MIN(14, "byte"), SHORT_MIN(15, "short"),
        LONG_MIN(16, "long"), FLOAT_MIN(17, "float"), DOUBLE_MIN(18, "double");

        private Integer key;

        private String value;

        TypeEnum(Integer key, String value) {

            this.key = key;
            this.value = value;

        }
    }

}