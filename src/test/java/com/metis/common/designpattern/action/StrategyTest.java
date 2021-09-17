package com.metis.common.designpattern.action;

import java.util.Scanner;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author xiaohui.wyh
 * @version ExampleApplication v0.1
 * @Date 2021/9/9 8:18 下午
 */
@Log4j2
public class StrategyTest {
    static Context context = new Context(); 

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        log.warn("Please input two numbers with 'Enter'");
        int para1 = Integer.parseInt(in.next());
        int para2 = Integer.parseInt(in.next());


        Handler1 handlerHead = new Handler1();
        Handler2 handler2 = new Handler2();
        Handler3 handler3 = new Handler3();
        constructHanlderChain(handlerHead, handler2, handler3);
        // 责任链模式,当一个判断成功时放弃后续无效判断
        handlerHead.execute(para1);
        
        // todo:不知道怎么迭代使用访问者模式啊
        Handler iter = handlerHead;

        VisitorImpl visitor = new VisitorImpl();
        handlerHead.accept(visitor);


        // 策略模式,将 控制逻辑 放在外面,以Context中的属性做基本 数据结构 做 业务逻辑 的容器
        // 符合开闭原则,有新的策略时添加新的策略与策略的判断逻辑即可
        context.executeStrategy(para1, para2);

        // context.setStrategyChainHead(add(),cut(),multiply());
        // context.executeStrategyChain(1,2);
        // context.executeStrategyChain(2,2);
        // context.executeStrategyChain(3,2);

        in.close();
        ;
    }

    private static void constructHanlderChain(Handler1 handlerHead, Handler2 handler2, Handler3 handler3) {
        handlerHead.next = handler2;
        handler2.next = handler3;
    }

    private static Strategy add() {
        return Integer::sum;
    }

    private static Strategy cut() {
        return (a, b) -> a - b;
    }

    private static Strategy multiply() {
        return (a, b) -> a * b;
    }

    private interface Handler {
        void execute(int flag);

        /**
         * "门"中可以封装很复杂的业务逻辑，以判断输入的参数是否足以开门
         */
        boolean door();

        /**
         * 访问者模式根据不同的 visitor 选择对应的Handler实现者做事
         */
        void accept(Visitor visitor);
    }

    @NoArgsConstructor
    private static class Handler1 implements Handler {
        Handler next;
        int flag;

        @Override
        public void execute(int flag) {
            this.flag = flag;
            if (this.door()) {
                context.setStrategy(add());
            } else {
                this.next.execute(flag);
            }
        }

        @Override
        public boolean door() {
            return flag > 1;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this, flag);
        }
    }

    @NoArgsConstructor
    private static class Handler2 implements Handler {
        Handler next;
        int flag;

        @Override
        public void execute(int flag) {
            if (door()) {
                context.setStrategy(cut());
            } else {
                this.next.execute(flag);
            }
        }

        @Override
        public boolean door() {
            return flag < 1;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this, flag);
        }
    }

    @NoArgsConstructor
    private static class Handler3 implements Handler {
        Handler next;
        int flag;

        @Override
        public void execute(int flag) {
            this.flag = flag;
            if (door()) {
                context.setStrategy(multiply());
            } else {
                throw new RuntimeException("not judge Condition: " + flag);
            }
        }

        @Override
        public boolean door() {
            return flag == 1;
        }


        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this, flag);
        
        }
    }

    private interface Visitor{
        void visit(Handler1 handler1,int flag);
        void visit(Handler2 handler2,int flag);
        void visit(Handler3 handler3,int flag);

    }
    private static class VisitorImpl implements Visitor{

        public void visit(Handler1 handler1,int flag){
            handler1.execute(flag);
        }

        public void visit(Handler2 handler2,int flag){
            handler2.execute(flag);
        }

        @Override
        public void visit(Handler3 handler3, int flag) {
            handler3.execute(flag);
        }
    }
}
