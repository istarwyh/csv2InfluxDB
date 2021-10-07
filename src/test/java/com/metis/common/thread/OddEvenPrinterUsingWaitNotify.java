package com.metis.common.thread;

/**
 * @Description: OddEvenPrinter
 * @Author: YiHui
 * @Date: 2020-11-24 15:13
 * @Version: ing
 */
public class OddEvenPrinterUsingWaitNotify{
    private final int    limit;
    private final Object monitor = new Object();
    private volatile int count;

    public OddEvenPrinterUsingWaitNotify (int initCount, int limit) {
        this.count = initCount;
        this.limit = limit;
    }

    public void print() {
        synchronized (monitor) {
            while (count < limit) {
                try {
                    System.out.printf("线程[%s]打印数字:%d%n", Thread.currentThread().getName(), ++count);
                    monitor.notifyAll();
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    //                防止有子线程被阻塞未被唤醒,导致主线程不退出
                monitor.notifyAll();
            }
        }
    }
}
