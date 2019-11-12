package com.img.dkk.myokhttp.engine;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by  dingkangkang on 2019/10/16
 * email：851615943@qq.com
 */
public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    public void addTask(Runnable runnable){
        if(runnable != null){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ThreadPoolExecutor threadPoolExecutor;
    public ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(3, 10
            , 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
            new RejectedExecutionHandler() {
                @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    addTask(r);
                }
            });
        threadPoolExecutor.execute(ddThread);

    }

    //创建调度线程
    public Runnable ddThread = new Runnable() {
        Runnable runn = null;
        @Override public void run() {

            while (true){
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadPoolExecutor.execute(runn);
            }
        }
    };


}
