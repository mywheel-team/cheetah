# cheetah

> cheetah是一个开源的java爬虫框架，定位目标为易于使用、侧重数据分析与展示。整体架构与思路相当程度借鉴了当下最有名的java爬虫[webmagic](https://github.com/code4craft/webmagic),在此表示感谢。


## 整体架构

cheetah依赖[httpclient](http://hc.apache.org/)进行请求的封装和下载，使用[jsoup](https://github.com/jhy/jsoup/)进行网页解析，使用[fastjson](https://github.com/alibaba/fastjson)进行json的转换操作。

项目使用[slf4j](https://github.com/qos-ch/slf4j)日志框架，您可以自由选择其他日志框架与之配合。

cheetah分为3个子模块。分别是 `cheetah-core`、`cheetah-datastore`、`cheetah-sample`。介绍如下：

- **cheetah-core**

  cheetah核心包，包括下载器、选择器、结果处理器等爬虫基本元素。
- **cheetah-datastore**

  cheetah的数据处理与储存模块。你可以选择合适的储存介质。目前支持`ElasticSearch`、`Redis`储存。
- **cheetah-sample**

  爬虫示例，如[知乎](https://www.zhihu.com/)、[豆瓣电影](https://movie.douban.com/)、[网易云音乐](http://music.163.com/)等知名网站的爬虫demo。


## 快速开始

1. 首先需要下载项目，将项目下载到本地后使用IDEA或其他IDE打开。
2. 在`cheetah-sample`模块中有`org.togethwy.sample.common`包，里面是各个网站爬虫示例。这里选`DoubanMovieDemo`这个类，按照下面所示，运行里面的`main()`方法，即可在控制台看到爬取的电影评分等信息。


`DoubanMovieDemo.main()`方法如下所示，**注意运行时需注释掉有关`Elasticsearch`和`redis`配置相关数据**（因为我们还没有配置）。

```java
    public static void main(String[] args) {
        Cheetah.create(new DoubanMovieDemo())
                .setHandler(new ConsoleHandler())
//                .setHandler(new ElasticHandler("localhost", 9300, "wth-elastic", "cheetah_new", "movie"))
//                .setHandler(new RedisHandler("127.0.0.1", "douban_movie_4"))
                .run();
    }

```

## 示例讲解

这里通过一个示例来讲解怎样使用`cheetah`框架来爬取网站。爬虫类大致结构和[webmagic](https://github.com/code4craft/webmagic)类似，所以如果您熟悉webmagic的话，会很容易上手。这里我们选取知乎，用来分析各个知友的粉丝数和关注人数。

### 创建一个实现类

所有的爬虫类都需要实现`PageProcessor`这个接口。我们把爬虫类命名为`ZhihuCrawler`,目前像是这样：

```java
public class ZhihuCrawler implements PageProcessor{
    
    @Override
    public void process(Page page, CheetahResult result) {
        
    }

    @Override
    public SiteConfig setAndGetSiteConfig() {
        return null;
    }
}

```
这里有两个必须实现的方法：`process() `和`setAndGetSiteConfig()`。

`process()`方法用于解析你需要爬取的网站并处理其结果，`setAndGetSiteConfig`用于设置网站的userAgent、cookie及爬取速度等配置。

### 编写process方法

编写process()主要根据各个网站的网页元素。

第一个参数page储存了爬取下来的html页面、该页面url等信息。通过`page.getHtml()`方法可得到页面数据。`Html`对象包含了若干jquery格式的选择器用于获取数据。

第二个参数用于储存爬取而来的结果及待爬URL。


```java

@Override
    public void process(Page page, CheetahResult cheetahResult) {

        String name = page.getHtml().$(".ProfileHeader-title .ProfileHeader-name").getValue();

        //如果name为空，说明爬取的当前页没有我们要的数据，所以将它过滤，直接返回
        if (StringUtils.isEmpty(name)) {
            cheetahResult.setSkip(true);
            return;
        }
        //解析网页
        Map<String, Object> result = new HashMap<>();
        Html follow = page.getHtml().$(".Profile-main .Profile-sideColumn .FollowshipCard .FollowshipCard-counts a");
        int followNum = Integer.parseInt(follow.get(0).$(".NumberBoard-value").getValue());
        int followerNum =  Integer.parseInt(follow.get(1).$(".NumberBoard-value").getValue());
        int answerNum = Integer.parseInt(page.getHtml().$(".Profile-mainColumn .ProfileMain-header ul li").get(1).$(".Tabs-meta").getValue());
        int quesNum = Integer.parseInt(page.getHtml().$(".Profile-mainColumn .ProfileMain-header ul li").get(2).$(".Tabs-meta").getValue());

        //将解析结果放在CheetahResult对象中
        result.put("name", name);
        result.put("answerNum",answerNum);
        result.put("quesNum", quesNum);
        result.put("following", followNum);
        result.put("follower", followerNum);
        cheetahResult.putResult(result);

        //将下次需要爬的url储存在CheetahResult对象中，你可以通俗认为是下一页的url
        List<String> links = page.getHtml().$(".Profile-mainColumn .List > div").get(1).$(".List-item .ContentItem-head .Popover").getLinks();
        links.forEach(link -> {
            String newLink = link + "/followers";
            cheetahResult.addWaitRequest(newLink);
        });

    }

```

### 配置setAndGetSiteConfig方法

`setAndGetSiteConfig`方法是爬虫的配置方法。这个基本见文知义，看示例吧。

```java
    @Override
    public SiteConfig setAndGetSiteConfig() {

        SiteConfig siteConfig = SiteConfig.create();

        siteConfig.setDomain("https://www.zhihu.com") //设置网站域名
                .setStartUrl("https://www.zhihu.com/people/zhang-jia-wei/followers") //设置爬虫起始url
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
                .addHeader("Accept-Language", "zh-CN, zh; q=0.8, en; q=0.6")
                .setThreadSleep(2000)  //设置线程休眠时间，单位ms
                .setThreadNum(3);  //设置线程数
        return siteConfig;
    }

```

### 开启爬虫

开启爬虫很简单，写个主函数即可。

```
   public static void main(String[] args) {
        Cheetah.create(new ZhihuCrawler()) //传入爬虫类示例对象
                .setHandler(new ConsoleHandler())  //设置爬取结果处理方法，这里为显示在控制台
                .run();
    }

```

开启后即可在控制台看到爬取到的结果。


## 项目特性

以上示例只是简单的爬虫demo,本项目针对爬取时遇到的问题做了一些优化及处理。主要体现在：

- **网页解析与API获取相结合**

  即爬取网站时可从网页中解析出数据，也可以同时根据JSON API获取结果。具体可参考`DoubanMovieDemo`、`Music163Demo`示例。

- **断点续爬功能**
  
  本项目提供了断点续爬功能，即将本次爬取进度保存下来，下次可以从上次断开的地方重新爬取。注意本功能需要redis支持，具体可参考`DoubanMovieDemo`示例。

- **定时器功能**

  即可自己指定时间，让爬虫在指定事件运行。具体示例可参考`TimerDemo`。
  
  
## 关于文档

文档详情见 [gitbook-cheetah](https://wangtonghe.gitbooks.io/cheetah/)

## 关于项目

这是本人的一个业余项目。非常欢迎大家参与贡献代码或提Issues,一起讨论交流，邮件地址`wthfeng@126.com`。若是觉得不错，可以star以鼓励。大家共勉。



 



