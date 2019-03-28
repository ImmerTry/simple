package top.immertry.simple.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: LL
 * @Date: 2019/3/26 上午 10:10
 * @Content: 使用 java 动态代理方式创建一个代理类
 */
public class MyMapperProxy<T> implements InvocationHandler {

    private Class<T> mapperInterface;
    private SqlSession sqlSession;

    public MyMapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //针对不同的 sql 类型，需要调用 sqlSession 不同方法
        //接口方法中的参数也有很多情况，这里只考虑没有参数的情况
        List<Object> selectList = sqlSession.selectList(mapperInterface.getCanonicalName() + "." + method.getName());
        return selectList;
    }
}
