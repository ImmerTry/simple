package top.immertry.simple.mapper;

import org.apache.ibatis.annotations.*;
import top.immertry.simple.model.SysRole;

import java.util.List;

/**
 * @Author: LL
 * @Date: 2019/3/22 0022 下午 5:48
 * @Content: 权限
 */
public interface RoleMapper {
    /**
     * 通过主键查询角色信息
     *
     * @param id
     * @return
     */
    @Select({"select id,role_name roleName,enabled,create_by createBy,create_time createTime",
            "from sys_role",
            "where id = #{id}"})
    SysRole selectById(Long id);

    /**
     * 通过主键查询角色信息
     *
     * @param id
     * @return
     */
    @Results(id = "roleResultMap", value = {
            @Result(property = "roleId", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select id,role_name,enabled,create_by,create_time from sys_role where id = #{id}")
    SysRole selectById2(Long id);

    /**
     * 查询所有
     *
     * @return
     */
    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    /**
     * 插入数据 不需要返回主键
     *
     * @param sysRole
     * @return
     */
    @Insert({"insert into sys_role(id,role_name,enabled,create_by,create_time)",
            "values(#{roleId},#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})"})
    int insertRole(SysRole sysRole);

    /**
     * 插入数据 返回主键
     *
     * @param sysRole
     * @return
     */
    @Insert({"insert into sys_role(role_name,enabled,create_by,create_time)",
            "values(#{roleName},#{enabled},#{createBy},#{createTime})," +
                    "#{createTime,jdbcType=TIMESTAMP}"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRoles2(SysRole sysRole);

    /**
     * 插入数据 返回非自增主键
     *
     * @param sysRole
     * @return
     */
    @Insert({"insert into sys_role(role_name,enabled,create_by,create_time)",
            "values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=TIMESTAMP})"})
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
    int insertRole3(SysRole sysRole);

    /**
     * 更新数据
     *
     * @param sysRole
     * @return
     */
    @Update({"update sys_role",
            "set role_name = #{roleName},",
            "enabled = #{enabled},",
            "create_by = #{createBy},",
            "create_time = #{createTime,jdbcType=TIMESTAMP}",
            "where id = #{roleId}"
    })
    int update(SysRole sysRole);

    @Delete({"delete from sys_role where id =#{roleId}"})
    int deleteById(Long id);

    /**
     * 根据主键查询 角色权限信息
     * @param id
     * @return
     */
    SysRole selectRoleById(Long id);
}
