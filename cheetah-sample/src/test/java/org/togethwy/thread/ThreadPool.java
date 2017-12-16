package org.togethwy.thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/12/10 11:20
 */
public class ThreadPool {

    private final LinkedList<Runnable> tasks = new LinkedList<>();

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    private List<Worker> workers;

    private ThreadPool() {
    }

    public ThreadPool createThreadPool(int threadNum) {
        ThreadPool pool = new ThreadPool();
        pool.setWorkers(new ArrayList<>(threadNum));
        return pool;
    }

    public class Worker implements Runnable {

        private volatile boolean running;

        @Override
        public void run() {
           while (running){
               synchronized (tasks){
                   while (tasks.isEmpty()){
                       try {
                           tasks.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   Runnable job = tasks.removeFirst();
                   if(job!=null){
                       job.run();
                   }
               }
           }
        }


        public void shundown(){
            running = false;
        }
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        tasks.add(runnable);
        tasks.notify();
    }


}
