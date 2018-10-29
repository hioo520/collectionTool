package com.hihuzi.collection.fill.constant;

import java.text.SimpleDateFormat;

/**
 * tips 控制常量
 *
 * @author: hihuzi 2018/9/23 15:17
 */
public interface FillBase {

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

    /**
     * tips: 排序规则
     *
     * @notice: 通用枚举
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

}
