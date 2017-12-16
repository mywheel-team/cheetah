package org.togethwy.cheetah;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.downloader.*;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.Handler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.redis.RedisHelper;
import org.togethwy.cheetah.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 爬虫框架入口类
 *
 * @author wangtonghe
 * @date 2017/7/3 09:09
 */
public class Cheetah implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Cheetah.class);

    private PageProcessor pageProcessor;

    private Downloader downloader = new HttpDownloader();

    private Downloader jsonDownloader = new JsonDownloader();

    private List<Handler> handlers = new ArrayList<>();

    private SiteConfig siteConfig;

    private Queue<Request> waitRequests = new LinkedBlockingQueue<>();

    private Set<Integer> waitReqBackup =  ConcurrentHashMap.newKeySet();

    private RedisHelper redisCacheHelper;


    private String redisWaitKey;

    private String redisSaveKey;


    private final static String wait_prefix = "wait_url_key_";

    private final static String save_prefix = "save_url_key_";


    private Cheetah(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.siteConfig = pageProcessor.setAndGetSiteConfig();

        String destClassName = pageProcessor.getClass().getSimpleName().toLowerCase();
        this.redisSaveKey = save_prefix + destClassName;
        this.redisWaitKey = wait_prefix + destClassName;

    }

    public static Cheetah create(PageProcessor pageProcessor) {
        return new Cheetah(pageProcessor);
    }


    public Cheetah setHandler(Handler handler) {
        this.handlers.add(handler);
        return this;
    }

    /**
     * 爬虫起始方法
     */
    public void run() {
        ThreadPool threadPool = new ThreadPool(siteConfig.getThreadNum());
        prepareWaitUrl();

        while (!Thread.currentThread().isInterrupted()) {  //响应中断
            //从请求队列拿一条请求url
            Request request = waitRequests.poll();
            if (request == null) {
                //若没有活跃线程且待爬列表为空，停止任务
                if (threadPool.getAliveThreadNum() == 0 && waitRequests.size() == 0) {
                    threadPool.shutdown();
                    break;
                }
                try {
                    Thread.sleep(siteConfig.getThreadSleep());
                } catch (InterruptedException e) {
                    logger.error("cheetah sleep", e);
                }
                continue;
            }
            //判断是否开启断点重爬
            if (siteConfig.isBreakRestart()) {
                redisCacheHelper.removeFromSet(redisWaitKey, request.getUrl()); //将待爬url从redis删除
                redisCacheHelper.add2Set(redisSaveKey, request.getUrl()); //记录爬取过的url
            }
            threadPool.execute(() -> {
                //页面抓取
                Page page = (Page) downloader.download(request, siteConfig);
                CheetahResult cheetahResult = new CheetahResult(page.getUrl());
                if (page.getStatusCode() == Constant.STATUS_CODE_200) {
                    pageProcessor.process(page, cheetahResult);  //处理页面
                }

                //jsonAPI方式
                if (siteConfig.isStartJSONAPI() && cheetahResult.isStartJsonAPI()) {
                    Request jsonConfig = pageProcessor.updateJSONConfig(cheetahResult, siteConfig);
                    JsonDataResult result = (JsonDataResult) jsonDownloader.download(new Request(jsonConfig), siteConfig);
                    pageProcessor.processJSON(result, cheetahResult);
                }
                handleWaitRequest(cheetahResult);
                if (!cheetahResult.isSkip()) {
                    handleResult(cheetahResult); //处理结果
                }
                try {
                    Thread.sleep(siteConfig.getThreadSleep());
                } catch (InterruptedException e) {
                    logger.error("cheetah run", e);
                }
            });
        }
        logger.debug("爬取结束，共获取到{}条数据，",waitReqBackup.size());
    }


    /**
     * 准备待爬url,若开启断点重爬，将之前未爬url加入队列，否则加入开始url
     */
    private void prepareWaitUrl() {
        if (siteConfig.isBreakRestart()) {
            redisCacheHelper =  new RedisHelper("127.0.0.1");
            Set<String> waitUrls = redisCacheHelper.getAllFromSet(redisWaitKey);
            waitUrls.forEach(waitUrl -> {
                waitRequests.add(new Request(waitUrl));
            });
            if (waitRequests.size() == 0) {
                waitRequests.add(new Request(siteConfig.getStartUrl()));
            }
        } else {
            waitRequests.add(new Request(siteConfig.getStartUrl()));
        }

    }

    private void handleResult(CheetahResult cheetahResult) {
        //默认使用Console处理结果，即打印到控制台
        if (handlers == null) {
            handlers.add(new ConsoleHandler());
        }
        handlers.forEach(e -> e.handle(cheetahResult));
    }

    private void handleWaitRequest(CheetahResult cheetahResult) {
        Set<Request> waitAddUrl = cheetahResult.getWaitRequests();
        List<String> waitUrlList = new ArrayList<>();
        if (waitAddUrl != null && waitAddUrl.size() > 0) {
            waitAddUrl.forEach(request -> {
                String url = request.getUrl();
                if (!waitReqBackup.contains(url.hashCode())) {
                    waitReqBackup.add(url.hashCode());
                    waitRequests.add(request);
                    waitUrlList.add(url);
                }
            });
            //开启断点重爬
            if (siteConfig.isBreakRestart()) {
                RedisHelper.getInstance(siteConfig.getBreakRedisHost(), siteConfig.getBreakRedisPort())
                        .add2Set(redisWaitKey, waitUrlList);
            }
        }
    }

}
