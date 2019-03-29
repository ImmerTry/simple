package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.SysRole;
import top.immertry.simple.model.SysUser;
import top.immertry.simple.util.MyMapperProxy;

import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @Author: LL
 * @Date: 2019/3/25 0025 上午 11:23
 * @Content: 用户测试类
 */
public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取 UserMapper 接口
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectById 方法，查询 id = 1 的用户
            SysUser user = mapper.selectById(1L);
            //User 不能为空
            Assert.assertNotNull(user);
            //userName = admin
            Assert.assertEquals("admin", user.getUserName());
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取 UserMapper 接口
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectAll 方法，查询所有
            List<SysUser> userList = mapper.selectAll();
            //结果不为空
            Assert.assertNotNull(userList);
            //用户数量大于0个
            Assert.assertTrue(userList.size() > 0);
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取 UserMapper 接口
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectRoleByUserId 获取当前用户角色信息
            List<SysRole> roleList = mapper.selectRoleByUserId(1L);
            //结果不为空
            Assert.assertNotNull(roleList);
            //角色数量大于0个
            Assert.assertTrue(roleList.size() > 0);
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //创建一个 user 对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassWord("123456");
            user.setUserEmail("test@mybatis.top");
            user.setUserInfo("test info");
            //正常情况下应该插入一张图片到 byte 数组中
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值 result 是执行的 SQL 影响的行数
            int result = mapper.insertSysUser(user);
            //只插入一条数据
            Assert.assertEquals(1, result);
            //id 为 null,没有给 id 赋值，并没有配置回写 id 的值
            Assert.assertNull(user.getId());
        } finally {
            //为了不影响其他测试，这里选择回滚
            //由于默认的sqlSessionFactory.openSession(）是不自动提交的
            //所以这里不需要执行 commit 也不会提交到数据库
            sqlSession.rollback();
            //关闭 sqlSession
            sqlSession.close();
        }
        /**
         * BLOB 对应的类型是 ByteArrayinputStream, 就是二进制数据流。
         * 由于数据库区分date、time、datetime类型，但是Java中一般都使用java.util.Date 类型。
         * 因此为了保证数据类型的正确，需要手动指定日期类型，date、time、datetime对应的
         * JDBC类型分别为DATE、 TIME、TIMESTAMP。
         */
    }

    @Test
    public void testInsertById() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //创建一个 user 对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassWord("123456");
            user.setUserEmail("test@mybatis.top");
            user.setUserInfo("test info");
            //正常情况下应该插入一张图片到 byte 数组中
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中，特别注意这里的返回值 result 是执行的 SQL 影响的行数
            int result = mapper.insertSysUser1(user);
            //只插入一条数据
            Assert.assertEquals(1, result);
            //id 回写，所以 id 不为 null
            Assert.assertNotNull(user.getId());
            System.out.println(user.getId());
        } finally {
            //为了不影响其他测试，这里选择回滚
            //由于默认的sqlSessionFactory.openSession(）是不自动提交的
            //所以这里不需要执行 commit 也不会提交到数据库
            sqlSession.rollback();
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //从数据库中查询 1 个对象
            SysUser user = mapper.selectById(1L);
            //当前 userName 为 admin
            Assert.assertEquals("admin", user.getUserName());
            //修改用户名
            user.setUserName("admin_test");
            //修改邮箱
            user.setUserEmail("test@mybatis.top");
            //更新数据，特别注意，这里的 result 是执行的 SQL 影响行数
            int result = mapper.updateById(user);
            //只更新一条数据
            Assert.assertEquals(1, result);
            //根据当前 id 查询修改后的数据
            user = mapper.selectById(1L);
            //修改后的名字为 admin_test
            Assert.assertEquals("admin_test", user.getUserName());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询1个 user 对象，根据 id = 1查询
            SysUser user = mapper.selectById(1L);
            //现在还能查询 user 对象
            Assert.assertNotNull(user);
            //调用方法删除
            Assert.assertEquals(1, mapper.deleteById(1L));
            //再次查询,这是没有值 为 null
            Assert.assertNull(mapper.selectById(1L));

            //使用 SysUser 参数再进行一次测试，根据 id = 1001 查询
            SysUser user1 = mapper.selectById(1001L);
            //现在还能查询 user 对象
            Assert.assertNotNull(user1);
            //调用方法删除
            Assert.assertEquals(1, mapper.deleteById(user1));
            //再次查询，这是应该没有值 为 null
            Assert.assertNull(mapper.selectById(1001L));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testMapperProxy() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        //获取 userMapper 接口
        MyMapperProxy myMapperProxy = new MyMapperProxy(UserMapper.class, sqlSession);
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{UserMapper.class},
                myMapperProxy);
        //调用selectAll 方法
        List<SysUser> userList = userMapper.selectAll();
    }

    @Test
    public void testSelectByUser() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser query = new SysUser();
            query.setUserName("ad");
            List<SysUser> userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);
            //只查询邮箱时
            query = new SysUser();
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);
            //同时查询用户、邮箱时
            query = new SysUser();
            query.setUserName("ad");
            query.setUserEmail("test@mybatis.top");
            userList = userMapper.selectByUser(query);
            //由于没有同时符合俩个条件的用户，因此查询结果为 0
            Assert.assertTrue(userList.size() == 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByIdSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            //更新 id = 1 的用户
            sysUser.setId(1L);
            //更新邮箱
            sysUser.setUserEmail("test@mybatis.tk");
            //返回受影响的行数
            int result = userMapper.updateByIdSelective(sysUser);
            //断言 只更新一条数据
            Assert.assertEquals(1, result);
            //查询当前更新过后的数据
            sysUser = userMapper.selectById(1L);
            //修改后的名称不变 邮箱格式发生改变
            Assert.assertEquals("admin", sysUser.getUserName());
            Assert.assertEquals("test@mybatis.tk", sysUser.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsertSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test_selective");
            sysUser.setUserPassWord("123456");
            sysUser.setUserInfo("test info");
            sysUser.setCreateTime(new Date());
            userMapper.insert(sysUser);
            sysUser = userMapper.selectById(sysUser.getId());
            Assert.assertEquals("test@mybatis.tk", sysUser.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("admin");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            //当没有 id 时
            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            //当 id 和 name 都为空时
            query.setUserName(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNull(user);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdList() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<Long> idList = new ArrayList<>();
            idList.add(1L);
            idList.add(1001L);
            //业务逻辑必须校验 idList.size() > 0
            List<SysUser> userList = userMapper.selectByIdList(idList);
            Assert.assertEquals(2, userList.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertList() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName("test" + i);
                sysUser.setUserPassWord("123456");
                sysUser.setUserEmail("test@mybatis.tk");
                userList.add(sysUser);
            }
            //将新建的对象批量插入到数据库中 结果返回数据库受影响的行数
            int result = userMapper.insertList(userList);
            //断言 插入2条记录
            Assert.assertEquals(2, result);
            for (SysUser user : userList) {
                System.out.println(user.getId());
            }
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            //查询字段 同样也是更新字段 必须保证该值存在
            map.put("id", 1L);
            //要更新其他字段
            map.put("user_email", "test@mybatis.tk");
            map.put("user_password", "123124567");
            //更新数据
            userMapper.updateByMap(map);
            //根据当前 id 查询更新后的数据
            SysUser sysUser = userMapper.selectById(1L);
            Assert.assertEquals("test@mybatis.tk", sysUser.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleId() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleId2(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleByIdSelect() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
            Assert.assertNotNull(user);
            System.out.println("调用 user.equals(null)");
            user.equals(null);
            System.out.println("调用 user.getRole()方法");
            Assert.assertNotNull(user.getRole());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRoles() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAllUserAndRoles();
            System.out.println("用户数：" + userList.size());
            for (SysUser user : userList) {
                System.out.println("用户名：" + user.getUserName());
                for (SysRole role : user.getRoleList()) {
                    System.out.println("角色名：" + role.getRoleName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }

}
