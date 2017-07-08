package org.togethwy.cheetah.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangtonghe
 * @date 2017/7/8 19:39
 */
public class ThreadPool {
    /**
     * 线程数
     */
    private int threadNum;

    private AtomicInteger taskNum = new AtomicInteger(0);


    private ExecutorService executorService;

    public ThreadPool(int threadNum) {
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    public ThreadPool(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(final Runnable runnable){
        executorService.execute(()-> runnable.run());
    }

    public void shutdown(){
        executorService.shutdown();
    }

    public boolean isShundown(){
        return executorService.isShutdown();
    }

    public int getAliveThreadNum() {
        return ((ThreadPoolExecutor) executorService).getActiveCount();
    }


}
