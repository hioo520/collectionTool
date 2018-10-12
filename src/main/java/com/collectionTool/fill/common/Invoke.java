package com.collectionTool.fill.common;

import com.collectionTool.cache.ClassCache;
import com.collectionTool.fill.constant.StuffConfig;
import com.collectionTool.utils.Constants;
import com.collectionTool.utils.DateUtils;
import com.collectionTool.utils.StrUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * tips 对对象反射调用set方法配置数据
 *
 * @author: hihuzi 2018/7/19 10:29
 */

public class Invoke {


    /**
     * 遍历父类 所有 获取属性
     *
     * @parameter: E obj
     * @parameter: String names
     * @parameter: String value
     * @parameter: StuffConfig config
     * @return:
     * @author: hihuzi 2018/6/22 9:22
     */
    public static <E> void injectionParameters(E obj, String names, String value, StuffConfig config) throws
            Exception {

        Class clazz = obj.getClass();
        Class<?> paramtertype = null;
        try {
            paramtertype = clazz.getDeclaredField(names).getType();
        } catch (NoSuchFieldException e) {
        }
        if (StrUtils.isNNoEE(paramtertype)) {
            putValue(obj, names, value, paramtertype, config);
        } else {
            for (; clazz != Object.class; clazz = (Class<E>) clazz.getSuperclass()) {
                try {
                    paramtertype = clazz.getDeclaredField(names).getType();
                } catch (NoSuchFieldException e) {
                }
                if (StrUtils.isNNoEE(paramtertype)) {
                    putValue(obj, names, value, paramtertype, config);
                }
            }

        }
    }

    /**
     * tips 注入 方法
     *
     * @parameter: E e
     * @parameter: String name
     * @parameter: String value
     * @parameter: Class<?> paramtertype
     * @parameter: StuffConfig config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */

    public static <E> void putValue(E e, String name, String value, Class<?> paramtertype, StuffConfig config) throws Exception {

        if (StrUtils.isNoEE(paramtertype)) {
            paramtertype = e.getClass().getDeclaredField(name).getType();
        }
        Method method = e.getClass().getMethod(StrUtils.achieveSetFunction(name), paramtertype);
        method.setAccessible(true);
        ClassCache.get().add(e.getClass(), name, paramtertype);
        methodInvokeValue(e, method, value, paramtertype.getSimpleName(), config);
    }

    /**
     * tips 注入值
     *
     * @parameter: E e
     * @parameter: String name
     * @parameter: String value
     * @parameter: StuffConfig config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */

    public static <E> void putValue(E e, String name, String value, StuffConfig config) throws Exception {

        Class<?> paramtertype = e.getClass().getDeclaredField(name).getType();
        Method method = e.getClass().getMethod(StrUtils.achieveSetFunction(name), paramtertype);
        method.setAccessible(true);
        ClassCache.get().add(e.getClass(), name, paramtertype);
        methodInvokeValue(e, method, value, paramtertype.getSimpleName(), config);
    }

    /**
     * tips 注入对对象注入值
     *
     * @parameter: E e
     * @parameter: Method method
     * @parameter: String value
     * @parameter: String fieldType
     * @parameter: StuffConfig config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */
    public static <E> void methodInvokeValue(E e, Method method, String value, String fieldType, StuffConfig config) throws Exception {

        Constants.TypeEnum configs = null;
        for (Constants.TypeEnum typeEnum : Constants.TypeEnum.values()) {
            if (typeEnum.getValue().equals(fieldType)) {
                configs = typeEnum;
            }
        }
        if (StrUtils.isNNoE(value)) {
            switch (configs) {
                case STRING:
                    method.invoke(e, value);
                    break;
                case DATE:
                    if (DateUtils.isDate(value)) {
                        method.invoke(e, DateUtils.parse(value));
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
                    System.out.println("类型错误" + fieldType);
                    break;
            }
        } else {
            switch (configs) {
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

}
