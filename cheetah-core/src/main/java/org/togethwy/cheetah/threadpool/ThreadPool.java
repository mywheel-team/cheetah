package org.togethwy.cheetah.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangtonghe
 * @date 2017/7/8 19:39
 */
public class ThreadPool {

    private final int DEFAULT_KEEP_ALIVE_TIME = 5000;

    /**
     * 线程数
     */
    private int threadNum;

    private AtomicInteger taskNum = new AtomicInteger(0);


    private ExecutorService executorService;

    public ThreadPool(int threadNum) {
        this.threadNum = threadNum;
        this.executorService =new ThreadPoolExecutor(threadNum,threadNum,DEFAULT_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,new LinkedTransferQueue<>());
    }

    public ThreadPool(int threadNum,int aliveTime) {
        this.threadNum = threadNum;
        this.executorService =new ThreadPoolExecutor(threadNum,threadNum,aliveTime, TimeUnit.MILLISECONDS,new LinkedTransferQueue<>());

    }

    public ThreadPool(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(final Runnable runnable){
        executorService.execute(runnable);
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
