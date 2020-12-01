package com.metis.paas;

/**
 * @Description: OddEvenPrinter
 * @Author: YiHui
 * @Date: 2020-11-24 15:13
 * @Version: ing
 */
public class OddEvenPrinter {
    private final int limit;
    private Object monitor = new Object();
    private volatile int count;

    OddEvenPrinter( int initCount, int limit ){
        this.count = initCount;
        this.limit = limit;
    }

    public static void main(String[] args) {
        OddEvenPrinter printer = new OddEvenPrinter(0,200);
        new Thread( printer::print, "odd").start();
        new Thread( printer::print,"even").start();
//        new Thread( printer::print,"???").start();
    }

    private void print(){
        synchronized ( monitor ){
            while( count < limit ){
                try{
                    System.out.println( String.format("线程[%s]打印数字:%d", Thread.currentThread().getName(),++count));
                    monitor.notifyAll();
                    monitor.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                防止有子线程被阻塞未被唤醒,导致主线程不退出
                monitor.notifyAll();
            }
        }
    }
}
