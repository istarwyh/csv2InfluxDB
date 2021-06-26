package com.metis.common.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: PrintABCUsingLock
 * @Author: YiHui
 * @Date: 2020-11-24 14:35
 * @Version: ing
 */
public class PrintABCUsingLock {
    protected final Task task;
    /**
     * 控制打印次数
     */
    private final int  times;
    private final Lock       lock  = new ReentrantLock();
    /**
     * 当前状态值,保证三个线程交替打印
     */
    private  int        state = 0;
    private int        count = 0;

    public PrintABCUsingLock(int times, String printObject, int targetId) {
        this.times = times;
        this.task = new Task(printObject, targetId);
    }

    public void printLetter(String name, int targetNum) {
        for (int i = 0; i < times;) {
            lock.lock();
            try {
                if (state % 3 == targetNum) {
                    state++;
                    i++;
                    System.out.print(name + " ");
                }
//                i++;
//            System.out.println("count: " + count++);
            } finally {
                lock.unlock();
            }
        }
//        todo:下面这段代码为什么不行?
//         已知应该是Thread-C拿不到state变为2的值,因为加上volatile是可行的(虽然不是原子操作)
//            因为需要保证state的可见性吗?
//        for (int i = 0; i < times;) {
//            if (state % 3 == targetNum){
//                lock.lock();
//                try {
//                    state++;
//                    System.out.print(name + " ");
//                    i++;
//                    System.out.println("state: " + state);
//                } finally {
//                    lock.unlock();
//                }
//            }
//        }
    }

    class Task implements Runnable {
        private final String printObject;
        private final int    targetId;

        public Task(String printObject, int targetId) {
            this.printObject = printObject;
            this.targetId = targetId;
        }

        @Override
        public void run() {
            printLetter(printObject, targetId);
        }

        private void printLetter(String name, int targetNum) {
            for (int i = 0; i < times;) {
                lock.lock();
                try {
                    if (state % 3 == targetNum) {
                        state++;
                        i++;
                        System.out.print(name + " ");
                    }
//                    System.out.println("count: " + count++);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
