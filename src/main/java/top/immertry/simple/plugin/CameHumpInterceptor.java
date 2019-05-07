package top.immertry.simple.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

/**
 * @ClassName: CameHumpInterceptor
 * @Description TODO
 * @Author LL
 * @Date: 2019/05/06 下午 4:12
 * @Content: Mybatis Map类型下划线 key 转小写驼峰形式
 */
@Intercepts(@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class}
))
@SuppressWarnings({"unchecked", "rawtypes"})
public class CameHumpInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行得到结果，再对结果进行处理
        List<Object> objectList = (List<Object>) invocation.proceed();
        for (Object object : objectList) {
            //如果是 Map 类型，就对 Map 的 key 进行转换
            if (object instanceof Map) {
                processMap((Map) object);
            } else {
                break;
            }
        }
        return objectList;
    }

    /**
     * 处理 Map 类型
     *
     * @param map
     */
    private void processMap(Map<String, Object> map) {
        Set<String> keySet = new HashSet<>(map.keySet());
        for (String key : keySet) {
            if (key.charAt(0) >= 'A'
                    && key.charAt(0) <= 'Z'
                    || key.indexOf("_") >= 0) {
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelhump(key), value);

            }
        }
    }

    /**
     * 将下画线风格替换为驼峰风格
     *
     * @param inputString
     * @return
     */
    private String underlineToCamelhump(String inputString) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
