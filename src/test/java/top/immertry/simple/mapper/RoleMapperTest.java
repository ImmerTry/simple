package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.SysRole;

import java.util.Date;
import java.util.List;

/**
 * @Author: LL
 * @Date: 2019/3/26 0026 上午 10:58
 * @Content:
 */
public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testBySelectId1() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取 RoleMapper 接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 调用 selectById 方法 查询 id = 1 的角色
            SysRole sysRole = roleMapper.selectById(1L);
            //断言 sysRole 不为空
            Assert.assertNotNull(sysRole);
            //断言 roleName = 管理员
            Assert.assertEquals("管理员", sysRole.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBySelectId2() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById2(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员", sysRole.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();
            //断言 角色数量大于 0 个
            Assert.assertTrue(roleList.size() > 0);
            //断言 验证下划线字段是否映射成功
            Assert.assertNotNull(roleList.get(0).getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertRole() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("超级管理员");
            sysRole.setCreateBy(0L);
            sysRole.setEnabled(1L);
            sysRole.setCreateTime(new Date());
            //返回受影响的行数
            int result = roleMapper.insertRole(sysRole);
            //断言 只插入一条数据
            Assert.assertEquals(1, result);
            //断言 没有给 id 赋值 并没有配置 id 回显的值
            Assert.assertNull(sysRole.getRoleId());
        } finally {
            //测试 不提交
            sqlSession.rollback();
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateRole() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //查找 id = 1 的角色是否存在
            SysRole sysRole = roleMapper.selectById(1L);
            //断言 role 对象不为空
            Assert.assertNotNull(sysRole);
            //断言 roleName = 管理员
            Assert.assertEquals("管理员", sysRole.getRoleName());
            sysRole.setRoleName("超级管理员");
            sysRole.setCreateBy(0L);
            sysRole.setEnabled(1L);
            sysRole.setCreateTime(new Date());
            //返回受影响的行数
            int result = roleMapper.update(sysRole);
            //断言 修改后的 roleName = 超级管理员
            Assert.assertEquals("超级管理员", sysRole.getRoleName());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void deleteRoleById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //查询 roleId = 1 对象是否存在
            SysRole sysRole = roleMapper.selectById(1L);
            //断言 sysRole 不为空
            Assert.assertNotNull(sysRole);
            //调用 deleteById 方法 删除 结果返回受影响的行数
            int result = roleMapper.deleteById(1L);
            //断言 result = 1
            Assert.assertEquals(1, result);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
