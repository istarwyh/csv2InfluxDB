package com.metis.paas;

import com.github.istarwyh.UserRejectHandler;
import com.github.istarwyh.UserThreadFactory;
import com.metis.paas.thread.OddEvenPrinter;
import com.metis.paas.thread.PrintABCUsingLock;
import com.metis.paas.thread.PrintABCUsingWaitNotify;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    void testLock(){
        var p1 = new PrintABCUsingLock(12, "A", 0);
        var p2 = new PrintABCUsingLock(12, "B", 1);
        var p3 = new PrintABCUsingLock(12, "C", 2);
//        这个可行,因为是由同一个实例对象持有的同一个代码块
//        每个线程自己从获取锁到执行一次函数的过程中都不会被抢占
//        结合 state % 3 == targetNum 与 state++保证了总会轮到某个线程执行代码块
//        i++在里面保证了每一次如果线程不执行,它就一直尝试获取锁
        threadPool.execute(() -> p1.printLetter("A", 0));
        threadPool.execute(() -> p1.printLetter("B", 1));
        threadPool.execute(() -> p1.printLetter("C", 2));
//      这个不可行,因为不同的实例对象不共享AQS的state,无法预料线程执行顺序
//        threadPool.execute(p1.task);
//        threadPool.execute(p2.task);
//        threadPool.execute(p3.task);
    }

    /**
     * 这个的问题在于notify唤醒没有针对性,因此只适用于两个的情况
     * LockCondition可解决这个问题
     */
    @Test
    void testWaitNotify1(){
        OddEvenPrinter printer = new OddEvenPrinter(0,200);
        threadPool.execute(printer::print);
        threadPool.execute(printer::print);
    }

    @Test
    void testWaitNotify2(){
        PrintABCUsingWaitNotify p1 = new PrintABCUsingWaitNotify(10);
        threadPool.execute(() -> p1.printLetter("A", 0));
        threadPool.execute(() -> p1.printLetter("B", 1));
        threadPool.execute(() -> p1.printLetter("C", 2));
    }
}
