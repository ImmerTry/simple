package top.immertry.simple.model;

import java.util.Date;

/**
 * @Author: LL
 * @Date: 2019/3/22 下午 5:00
 * @Content: 角色表
 */
public class SysRole {
    /**
     * 角色 ID
     */
    private Long roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 有效标志
     */
    private Long enabled;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

