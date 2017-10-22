package org.togethwy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.togethwy.sample.common.Music163Demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/22 12:28
 */
public class TestMusic163 {

    @Test
    public void test(){
        List<Music> list = new ArrayList<>();
        list.add(new Music("dffg",123,"gg"));
        list.add(new Music("gddd",333,"hhhh"));
        System.out.println(JSONArray.toJSONString(list));
    }


    class Music {
        public Music(String name,int num) {
            this.name = name;
            this.num = num;
        }

        private String name;

        private int num;

        private String email;

        public String getEmail() {
            return email;
        }

        public Music(String name, int num, String email) {
            this.name = name;
            this.num = num;
            this.email = email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Music{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
