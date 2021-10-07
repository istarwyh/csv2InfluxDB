package com.metis.common.callback;


public class People implements Callback{

    Printer printer = new Printer();

    /*
     * 同步回调
     */
    public void goToPrintSyn() {
        printer.print(this::printFinished);
    }

    /*
     * 异步回调
     */
    public void goToPrintASyn(Callback callback) {
        // 新起了一个线程
        new Thread(new Runnable() {
            public void run() {
                printer.print(callback);
            }
        }).start();
    }

    @Override
    public void printFinished(String msg) {
        System.out.println("打印机告诉我的消息是 ---> " + msg);
    
    }
}