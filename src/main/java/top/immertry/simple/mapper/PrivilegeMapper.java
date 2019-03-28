package top.immertry.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import top.immertry.simple.model.SysPrivilege;
import top.immertry.simple.util.PrivilegeProvider;

/**
 * @Author: LL
 * @Date: 2019/3/22 0022 下午 5:49
 * @Content:
 */
public interface PrivilegeMapper {

    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);
}
