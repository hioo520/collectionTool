package com.collectionTool.fill.constant;

/**
 * tips: 填充工具控制器
 *
 * @notices:
 * @author: hihuzi 2018/9/30 10:08
 **/
public class StuffConfig implements StuffBase {
    /**
     * 对于空值得管理
     */
    private SaveStyleEnum saveStyleEnum;

    /**
     * 对于排序管理器
     */
    private SortStyleEnum sortStyleEnum;

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

    public StuffConfig(SaveStyleEnum saveStyleEnum) {
        this.saveStyleEnum = saveStyleEnum;
    }

    public SaveStyleEnum getSaveStyleEnum() {
        return saveStyleEnum != null ? saveStyleEnum : SaveStyleEnum.DEFAULT;
    }

    public void setSaveStyleEnum(SaveStyleEnum saveStyleEnum) {
        this.saveStyleEnum = saveStyleEnum;
    }

    public SortStyleEnum getSortStyleEnum() {
        return sortStyleEnum != null ? sortStyleEnum : SortStyleEnum.DEFAULT;
    }

    public void setSortStyleEnum(SortStyleEnum sortStyleEnum) {
        this.sortStyleEnum = sortStyleEnum;
    }
}
