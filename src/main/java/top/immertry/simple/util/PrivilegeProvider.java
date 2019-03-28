package top.immertry.simple.util;

import org.apache.ibatis.jdbc.SQL;

/**
 * @Author: LL
 * @Date: 2019/3/26 下午 1:44
 * @Content:
 */
public class PrivilegeProvider {
    public String selectById(final Long id) {
        return new SQL() {
            {
                SELECT("id,privilege_name,privilege_url");
                FROM("sys_privilege");
                WHERE("id = #{privilegeId}");
            }
        }.toString();
    }
}
