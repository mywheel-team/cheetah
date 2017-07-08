package org.togethwy.cheetah;

import org.togethwy.cheetah.downloader.Downloader;
import org.togethwy.cheetah.downloader.HttpDownloader;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.downloader.Request;
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
 * @author wangtonghe
 * @date 2017/7/3 09:09
 */
public class Crawler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);


    private PageProcessor pageProcessor;

    private Downloader downloader  = new HttpDownloader();

    private ThreadPool threadPool;

    private List<Handler> handlers = new ArrayList<>();

    private Config config;

    private Queue<Request> waitRequests = new LinkedBlockingQueue<>();


    public Crawler(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.config = pageProcessor.getConfig();
    }

    public static Crawler create(PageProcessor pageProcessor){
        return new Crawler(pageProcessor);
    }


    public Crawler setHandler(Handler handler) {
       this.handlers.add(handler);
       return this;
    }

    /**
     * 爬虫起始方法
     */
    public void run() {
        threadPool = new ThreadPool(config.getThreadNum());
        while (!Thread.currentThread().isInterrupted() ) {
            //从请求队列拿一条请求url
            Request request = waitRequests.poll();
            if (request == null) {
                if (threadPool.getAliveThreadNum()== 0) {
                    break;
                }
                continue;
            }
            threadPool.execute(() -> {
                Page page = downloader.download(request,config);
                if (page.getStatusCode() == Constant.STATUS_CODE_200) {
                    try {
                        pageProcessor.process(page);  //处理页面
                        handleResult(page);    //处理结果
                        Thread.sleep(config.getThreadSleep());
                    } catch (InterruptedException e) {
                        logger.error("threadPool InterruptedException error",e);
                    }
                }
            });
        }
    }

    private void handleResult(Page page) {
        //默认使用Console处理结果，即打印到控制台
        if (handlers == null) {
            handlers.add(new ConsoleHandler());
        }
        handlers.forEach(e-> e.handle(page.getResult()));

        if(page.getWaitRequests()!=null&&page.getWaitRequests().size()>0){
            waitRequests.addAll(page.getWaitRequests());  //添加待处理url
        }

    }

}
