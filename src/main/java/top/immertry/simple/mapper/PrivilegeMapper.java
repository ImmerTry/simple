package top.immertry.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import top.immertry.simple.model.SysPrivilege;
import top.immertry.simple.util.PrivilegeProvider;

import java.util.List;

/**
 * @Author: LL
 * @Date: 2019/3/22 下午 5:49
 * @Content:
 */
public interface PrivilegeMapper {


    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);

    /**
     * 通过角色 id 获取该角色对应的所有权限信息
     *
     * @param id
     * @return
     */
    List<SysPrivilege> selectPrivilegeByRoleId(Long id);
}
