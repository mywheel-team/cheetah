package org.togethwy.sample.common;

import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.JsonDataResult;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.downloader.Request;
import org.togethwy.cheetah.downloader.RequestMethod;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.ElasticHandler;
import org.togethwy.cheetah.handler.RedisHandler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/10/12 22:19
 */
public class Music163Demo implements PageProcessor {

    private SiteConfig siteConfig = SiteConfig.create();

    @Override
    public void process(Page page, CheetahResult cheetahResult) {

        String url = page.getUrl();
        int index = url.lastIndexOf("song?id=");
        if (index > -1) {
            Selectable songInfo = page.getHtml().$(".m-lycifo .cnt");
            String name = songInfo.$(".tit em").getValue();
            String singer = songInfo.$("p").get(0).$("span a").getValue();
            String album = songInfo.$("p").get(1).$("a").getValue();
            String musicId = url.substring(index + 8).trim();
            Map<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("id", musicId);
            result.put("singer", singer);
            result.put("album", album);
            cheetahResult.putResult(result);
            cheetahResult.setStartJsonAPI(true);
        } else {
            Selectable discover = page.getHtml().$("#m-disc-pl-c");

            //歌单类型
            List<String> typeUrls = discover.$("#cateListBox > .bd .f-cb").get(0).getLinks();
            cheetahResult.addWaitRequest(typeUrls);

            //歌单
            List<String> playUrls = discover.$("#m-pl-container li > div.u-cover").getLinks();
            cheetahResult.addWaitRequest(playUrls);

            //下一页
            List<String> nextUrl = discover.$("#m-pl-pager .u-page").getLinks();
            if (nextUrl.size() > 1) {
                cheetahResult.addWaitRequest(nextUrl.get(nextUrl.size() - 1));
            }

            Selectable playInfo = page.getHtml().$("#song-list-pre-cache ul");
            List<String> songUrls = playInfo.getLinks();
            cheetahResult.addWaitRequest(songUrls);
            cheetahResult.setSkip(true);
        }

    }

    @Override
    public SiteConfig setAndGetSiteConfig() {
        this.siteConfig.setDomain("http://music.163.com")
                .setStartUrl("http://music.163.com/discover/playlist?cat=%E5%8D%8E%E8%AF%AD")
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
                .addHeader("Accept-Language", "zh-CN, zh; q=0.8, en; q=0.6")
                .setThreadSleep(2000)
                .setThreadNum(3)
                .setJsonAPIUrl("http://music.163.com/weapi/v1/resource/comments/R_SO_4_5051245?csrf_token=")
                .setStartJSONAPI(true);
//                .openBreakRestart(true)
//                .setBreakRedisConfig("127.0.0.1");
        return siteConfig;
    }

    @Override
    public void processJSON(JsonDataResult jsonData, CheetahResult cheetahResult) {
        List<MusicContent> lists = new ArrayList<>();
        Map<String, Object> commentData = jsonData.parseMap();
        int commentNum = (Integer) commentData.get("total");
        List<Map<String, Object>> listData = (List<Map<String, Object>>) commentData.get("hotComments");
        listData.forEach(map -> {
            Map<String, String> user = (Map<String, String>) map.get("user");
            String nickName = user.get("nickname");
            int likeNum = (Integer) map.get("likedCount");
            String content = (String) map.get("content");
            MusicContent musicContent = new MusicContent(nickName, likeNum, content);
            lists.add(musicContent);
        });
        cheetahResult.putField("hotComments", lists);
        cheetahResult.putField("commentNum", commentNum);
    }

    @Override
    public Request updateJSONConfig(CheetahResult cheetahResult, SiteConfig siteConfig) {
        if (cheetahResult == null) {
            return null;
        }
        String url = siteConfig.getJsonAPIUrl();
        Map<String, Object> music = cheetahResult.getResults().get(0);
        String newUrl = "";
        if (music != null) {
            String oldStr = url.substring(url.lastIndexOf("R_SO_4_") + 7, url.lastIndexOf("?"));
            String newStr = (String) music.get("id");
            newUrl = url.replace(oldStr, newStr);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("params", "2XrYzouNKhZ/ewhVWpTNvNd5kpXAf/QR5moeaTShm0ch2QK/96FpuE2SlEj5BcmeNSvfP+m2KKcOB/aGV9nGLiUwXkbzR88sbEs0UIKcAVDXsQ/84gPB1b12gekAp6vQL6vekQP6aXKx9bSMSzBPXchRhz1+ESQwAOmGaQ2YfniEuB9W6hUScyOT16vlujOapKIQKo5CKoM5pqP2JVAIS828fsuGNIf2LA3clcq4/1Y=");
        paramMap.put("encSecKey", "7dad56c9971eaa36785ef9ecc956597f184d72bf29de68671d5ac5e188d5dd248cbb20a32ff02a6c5959e750d215c71607067d9c4c50856664b1b07ea280946fb954e6341f9d6af340632c61af71c7d30b7e72ded33a56570345bbc972cc06203587174a003ce9bc390d0c59235e4215f54b4f1640bc87f4857bd9c5f3cbde0b");

        return new Request(newUrl, paramMap, RequestMethod.POST);

    }

    public static void main(String[] args) {
        Cheetah.create(new Music163Demo())
                .setHandler(new ConsoleHandler())
                .setHandler(new ElasticHandler("127.0.0.1", 9300, "wth-elastic", "music_test2", "Netease"))
//                .setHandler(new RedisHandler("127.0.0.1", "music163_2"))
                .run();
    }

    public class MusicContent {

        public MusicContent(String nickName, int likeNum, String content) {
            this.nickName = nickName;
            this.content = content;
            this.likeNum = likeNum;

        }

        private String nickName;
        private int likeNum;
        private String content;

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("MusicContent{");
            sb.append("nickName='").append(nickName).append('\'');
            sb.append(", likeNum=").append(likeNum);
            sb.append(", content='").append(content).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }



}
