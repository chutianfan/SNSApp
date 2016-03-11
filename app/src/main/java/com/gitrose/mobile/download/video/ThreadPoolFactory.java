package com.gitrose.mobile.download.video;

import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolFactory extends ThreadPoolExecutor {
    private static final int POOL_SIZE = 2;
    private static final String TAG = "ThreadPoolFactory";
    private static final int corePoolSize;
    private static ThreadPoolFactory instance = null;
    private static final int poolSize = 5;
    private static final int queueSize = 10;
    private static final int waitTime = 10;
    private boolean isPaused;
    private boolean isStarted;
    private ReentrantLock pauseLock;
    private Condition unpaused;

    static {
        corePoolSize = (Runtime.getRuntime().availableProcessors() * POOL_SIZE) + poolSize;
        instance = null;
    }

    private ThreadPoolFactory(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.pauseLock = new ReentrantLock();
        this.unpaused = this.pauseLock.newCondition();
        this.isPaused = false;
        this.isStarted = false;
    }

    public static synchronized ThreadPoolFactory defaultInstance() {
        ThreadPoolFactory threadPoolFactory;
        synchronized (ThreadPoolFactory.class) {
            if (instance == null) {
                instance = createInstance();
            }
            threadPoolFactory = instance;
        }
        return threadPoolFactory;
    }

    public static synchronized ThreadPoolFactory createSingleInstance() {
        ThreadPoolFactory threadPoolFactory;
        synchronized (ThreadPoolFactory.class) {
            if (instance == null) {
                instance = createSingleThreadPool();
            }
            threadPoolFactory = instance;
        }
        return threadPoolFactory;
    }

    public static synchronized void resetInstance(int corePoolSize, int maximumPoolSize) {
        synchronized (ThreadPoolFactory.class) {
            instance.removeAllTask();
            instance = createInstance(corePoolSize, maximumPoolSize);
        }
    }

    private static ThreadPoolFactory createSingleThreadPool() {
        return new ThreadPoolFactory(poolSize, waitTime, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), Executors.defaultThreadFactory(), new DiscardOldestPolicy());
    }

    private static ThreadPoolFactory createInstance() {
        Log.d(TAG, "thread-corepollsize:" + corePoolSize);
        return new ThreadPoolFactory(corePoolSize, corePoolSize + poolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(waitTime), Executors.defaultThreadFactory(), new DiscardOldestPolicy());
    }

    private static ThreadPoolFactory createInstance(int corePoolSize, int maximumPoolSize) {
        Log.d(TAG, "thread-corepollsize:" + corePoolSize);
        return new ThreadPoolFactory(corePoolSize, maximumPoolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(waitTime), Executors.defaultThreadFactory(), new DiscardOldestPolicy());
    }

    public void pause() {
        this.pauseLock.lock();
        try {
            this.isPaused = true;
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            this.isPaused = false;
            this.unpaused.signalAll();
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void removeAllTask() {
        Log.d(TAG, "Thread pool :thread size:" + instance.getPoolSize() + ",Completed Task size:" + instance.getCompletedTaskCount() + ",Task size:" + instance.getTaskCount());
        Log.d(TAG, "BlockingQueue Runnable :" + instance.getQueue().size());
        try {
            purge();
            Log.d(TAG, "clearTask , runnable : " + instance.getQueue().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Log.d(TAG, "start Thread=======>:" + r.hashCode());
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                this.unpaused.await();
            } catch (InterruptedException e) {
                t.interrupt();
            } finally {
                this.pauseLock.unlock();
            }
        }
        if (!this.isStarted) {
            this.isStarted = true;
        }
    }

    protected void afterExecute(Runnable r, Throwable t) {
        Log.d(TAG, "Thread excute over =======>:" + r.hashCode());
        super.afterExecute(r, t);
    }
}
