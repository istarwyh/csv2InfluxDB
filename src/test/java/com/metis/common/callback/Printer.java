package com.metis.common.callback;


public class Printer {
    public void print(Callback callback) {
        System.out.println("正在打印 . . . ");
        // 模拟打印机打印的过程
        try {
            Thread.currentThread();
            Thread.sleep(3000);// 毫秒
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 打印完了之后回调
        callback.printFinished("打印完成");
    }

}
