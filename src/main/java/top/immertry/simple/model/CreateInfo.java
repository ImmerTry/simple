package top.immertry.simple.model;

import java.util.Date;

/**
 * @ClassName: CreateInfo
 * @Description TODO
 * @Author LL
 * @Date: 2019/04/01 下午 1:39
 * @Content: 创建信息
 */
public class CreateInfo {
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
