package com.metis.paas;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: PrintABCUsingLock
 * @Author: YiHui
 * @Date: 2020-11-24 14:35
 * @Version: ing
 */
public class PrintABCUsingLock {
    private final Lock lock = new ReentrantLock();
    /**
     * 控制打印次数
     */
    private int times;
    /**
     * 当前状态值,保证三个线程交替打印
      */
    private int state = 0;

    public PrintABCUsingLock( int times ){
        this.times = times;
    }

    public static void main(String[] args) {
//        三个线程抢锁,但是state%3的初始值为0,只有AThread是满足条件的
        PrintABCUsingLock loopThread = new PrintABCUsingLock(10);
            new Thread( ()->{
                loopThread.printLetter("B",1);
            },"BThread").start();

            new Thread( () -> {
                loopThread.printLetter("A",0);
            },"AThread").start();

            new Thread( ()-> {
                loopThread.printLetter("C",2);
            },"CThread").start();

    }

    private void printLetter( String name ,int targetNum ){

        for( int i=0;i<times;){
            lock.lock();
            try{
                if( state % 3 == targetNum ){
                    state++;
//                i++ 相当于当前线程执行完之后将执行权交给其他线程
                    i++;
                    System.out.print( name + " ");
                }
            }finally {
                lock.unlock();

            }
        }
    }
}
