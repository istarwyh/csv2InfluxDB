package com.metis.common.thread;

/**
 * @Description: PrintABCUsingWaitNotify
 * @Author: YiHui
 * @Date: 2020-11-24 15:03
 * @Version: ing
 */
public class PrintABCUsingWaitNotify {
    private final Object monitor = new Object();
    private int                 state;
    private final int                 times;

    public PrintABCUsingWaitNotify(int times) {
        this.times = times;
    }

    public void printLetter(String name, int targetState) {
        for (int i = 0; i < times; i++) {
            synchronized (monitor) {
                while (state % 3 != targetState) {
                    try {
                       monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                state++;
                System.out.print(name + " ");
                monitor.notifyAll();
            }
        }
    }
}
