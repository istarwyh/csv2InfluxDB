package com.metis.common.callback;

import org.junit.jupiter.api.Test;

public class CallbackTest {

    /**
     * 同步回调:代码运行到某一个位置的时候，如果遇到了需要回调的代码，会在这里等待，等待回调结果返回后再继续执行。
     */
    @Test
    public void syncTest(){
        People people = new People();
        System.out.println("需要打印的内容是 ---> " + "打印一份简历");
        people.goToPrintSyn();
        System.out.println("我在等待 打印机 给我反馈");
    }

    /**
     * 异步回调:代码执行到需要回调的代码的时候，并不会停下来，而是继续执行，当然可能过一会回调的结果会返回回来。
     * 
     * @Warning:这里使用@Test会导致新起的线程并不能回到那时候已经死了的asyncTest线程
     */
    @Test
    public void asyncTest(){
        People people = new People();
        System.out.println("需要打印的内容是 ---> " + "打印一份简历");
        // 代码执行到这一句的时候告知操作系统要新起一个线程运行，但自己并不会停下来
        people.goToPrintASyn(people::printFinished);
        System.out.println("我在等待 打印机 给我反馈");
    }
}
