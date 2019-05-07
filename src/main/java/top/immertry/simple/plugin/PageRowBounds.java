package top.immertry.simple.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * @ClassName: PageRowBounds
 * @Description TODO
 * @Author LL
 * @Date: 2019/05/07 上午 9:37
 * @Content: 可以记录 total 的分页参数
 */
public class PageRowBounds extends RowBounds {
    private long total;

    public PageRowBounds() {
        super();
    }

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
