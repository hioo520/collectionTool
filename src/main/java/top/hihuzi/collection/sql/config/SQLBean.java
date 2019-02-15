package top.hihuzi.collection.sql.config;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tips collection
 *
 * @author: hihuzi 2019/2/15 17:13
 */
public class SQLBean {


    /**
     * tips 唯一性 用于此条SQL 缓存(不设置存在数据紊乱(情况:出现同样检索只是SQL不同时缓存的SQL容易被覆盖))
     *
     * @author: hihuzi 2019/2/15 10:13
     */
    private String unique;

    /**
     * tips 待查询的class对象
     *
     * @author: hihuzi 2019/2/15 10:13
     */
    private List<Class> clazz;

    /**
     * tips 名称的表昵称 提取表的昵称作为 属性的前缀
     *
     * @author: hihuzi 2019/2/15 10:15
     */
    private Map<String, String> nickname;

    /**
     * tips 存在重复属性(也就是重复的列名)
     *
     * @author: hihuzi 2019/2/15 10:15
     */
    private List<String> repeat;

    /**
     * tips 需要检索出来的属性(也就是待显示的列名)
     *
     * @notice: 没有配置时 是查找全部
     * @author: hihuzi 2019/2/15 10:15
     */
    private List<String> display;

    public SQLBean(String unique, List<Class> clazz, Map nickname, List<String> repeat, List<String> display) {

        this.unique = unique;
        this.clazz = clazz;
        this.nickname = nickname;
        this.repeat = repeat;
        this.display = display;
    }

    public SQLBean() {


    }

    public String getUnique() {

        return unique;
    }

    public SQLBean addUnique(Object unique) {

        this.unique = String.valueOf(unique);
        return this;
    }

    public List<Class> getClazz() {

        return clazz;
    }

    public <E> SQLBean addClazz(E... e) {

        this.clazz = (List<Class>) Arrays.asList(e);
        return this;
    }

    public Map getNickname() {

        return nickname;
    }

    public <E> SQLBean addNickname(E... e) {

        Map map = new HashMap();
        List<String> list = (List<String>) Arrays.asList(e);
        for (int i = 0; i < this.clazz.size(); i++) {
            map.put(clazz.getClass().getName(), list.get(i));

        }
        this.nickname = nickname;
        return this;
    }

    public List<String> getRepeat() {

        return repeat;
    }

    public <E> SQLBean addRepeat(E... e) {

        this.repeat = (List<String>) Arrays.asList(e);
        return this;
    }

    public List<String> getDisplay() {

        return display;
    }

    public <E> SQLBean addDisplay(E... e) {

        this.display = (List<String>) Arrays.asList(e);
        return this;
    }

}
