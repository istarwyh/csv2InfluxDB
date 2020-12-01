package com.metis.paas;

/**
 * @Description: PrintABCUsingWaitNotify
 * @Author: YiHui
 * @Date: 2020-11-24 15:03
 * @Version: ing
 */
public class PrintABCUsingWaitNotify {
    private static final Object LOCK = new Object();
    private int state;
    private int times;

    public PrintABCUsingWaitNotify( int times ){
        this.times = times;
    }

    public static void main(String[] args) {
        PrintABCUsingWaitNotify loopThread = new PrintABCUsingWaitNotify(10);
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

    private void printLetter( String name ,int targetState ) {
        for( int i=0;i<times;i++ ){
            synchronized ( LOCK ){
                while ( state % 3 != targetState ){
                    try {
                        LOCK.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                state++;
                System.out.print(name+"  ");
                LOCK.notifyAll();
            }
        }
    }
}
