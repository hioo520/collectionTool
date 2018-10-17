package com.hihuzi.collection.utils;

/**
 * tips:
 *
 * @notices:
 * @author: hihuzi 2018/9/29 10:45
 **/
public class Constants {

    /**
     * tips 数据类型转化
     *
     * @notice : 0 是预留数据类型 表示没有匹配
     * @author:hihuzi 2018/9/24 19:30
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
