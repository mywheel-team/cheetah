import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/10/11 15:57
 */
public class TestMap {
    @Test
    public void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","jjj");
        map.put("email","dd@sss.com");
        map.put("author","sdcvg");
        List<String> list = Arrays.asList("sdf","fgt","hju");
        map.put("list",list);

        String jsonStr = JSON.toJSONString(map);
        System.out.println(jsonStr);

    }
}
