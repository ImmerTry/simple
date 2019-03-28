package top.immertry.simple.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.Reader;


/**
 * @Author: LL
 * @Date: 2019/3/25 0025 上午 10:47
 * @Content: 基础测试类
 */
public class BaseMapperTest {
    //初始化 静态 SQL会话工厂
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            //通过 Resources 工具类将 mybatis-config.xml 配置文件读入 reader
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            //再通过 sqlSessionFactoryBuilder 建造类使用 Reader 创建 sqlSessionFactory 工厂对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            //关闭 reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 sqlSession
     *
     * @return SqlSession
     */
    public SqlSession getSqlSession() {
        //通过 sqlSessionFactory 工厂对象获取一个 SqlSession
        return sqlSessionFactory.openSession();
    }
}
