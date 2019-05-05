package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.SysPrivilege;
import top.immertry.simple.model.SysRole;
import top.immertry.simple.type.Enabled;

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
//            sysRole.setEnabled(1L);
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
//            sysRole.setEnabled(1L);
            sysRole.setCreateTime(new Date());
            //返回受影响的行数
            int result = roleMapper.updateById(sysRole);
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

    @Test
    public void selectAllRoleAndPrivilege() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = mapper.selectAllRoleAndPrivileges();
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void selectRoleByUserId() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectRoleByUserId(1L);
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //数据此时的用户状态都为1 所以要给其中一个角色设置为0
            SysRole role = roleMapper.selectById(2L);
//            role.setRoleId(2L);
//            role.setEnabled(0L);
//            int result = roleMapper.updateById(role);
//            Assert.assertEquals(1,result);
            //获取用户为 1 的信息
            List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
            for (SysRole r : roleList) {
                System.out.println("角色名：" + r.getRoleName());
                if (r.getRoleId().equals(1L)) {
                    //第一个角色存在的权限信息
                    Assert.assertNotNull(r.getPrivilegeList());
                } else if (r.getRoleId().equals(2L)) {
                    //第二个角色的权限为 null
//                    Assert.assertNull(r.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege privilege : r.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = mapper.selectById(2L);
            Assert.assertEquals(Enabled.enabled,role.getEnabled());
            role.setEnabled(Enabled.disabled);
            mapper.updateById(role);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
