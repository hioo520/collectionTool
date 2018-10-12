package com.collectionTool.pick.constant;

/**
 * tips:构造器
 *
 * @notices:
 * @author: hihuzi 2018/9/29 16:43
 **/
public class PickConfig implements PickBase {
    /**
     * 返回名自定义
     */
    private ReturnNameEnum returnNameEnum;
    /**
     * 返回风格
     */
    private ReturnStyleEnum returnStyleEnum;
    /**
     * 对于空值得管理
     */
    private SaveStyleEnum saveStyleEnum;

    /**
     * tips: 使用这个构造器所有值都是调用默认值
     *
     * @notice: this.returnNameEnum = ReturnNameEnum.DEFAULT;
     * @notice: this.saveStyleEnum = SaveStyleEnum.DEFAULT;
     * @notice: this.returnNameEnum = ReturnNameEnum.DEFAULT;
     * @author: hihuzi 2018/9/30 8:59
     **/
    public PickConfig() {
    }

    public PickConfig(SaveStyleEnum saveStyleEnum) {
        this.saveStyleEnum = saveStyleEnum;
    }

    public PickConfig(ReturnStyleEnum returnStyleEnum, ReturnNameEnum returnNameEnum, SaveStyleEnum saveStyleEnum) {
        this.returnNameEnum = returnNameEnum;
        this.returnStyleEnum = returnStyleEnum;
        this.saveStyleEnum = saveStyleEnum;
    }

    public ReturnNameEnum getReturnNameEnum() {
        return returnNameEnum != null ? returnNameEnum : ReturnNameEnum.DEFAULT;
    }

    public PickConfig setReturnNameEnum(ReturnNameEnum returnNameEnum) {
        this.returnNameEnum = returnNameEnum != null ? returnNameEnum : ReturnNameEnum.DEFAULT;
        return this;
    }

    public ReturnStyleEnum getReturnStyleEnum() {
        return returnStyleEnum != null ? returnStyleEnum : ReturnStyleEnum.DEFAULT;
    }

    public PickConfig setReturnStyleEnum(ReturnStyleEnum returnStyleEnum) {
        this.returnStyleEnum = returnStyleEnum != null ? returnStyleEnum : ReturnStyleEnum.DEFAULT;
        return this;
    }

    public SaveStyleEnum getSaveStyleEnum() {
        return saveStyleEnum != null ? saveStyleEnum : SaveStyleEnum.DEFAULT;
    }

    public PickConfig setSaveStyleEnum(SaveStyleEnum saveStyleEnum) {
        this.saveStyleEnum = saveStyleEnum != null ? saveStyleEnum : SaveStyleEnum.DEFAULT;
        return this;
    }
}
