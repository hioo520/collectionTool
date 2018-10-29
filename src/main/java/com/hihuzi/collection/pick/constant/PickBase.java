package com.hihuzi.collection.pick.constant;

import java.text.SimpleDateFormat;

/**
 * tips: 所有组件
 *
 * @notices: 返回风格 返回key控制 返回value控制
 * @author: hihuzi 2018/9/29 16:44
 **/
public interface PickBase {
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
     * tips: 返回类型枚举
     *
     * @notice: 通用枚举
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum ReturnStyleEnum {
        /**
         * value=1: 返回类型:List<Map>
         *
         * @author: hihuzi
         */
        DEFAULT(0),
        /**
         * value=0: 返回类型:List<Map>
         *
         * @author: hihuzi
         */
        LIST_MAP(1),
        /**
         * value=2: 返回类型:Map
         *
         * @author: hihuzi
         */
        MAP(2),
        /**
         * value=3: 返回类型:Set
         *
         * @author: hihuzi
         */
        SET(3);
        private Integer key;

        ReturnStyleEnum(Integer key) {
            this.key = key;
        }

        public Integer getKey() {
            return key;
        }
    }

    /**
     * tips: 返回值为空或者""
     *
     * @notice: 通用枚举
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum SaveStyleEnum {
        /**
         * 所有都保存
         */
        DEFAULT(true),
        /**
         * 所有都保存(不处理)
         */
        NOT_PROCESSED(true),
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
         * @notice: 判断是否存在Null empty "" "   "
         * @author: hihuzi 2018/9/30 8:52
         **/
        public Boolean getHaving() {
            return isHaving;
        }
    }

    /**
     * tips: 时间规则
     *
     * @notice: 通用枚举
     * @author: hihuzi 2018/9/29 14:54
     **/
    enum DateStyleEnum {
        /**
         * 默认时间风格规则
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
            SimpleDateFormat threadSimpleDateFormat = dateFormat.get();
            if (null == threadSimpleDateFormat || !formartStyle.equals(threadSimpleDateFormat.toPattern())) {
                dateFormat.set(new SimpleDateFormat(formartStyle));
            }
            return this;
        }
    }
}
