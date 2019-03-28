package top.immertry.simple.model;

/**
 * @Author: LL
 * @Date: 2019/3/22 下午 4:57
 * @Content: 角色权限关联表
 */
public class SysRolePrivilege {
    /**
     * 角色 ID
     */
    private Long roleId;
    /**
     * 权限 ID
     */
    private Long privilegeId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }
}
