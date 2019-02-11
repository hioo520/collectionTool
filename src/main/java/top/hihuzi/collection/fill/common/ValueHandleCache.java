package top.hihuzi.collection.fill.common;

import top.hihuzi.collection.fill.constant.FillConfig;
import top.hihuzi.collection.utils.StrUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;


/**
 * tips 塞值处理器(高效)
 *
 * @author: hihuzi 2018/9/24 20:57
 */
public class ValueHandleCache {

    /**
     * tips 缓存注入数据
     *
     * @parameter: E e
     * @parameter: Method method
     * @parameter: String value
     * @parameter: String fieldType
     * @parameter: FillEnum enums
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */
    public static <E> void invokeValueCache(E e, Method method, String value, TypeEnum typeEnum, FillConfig config) throws Exception {

        if (StrUtils.isNNoE(value)) {
            switch (typeEnum) {
                case STRING:
                    method.invoke(e, String.valueOf(value));
                    break;
                case DATE:
                    method.invoke(e, config.getDateStyleEnum().getFormartStyle().parse(value));
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
     * tips 通用方法注入值
     *
     * @parameter: E e
     * @parameter: Method method
     * @parameter: String value
     * @parameter: String fieldType
     * @parameter: FillConfig config
     * @return:
     * @author: hihuzi 2018/7/19 10:26
     */
    public static <E> void invokeValue(E e, Method method, String value, String fieldType, FillConfig config) throws Exception {

        TypeEnum configs = null;
        for (TypeEnum typeEnum : TypeEnum.values()) {
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
                    method.invoke(e, config.getDateStyleEnum().getFormartStyle().parse(value));
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

    /**
     * tips 只针对时间类型转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author: hihuzi 2018/10/10 19:30
     */
    public static Object processingTimeType(Class<?> type, FillConfig config, Object obj) {

        if (TypeEnum.DATE.getValue().equals(type.getSimpleName())) {
            return config.getDateStyleEnum().getFormartStyle().format(obj);
        }
        return obj;
    }

    /**
     * tips 数据类型转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author: hihuzi 2018/9/24 19:30
     */
    public enum TypeEnum {
        /**
         * 数据类型
         */
        BOOLEAN(1, "Boolean"),
        /**
         * 数据类型
         */
        BYTE(2, "Byte"),
        /**
         * 数据类型
         */
        SHORT(3, "Short"),
        /**
         * 数据类型
         */
        INTEGER(4, "Integer"),
        /**
         * 数据类型
         */
        LONG(5, "Long"),
        /**
         * 数据类型
         */
        FLOAT(6, "Float"),
        /**
         * 数据类型
         */
        DOUBLE(7, "Double"),
        /**
         * 数据类型
         */
        STRING(8, "String"),
        /**
         * 数据类型
         */
        BIGDECIMAL(9, "BigDecimal"),
        /**
         * 数据类型
         */
        DATE(10, "Date"),
        /**
         * 数据类型
         */
        CHAR(11, "char"),
        /**
         * 数据类型
         */
        INT(12, "int"),
        /**
         * 数据类型
         */
        BOOLEAN_MIN(13, "boolean"),
        /**
         * 数据类型
         */
        BYTE_MIN(14, "byte"),
        /**
         * 数据类型
         */
        SHORT_MIN(15, "short"),
        /**
         * 数据类型
         */
        LONG_MIN(16, "long"),
        /**
         * 数据类型
         */
        FLOAT_MIN(17, "float"),
        /**
         * 数据类型
         */
        DOUBLE_MIN(18, "double");

        private Integer key;

        private String value;

        TypeEnum(Integer key, String value) {

            this.key = key;
            this.value = value;

        }

        public String getValue() {

            return value;
        }
    }

}
