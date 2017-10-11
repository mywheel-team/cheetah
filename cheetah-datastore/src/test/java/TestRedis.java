import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * @author wangtonghe
 * @date 2017/10/11 17:17
 */
public class TestRedis {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("localhost");


    }

    @Test
    public void test() {
        Set<String> list = jedis.smembers("movie");
        list.forEach(movie -> {
            Map<String, Object> map = JSON.parseObject(movie, Map.class);
            System.out.println(map);
        });
        System.out.println(list.size());
    }
}
