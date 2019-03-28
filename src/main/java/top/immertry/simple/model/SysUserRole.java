package top.immertry.simple.model;

/**
 * @Author: LL
 * @Date: 2019/3/22 下午 4:50
 * @Content: 用户角色关联表
 */
public class SysUserRole {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
