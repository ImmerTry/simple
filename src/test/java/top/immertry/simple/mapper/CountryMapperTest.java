package top.immertry.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import top.immertry.simple.model.Country;
import top.immertry.simple.model.CountryExample;

import java.util.List;

/**
 * @Author: 刘磊
 * @Date: 2019/3/22 下午 1:44
 * @Content:
 */
public class CountryMapperTest extends BaseMapperTest {

    @Test
    public void testSelectAll() {
        //通过 sqlSessionFactory 工厂对象获取一个 SqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //通过 SqlSession 的 selectList 方法查找到 CountryMapper.xml 中的 selectAll() 方法 执行 SQL 查询
            List<Country> countryList = sqlSession.selectList("top.immertry.simple.mapper.CountryMapper.selectAll");
            printCountry(countryList);
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建 Example 对象
            CountryExample example = new CountryExample();
            //设置排序规则
            example.setOrderByClause("id desc, countryname asc");
            //设置 distinct 是否去重
            example.setDistinct(true);
            //创建条件
            CountryExample.Criteria criteria = example.createCriteria();
            //id > 1
            criteria.andIdGreaterThanOrEqualTo(1);
            //id < 4
            criteria.andIdLessThan(4);
            //countrycode like '%U%'
            criteria.andCountrycodeLike("%U%");
            //or 情况
            CountryExample.Criteria or = example.or();
            //countryname = 中国
            or.andCountrynameEqualTo("中国");
            //执行查询
            List<Country> countryList = countryMapper.selectByExample(example);
            printCountry(countryList);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExampleSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建 Example 对象
            CountryExample example = new CountryExample();
            //创建条件，只有一个 createCriteria
            CountryExample.Criteria criteria = example.createCriteria();
            //更新所有 id > 2 的国家
            criteria.andIdGreaterThan(2);
            //创建一个要设置对象
            Country country = new Country();
            //将国家名字设置成为 China
            country.setCountryname("China");
            //执行查询
            countryMapper.updateByExampleSelective(country, example);
            //查看符合条件的结果
            printCountry(countryMapper.selectByExample(example));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
            //创建 example 对象
            CountryExample example = new CountryExample();
            //创建条件 只能有一个 createCriteria
            CountryExample.Criteria criteria = example.createCriteria();
            //删除所有 id > 2 的国家
            criteria.andIdGreaterThan(2);
            //执行查询
            countryMapper.deleteByExample(example);
            //使用 countryByExample 查询符合条件的数量，因为已删除，所以这里应该是0
            Assert.assertEquals(0,countryMapper.countByExample(example));
        } finally {
            sqlSession.close();
        }
    }

    private void printCountry(List<Country> countryList) {
        for (Country country : countryList) {
            System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }
}
