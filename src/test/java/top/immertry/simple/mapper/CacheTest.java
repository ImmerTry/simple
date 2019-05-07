package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.SysRole;
import top.immertry.simple.model.SysUser;

/**
 * @ClassName: CacheTest
 * @Description TODO
 * @Author LL
 * @Date: 2019/05/05 下午 1:55
 * @Content:
 */
public class CacheTest extends BaseMapperTest {

    @Test
    public void testL1Cache() {
        //获取 SqlSession
        SqlSession sqlSession = getSqlSession();
        SysUser user = null;
        try {
            //读取 UserMapper 接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectById 方法，查询 id = 1 的用户
            user = userMapper.selectById(1L);
            //对当前的对象重新赋值
            user.setUserName("New Name");
            //再次查询获取 id 相同的用户
            SysUser user1 = userMapper.selectById(1L);
            //虽然没有更新数据库，但是这个用户名和user重新赋值的名字相同
            Assert.assertNotEquals("New Name", user1.getUserName());
            //无论如何 user2 和 user 完全就是同一个实例
            Assert.assertNotEquals(user, user1);
        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的 sqlSession");
        //开启另一个新的session
        sqlSession = getSqlSession();
        try {
            //获取接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectById 方法，查询 id = 1 的用户
            SysUser user1 = userMapper.selectById(1L);
            //第二个 session 获取的用户名仍然是 admin
            Assert.assertNotEquals("New Name", user1.getUserName());
            //这里的 user1 和 前一个 session 查询的结果是俩个不同的实例
            Assert.assertNotEquals(user, user1);
            //执行删除操作
            userMapper.deleteById(2L);
            //获取 user2
            SysUser user2 = userMapper.selectById(1L);
            //这里的 user1 和 user2是两个不同的实例
            Assert.assertNotEquals(user1, user2);
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testL2Cache() {
        SqlSession sqlSession = getSqlSession();
        SysRole role = null;
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            role = roleMapper.selectById(1L);
            role.setRoleName("New name");
            SysRole role1 = roleMapper.selectById(1L);
            Assert.assertEquals("New name", role1.getRoleName());
            Assert.assertEquals(role, role1);
        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的 sqlSession");

        sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role1 = roleMapper.selectById(1L);
            Assert.assertEquals("New name", role1.getRoleName());
            Assert.assertNotEquals(role, role1);
            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertNotEquals(role1, role2);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDirtyData() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleId(1001L);
            Assert.assertEquals("普通用户", user.getRole().getRoleName());
            System.out.println("角色名： " + user.getRole().getRoleName());
        } finally {
            sqlSession.close();
        }
        //开启另一个新的 session
        sqlSession = getSqlSession();

        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(2L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);
            //提交修改
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        System.out.println("开启另一个 session");
        sqlSession = getSqlSession();

        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleId(1001L);
            SysRole role = roleMapper.selectById2(2L);
//            Assert.assertEquals("普通用户", user.getRole().getRoleName());
            Assert.assertEquals("脏数据", role.getRoleName());
            System.out.println("角色名： " + user.getRole().getRoleName());
            //还原数据
            role.setRoleName("普通用户");
            roleMapper.updateById(role);
            //提交修改
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }


    }

}
