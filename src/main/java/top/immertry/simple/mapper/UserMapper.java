package top.immertry.simple.mapper;

import top.immertry.simple.model.SysRole;
import top.immertry.simple.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Author: LL
 * @Date: 2019/3/22 0022 下午 5:48
 * @Content:
 */
public interface UserMapper {
    /**
     * 通过 id 查询用户
     *
     * @param id
     * @return
     */
    SysUser selectById(Long id);

    /**
     * 查询全部用户
     *
     * @return
     */
    List<SysUser> selectAll();

    /**
     * 根据 id 获取角色信息
     *
     * @param id
     * @return
     */
    List<SysRole> selectRoleByUserId(Long id);

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    int insertSysUser(SysUser sysUser);

    /**
     * 新增用户 使用 selectKey 方式
     *
     * @param sysUser
     * @return
     */
    int insertSysUser1(SysUser sysUser);

    /**
     * 根据主键进行更新
     *
     * @param sysUser
     * @return
     */
    int updateById(SysUser sysUser);

    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 通过对象删除
     *
     * @param sysUser
     * @return
     */
    int deleteById(SysUser sysUser);

    /**
     * 根据动态条件查询用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectByUser(SysUser sysUser);

    /**
     * 通过主键 ID 更新
     * 一般情况下 MyBatis 中选择更新的方法名会以 Selective
     *
     * @param sysUser
     * @return
     */
    int updateByIdSelective(SysUser sysUser);

    /**
     * 插入数据
     *
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);

    /**
     * 根据主键 Id 或用户名查询
     *
     * @param sysUser
     * @return
     */
    SysUser selectByIdOrUserName(SysUser sysUser);

    /**
     * 根据用户 id 集合查询
     *
     * @param idList
     * @return
     */
    List<SysUser> selectByIdList(List<Long> idList);

    /**
     * 根据用户 id 集合查询
     *
     * @param idArray
     * @return
     */
    List<SysUser> selectByIdList(Long[] idArray);

    /**
     * 批量插入用户信息
     *
     * @param userList
     * @return
     */
    int insertList(List<SysUser> userList);

    /**
     * 通过 map 更新列
     *
     * @return
     */
    int updateByMap(Map<String, Object> map);

    /**
     * 根据用户 id 获取用户信息和用户的角色信息 （一对一映射）
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleId(Long id);

    /**
     * 根据用户 id 获取用户信息和用户的角色信息 （resultMap 一对一映射）
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleId2(Long id);

    /**
     * 根据用户 id 获取用户信息和用户的角色信息 （嵌套查询）
     *
     * @param id
     * @return
     */
    SysUser selectUserAndRoleByIdSelect(Long id);

    /**
     * 查询所有用户及其对应的角色
     *
     * @return
     */
    List<SysUser> selectAllUserAndRoles();

    /**
     * 通过嵌套查询获取指定用户信息以及用户角色和权限信息
     *
     * @param id
     * @return
     */
    SysUser selectAllUserAndRolesSelect(Long id);
}
