package com.collectionTool.cache;

import com.collectionTool.fill.common.ValueHandleCache;
import com.collectionTool.utils.StrUtils;

import java.lang.reflect.Method;

/**
 * tips 待处理的某一状态(多实例)
 *
 * @author: hihuzi 2018/9/24 9:23
 */
public class TypeCache {

    /**
     * class对象
     */
    private Class<?> clazz;

    /**
     * ParamterName的get方法
     */
    private Method methodGet;

    /**
     * ParamterName的set方法
     */
    private Method methodSet;

    /**
     * 属性
     */
    private String paramterName;

    /**
     * 参数
     */
    private String paramterGet;

    /**
     * 参数
     */
    private String paramterSet;

    /**
     * 参数类型
     */
    private Class<?> paramtertype;

    private ValueHandleCache.TypeEnum type;

    /**
     * tips 构造器实例化对象
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: Class<?> paramtertype
     * @author: hihuzi 2018/9/24 23:45
     */
    public TypeCache(Class<?> clazz, String paramterName, Class<?> paramtertype) {

        this.clazz = clazz;
        this.paramterName = paramterName;
        this.paramtertype = paramtertype != null ? paramtertype : conversiontoparamterType(paramterName);
        this.paramterGet = StrUtils.achieveGetFunction(paramterName);
        this.paramterSet = StrUtils.achieveSetFunction(paramterName);
        this.methodGet = conversionToMethod(this.paramterGet, null);
        this.methodSet = conversionToMethod(this.paramterSet, this.paramtertype);
        this.type = conversionToType(this.paramtertype);
    }


    /**
     * tips 构造器实例化对象
     *
     * @parameter: Class<?> clazz
     * @parameter: String paramterName
     * @parameter: Class<?> paramtertype
     * @return: TypeCache
     * @author: hihuzi 2018/9/24 23:45
     */
    public static TypeCache add(Class<?> clazz, String paramterName, Class<?> paramtertype) {

        TypeCache cacheAttribute = new TypeCache(clazz, paramterName, paramtertype);
        return cacheAttribute;
    }

    /**
     * tips 转化为对应的参数类型
     * 针对继承父类方法的解析
     *
     * @parameter: String paramterName
     * @return: Class<?> paramtertype
     * @author: hihuzi 2018/9/24 19:12
     */
    private Class<?> conversiontoparamterType(String paramterName) {

        Class<?> paramterType = null;
        try {
            paramterType = clazz.getDeclaredField(paramterName).getType();
        } catch (Exception e) {

        }
        if (paramterType != null) {
            return paramterType;
        } else {
            for (clazz = clazz.getSuperclass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    paramterType = clazz.getDeclaredField(paramterName).getType();
                } catch (Exception e) {
                }
                if (paramterType != null) {
                    return paramterType;
                }
            }
        }
        System.out.println(this.clazz + "中无此" + paramterName + "属性,从而无法确定参数类型");
        return null;
    }

    /**
     * tips 转化为对应的方法类型
     * (待优化)
     * 针对继承父类方法的解析
     *
     * @parameter: String paramter
     * @parameter: Class<?> paramtertype
     * @return: Method
     * @author: hihuzi 2018/9/24 19:12
     */
    private Method conversionToMethod(String paramter, Class<?> paramtertype) {

        Method method = null;
        try {
            method = clazz.getMethod(paramter, paramtertype);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(paramter);
            } catch (NoSuchMethodException e1) {
            }
        }
        if (method != null) {
            return method;
        } else {
            for (clazz = clazz.getSuperclass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    method = clazz.getMethod(paramter, paramtertype);
                } catch (NoSuchMethodException e) {
                    try {
                        method = clazz.getMethod(paramter);
                    } catch (NoSuchMethodException e1) {
                    }
                }
                if (method != null) {
                    return method;
                }
            }
        }
        System.out.println(this.clazz + "中无此" + paramter + "属性,从而无法确定方法类型");
        return null;
    }

    /**
     * tips 转化为对应的方法类型
     *
     * @parameter: Class<?> paramtertype
     * @return: ValueHandleCache.TypeEnum
     * @author: hihuzi 2018/9/24 19:12
     */
    private ValueHandleCache.TypeEnum conversionToType(Class<?> paramtertype) {

        ValueHandleCache.TypeEnum[] values = ValueHandleCache.TypeEnum.values();
        for (ValueHandleCache.TypeEnum value : values) {
            if (value.getValue().equals(paramtertype.getSimpleName())) {
                return value;
            }
        }
        return null;
    }

    public Class<?> getClazz() {

        return clazz;
    }

    public Method getMethodGet() {

        return methodGet;
    }

    public Method getMethodSet() {

        return methodSet;
    }

    public String getParamterName() {

        return paramterName;
    }

    public String getParamterGet() {

        return paramterGet;
    }

    public String getParamterSet() {

        return paramterSet;
    }

    public Class<?> getParamtertype() {

        return paramtertype;
    }

    public ValueHandleCache.TypeEnum getType() {

        return type;
    }

}
