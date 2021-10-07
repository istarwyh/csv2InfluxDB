package com.metis.common;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.github.istarwyh.UserRejectHandler;
import com.github.istarwyh.UserThreadFactory;
import com.metis.common.thread.OddEvenPrinterUsingWaitNotify;
import com.metis.common.thread.PrintABCUsingLock;
import com.metis.common.thread.PrintABCUsingWaitNotify;

/**
 * @Description: ThreadTest
 * @Author: wx:istarwyh
 * @Date: 2021-04-11 19:41
 * @Version: ing
 */
public class ThreadTest {
    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(12, 12, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(30),
            new UserThreadFactory("MBinPC"), new UserRejectHandler());

    @Test
    void testLock() {
        var p1 = new PrintABCUsingLock(12, "A", 0);
        var p2 = new PrintABCUsingLock(12, "B", 1);
        var p3 = new PrintABCUsingLock(12, "C", 2);
        // 这个可行,因为是由同一个实例对象持有的同一个代码块
        // 每个线程自己从获取锁到执行一次函数的过程中都不会被抢占
        // 结合 state % 3 == targetNum 与 state++保证了总会轮到某个线程执行代码块
        // i++在里面保证了每一次如果线程不执行,它就一直尝试获取锁
        threadPool.execute(() -> p1.printLetter("A", 0));
        threadPool.execute(() -> p1.printLetter("B", 1));
        threadPool.execute(() -> p1.printLetter("C", 2));
        // 这个不可行,因为不同的实例对象不共享AQS的state,无法预料线程执行顺序
        // threadPool.execute(p1.task);
        // threadPool.execute(p2.task);
        // threadPool.execute(p3.task);
    }

    /**
     * 这个的问题在于notify唤醒没有针对性,因此只适用于两个的情况 LockCondition可解决这个问题
     */
    @Test
    void testWaitNotify1() {
        OddEvenPrinterUsingWaitNotify printer = new OddEvenPrinterUsingWaitNotify(0, 200);
        threadPool.execute(printer::print);
        threadPool.execute(printer::print);
    }

    @Test
    void testWaitNotify2() {
        PrintABCUsingWaitNotify p1 = new PrintABCUsingWaitNotify(10);
        threadPool.execute(() -> p1.printLetter("A", 0));
        threadPool.execute(() -> p1.printLetter("B", 1));
        threadPool.execute(() -> p1.printLetter("C", 2));
    }

    /**
     * 消费者: AQS::addConditionalWaiter()操作等待队列里的结点,结点中存放着thread-3/2/1 生产者:
     * ThreadPoolExecutor::execute中workQueue.offer()将task放入队列中,并通过signalNotEmpty()->doSignal->transFerSignal
     * ->LockSupport.unpark(node.thread)唤醒线程 线程被唤醒的顺序就是等待队列中结点的顺序,所以最后task会被轮询执行
     */
    @Test
    public void testKeepAlive() throws InterruptedException {
        var threadPool2 = new ThreadPoolExecutor(2, 3, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2),
                new UserThreadFactory("MBinPC"), new UserRejectHandler());

        // //每隔两秒打印线程池的信息
        // ScheduledExecutorService scheduledExecutorService =
        // Executors.newScheduledThreadPool(1);
        // scheduledExecutorService.scheduleAtFixedRate(() -> {
        // System.out.println("=====================================thread-pool-info:" +
        // new Date()
        // + "=====================================");
        // System.out.println("CorePoolSize:" + threadPool2.getCorePoolSize());
        // System.out.println("PoolSize:" + threadPool2.getPoolSize());
        // System.out.println("ActiveCount:" + threadPool2.getActiveCount());
        // System.out.println("KeepAliveTime:" +
        // threadPool2.getKeepAliveTime(TimeUnit.SECONDS));
        // System.out.println("QueueSize:" + threadPool2.getQueue().size());
        // }, 0, 2, TimeUnit.SECONDS);

        try {
            // 1. 同时提交5个任务,模拟达到最大线程数
            for (int i = 0; i < 6; i++) {
                threadPool2.execute(new Task());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 休眠10秒，打印日志，观察线程池状态
        Thread.sleep(10000);

        // 2. 每隔3秒提交一个任务
        while (true) {
            Thread.sleep(3000);
            threadPool2.submit(new Task());
        }
        // threadPool2.awaitTermination(60,TimeUnit.SECONDS);
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "-执行任务");
        }
    }
}
