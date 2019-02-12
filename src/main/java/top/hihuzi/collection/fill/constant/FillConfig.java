package top.hihuzi.collection.fill.constant;

import top.hihuzi.collection.utils.Constants;

import java.util.List;

/**
 * tips: 填充工具控制器
 *
 * @notices: 默认时间类型是 yyyy-MM-dd
 * @author: hihuzi 2018/9/30 10:08
 **/
public class FillConfig implements FillBase {

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
     * 对于返回风格管理器
     */
    private ReturnStyleEnum returnStyleEnum;

    public FillConfig(DateStyleEnum dateStyleEnum, ReturnStyleEnum returnStyleEnum) {

        this.dateStyleEnum = dateStyleEnum;
        this.returnStyleEnum = returnStyleEnum;

    }


    /**
     * 重置枚举对象
     * 对枚举静态变量一定要初始化
     */
    public static void reset() {

        SortStyleEnum.DEFAULT.setSort(new Integer[]{});
        DateStyleEnum.DEFAULT.dateFormat.remove();
        ReturnStyleEnum.DEFAULT.setList(new List[]{});
    }

    /**
     * tips: 默认
     *
     * @notice: saveStyleEnum=SaveStyleEnum.DEFAULT
     * @author: hihuzi 2018/9/30 10:59
     **/
    public FillConfig() {

    }

    public FillConfig(SaveStyleEnum saveStyleEnum, SortStyleEnum sortStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        this.sortStyleEnum = sortStyleEnum;
    }

    public FillConfig(SaveStyleEnum saveStyleEnum, SortStyleEnum sortStyleEnum, DateStyleEnum dateStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        this.sortStyleEnum = sortStyleEnum;
        this.dateStyleEnum = dateStyleEnum;
    }

    public FillConfig(SaveStyleEnum saveStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
    }

    public FillConfig(DateStyleEnum dateStyleEnum) {

        this.dateStyleEnum = dateStyleEnum;
    }

    public FillConfig(ReturnStyleEnum returnStyleEnum) {

        this.returnStyleEnum = returnStyleEnum;
    }

    public SaveStyleEnum getSaveStyleEnum() {

        return null != saveStyleEnum ? saveStyleEnum : SaveStyleEnum.DEFAULT;
    }

    public FillConfig setSaveStyleEnum(SaveStyleEnum saveStyleEnum) {

        this.saveStyleEnum = saveStyleEnum;
        return this;
    }

    public SortStyleEnum getSortStyleEnum() {

        return null != sortStyleEnum ? sortStyleEnum : SortStyleEnum.DEFAULT;
    }

    public void setSortStyleEnum(SortStyleEnum sortStyleEnum) {

        this.sortStyleEnum = sortStyleEnum;
    }

    public DateStyleEnum getDateStyleEnum() {

        return null != dateStyleEnum ? dateStyleEnum : DateStyleEnum.DEFAULT.setFormartStyle(Constants.DATE_FORMART);
    }

    public void setDateStyleEnum(DateStyleEnum dateStyleEnum) {

        this.dateStyleEnum = dateStyleEnum;
    }

    public ReturnStyleEnum getReturnStyleEnum() {

        return null != returnStyleEnum ? returnStyleEnum : ReturnStyleEnum.DEFAULT;
    }

    public void setReturnStyleEnum(ReturnStyleEnum returnStyleEnum) {

        this.returnStyleEnum = returnStyleEnum;
    }

}
