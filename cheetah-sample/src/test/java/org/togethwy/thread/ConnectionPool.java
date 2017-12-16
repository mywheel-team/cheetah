package org.togethwy.thread;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author wangtonghe
 * @date 2017/12/9 23:08
 */
public class ConnectionPool {


    private final LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int size){

        for (int i = 0; i < size; i++) {
            pool.addLast(ConnectionDriver.getConnection());

        }
    }



    public Connection getConnection(long timeout) throws InterruptedException {
        synchronized (pool) {
            long start = System.currentTimeMillis();
            long future = start + timeout;
            Connection connection = null;
            while (pool.isEmpty() && future > System.currentTimeMillis()) {
                pool.wait(timeout);
            }
            if (!pool.isEmpty()) {
                connection = pool.removeFirst();
            }
            return connection;
        }
    }

    public void release(Connection connection) {
        if(connection!=null){
            synchronized (pool) {
                pool.addLast(connection);
                pool.notify();
            }
        }
    }
}
