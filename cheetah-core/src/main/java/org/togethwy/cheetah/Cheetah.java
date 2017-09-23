package org.togethwy.cheetah;

import org.togethwy.cheetah.downloader.*;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.Handler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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


    private Cheetah(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.siteConfig = pageProcessor.setAndGetSiteConfig();
        this.waitRequests.add(new Request(siteConfig.getStartUrl()));
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
        while (!Thread.currentThread().isInterrupted()) {

            //从请求队列拿一条请求url
            Request request = waitRequests.poll();
            if (request == null) {
                if (threadPool.getAliveThreadNum()== 0) {
                   threadPool.shutdown();
                }
                continue;
            }
            threadPool.execute(() -> {

                if(siteConfig.isStartJSONAPI()){
                    String url = siteConfig.getJsonAPIUrl();
                    Request jsonReq = new Request(url);
                    JsonDataResult result = (JsonDataResult) jsonDownloader.download(jsonReq, siteConfig);
                    pageProcessor.processJSON(result);
                    handleResult(result);    //处理结果
                    siteConfig.setJsonAPIUrl(result.getJsonUrl());
                }

                Page page = (Page) downloader.download(request, siteConfig);
                if (page.getStatusCode() == Constant.STATUS_CODE_200) {
                    try {
                        pageProcessor.process(page);  //处理页面
                        handleResult(page);    //处理结果
                        Thread.sleep(siteConfig.getThreadSleep());
                    } catch (InterruptedException e) {
                        logger.error("threadPool InterruptedException error", e);
                    }
                }
            });
        }
    }

    private void handleResult(DownloadResult downloadResult) {
        //默认使用Console处理结果，即打印到控制台
        if (handlers == null) {
            handlers.add(new ConsoleHandler());
        }
        handlers.forEach(e -> e.handle(downloadResult.getResults()));

        if (downloadResult.getWaitRequests() != null && downloadResult.getWaitRequests().size() > 0) {
            waitRequests.addAll(downloadResult.getWaitRequests());  //添加待处理url
        }

    }

}
