package com.collectionTool.fill.constant;

/**
 * tips: 填充工具控制器
 *
 * @notices: 默认时间类型是 yyyy-MM-dd
 * @author: hihuzi 2018/9/30 10:08
 **/
public class StuffConfig implements StuffBase {

    /**
     * tips 默认可以处理的时间类型
     */
    private static final String DATE_FORMART = "yyyy-MM-dd";

    /**
     * 对于空值得管理
     */
    private SaveStyleEnum saveStyleEnum;

    /**
     * 对于排序管理器
     */
    private SortStyleEnum sortStyleEnum;

    /**
     * 对于时间管理器
     */
    private DateStyleEnum dateStyleEnum;

    /**
     * 重置枚举对象
     * 对枚举静态变量一定要初始化
     */
    public static void reset() {

        StuffConfig.SortStyleEnum.DEFAULT.setSort(new Integer[]{});
        StuffConfig.DateStyleEnum.DEFAULT.setFormartStyle(DATE_FORMART);
    }

    /**
     * tips: 默认
     *
     * @notice: saveStyleEnum=SaveStyleEnum.DEFAULT
     * @author: hihuzi 2018/9/30 10:59
     **/
    public StuffConfig() {

    }

    public StuffConfig(SaveStyleEnum saveStyleEnum, SortStyleEnum sortStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        this.sortStyleEnum = sortStyleEnum;
    }

    public StuffConfig(SaveStyleEnum saveStyleEnum, SortStyleEnum sortStyleEnum, DateStyleEnum dateStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        this.sortStyleEnum = sortStyleEnum;
        this.dateStyleEnum = dateStyleEnum;
    }

    public StuffConfig(SaveStyleEnum saveStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
    }

    public StuffConfig(DateStyleEnum dateStyleEnum) {

        this.dateStyleEnum = dateStyleEnum;
    }

    public SaveStyleEnum getSaveStyleEnum() {

        return saveStyleEnum != null ? saveStyleEnum : SaveStyleEnum.DEFAULT;
    }

    public StuffConfig setSaveStyleEnum(SaveStyleEnum saveStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        return this;
    }

    public SortStyleEnum getSortStyleEnum() {

        return sortStyleEnum != null ? sortStyleEnum : SortStyleEnum.DEFAULT;
    }

    public void setSortStyleEnum(SortStyleEnum sortStyleEnum) {

        this.sortStyleEnum = sortStyleEnum;
    }

    public DateStyleEnum getDateStyleEnum() {

        return dateStyleEnum != null ? dateStyleEnum : DateStyleEnum.DEFAULT.setFormartStyle(DATE_FORMART);
    }

    public void setDateStyleEnum(DateStyleEnum dateStyleEnum) {

        this.dateStyleEnum = dateStyleEnum;
    }

}
