package top.immertry.simple.model;

import top.immertry.simple.type.Enabled;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: LL
 * @Date: 2019/3/22 下午 5:00
 * @Content: 角色表
 */
public class SysRole implements Serializable {

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
    private Enabled enabled;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色包含的权限列表
     */
    private List<SysPrivilege> privilegeList;

    /**
     * 创建信息
     */
    private CreateInfo createInfo;

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

    public Enabled getEnabled() {
        return enabled;
    }

    public void setEnabled(Enabled enabled) {
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

    public List<SysPrivilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<SysPrivilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public CreateInfo getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(CreateInfo createInfo) {
        this.createInfo = createInfo;
    }
}

