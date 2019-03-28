package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.SysPrivilege;

/**
 * @Author: LL
 * @Date: 2019/3/26 0026 下午 1:51
 * @Content:
 */
public class PrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取 privilegeMapper 接口
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            //调用 selectById 方法 查询 id = 1 对象
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            //断言 privilege 不为 空
            Assert.assertNotNull(privilege);
            //断言 privilegeName = 用户管理
            Assert.assertEquals("用户管理",privilege.getPrivilegeName());
        } finally {
            sqlSession.close();
        }
    }
}
