package com.metis.common.designpattern.action;

import java.util.Objects;
import java.util.Scanner;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import lombok.NoArgsConstructor;

/**
 * @author xiaohui.wyh
 * @version ExampleApplication v0.1
 * @Date 2021/9/9 8:18 下午
 */
public class StrategyTest {
    private static Logger log = LoggerFactory.getLogger(StrategyTest.class);

    static Context context = new Context(); 

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        log.warn("Please input two numbers with 'Enter'");
        int para1 = Integer.parseInt(in.next());
        int para2 = Integer.parseInt(in.next());

        log.info("--------------------------------------策略模式--------------------------------------");
        // 或者放入cut()/multiply()
        context.setStrategy(add());
        // 将 控制逻辑 放在外面,以Context中的 属性 作基本 数据结构 做 业务逻辑 的容器
        // 属性可以换成复杂数据结构并且事先做缓存，比如HashMap<StrategyFlag,Strategy>,加入对应的StrategyFlag与Strategy即可
        // 符合开闭原则,有新的策略时添加新的策略与策略的判断逻辑即可
        context.executeStrategy(para1, para2);
        

        log.info("--------------------------------------责任链模式--------------------------------------");
        // 当一个判断成功时放弃后续无效判断;或者链上每一个节点都执行,但是执行逻辑不一样。
        // 第二种场景更适合用,因为本来那样的需求相对复杂。
        AbstractHandler handlerHead = new AbstractHandler.Builder()
        .addHandler(new Handler1())
        .addHandler(new Handler2())
        .addHandler(new Handler3())
        .build();
        logHandlerChain(handlerHead);
        int flag = para1;
        handlerHead.execute(flag);
        context.executeStrategy(para1, para2);

        log.info("--------------------------------------访问者模式--------------------------------------");
        Visitor visitorAll = new ConcreteVisitors();
        AbstractHandler iter = handlerHead;
        while(Objects.nonNull(iter)){
            iter.accept(visitorAll);
            context.executeStrategy(para1, para2); 

            iter = iter.next;
        }
    
        // context.setStrategyChainHead(add(),cut(),multiply());
        // context.executeStrategyChain(1,2);
        // context.executeStrategyChain(2,2);
        // context.executeStrategyChain(3,2);

        in.close();
    }

    private static void logHandlerChain(AbstractHandler head){
        if(Objects.isNull(head)){
            return;
        }
        AbstractHandler iter = head;
        while(Objects.nonNull(iter)){
            log.info(iter.toString());
            iter = iter.next;
        }
        if(Objects.isNull(iter)){
            log.info("null");
        }
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
         * 访问者模式根据不同的 visitor 选择对应的Handler处理逻辑
         */
        void accept(Visitor visitor);
    }

    /**
     * 用于构造责任链的公共抽象节点
     */
    private abstract static class AbstractHandler implements Handler {
        AbstractHandler next;
        int flag;

        public static void constructHandlerChain(AbstractHandler... handlers) {
            int len = handlers.length;
            for (int i = 0; i < len; i++) {
                if (i + 1 >= len) {
                    break;
                }
                handlers[i].next = handlers[i + 1];
            }
        }

        public static class Builder{
            private AbstractHandler head;
            private AbstractHandler tail;

            public Builder addHandler(AbstractHandler node){
                if(this.head == null){
                    this.head = this.tail = node;
                    return this;
                }
                // 链表指向下一个结点
                this.tail.next = node;
                // 把引用指向最后一个结点
                this.tail = node;
                return this;
            }
            public AbstractHandler build(){
                return this.head;
            }
        }

        @Override
        public String toString(){
            return String.format("%s ->", getClass().getSimpleName());
        }
    }

    @NoArgsConstructor
    private static class Handler1 extends AbstractHandler {

        @Override
        public void execute(int flag) {
            this.flag = flag;
            if (this.door()) {
                log.info("I am "+ getClass().getSimpleName()+ " add()");
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
            visitor.visit(this);
        }
    }

    @NoArgsConstructor
    private static class Handler2 extends AbstractHandler {

        @Override
        public void execute(int flag) {
            if (door()) {
                log.info("I am "+ getClass().getSimpleName()+ " cut()");
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
            visitor.visit(this);
        }
    }

    @NoArgsConstructor
    private static class Handler3 extends AbstractHandler {

        @Override
        public void execute(int flag) {
            this.flag = flag;
            if (door()) {
                log.info("I am "+ getClass().getSimpleName()+ " multiply()");
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
            visitor.visit(this);
        
        }
    }

    /**
     * 访问者模式诞生于这样一个困境：
     * Handler1、2、3相关的代码都是老代码，最好不要动，但是又要给它们加上新功能；
     * 所以给它们在统一的Visitor中加上新功能，并且当handler遇到visitor时都知道怎样“接待”这个visitor
     * -- 也就是执行它们各自的新功能
     */
    private interface Visitor{

        void visit(Handler1 handler1);
        void visit(Handler2 handler2);
        void visit(Handler3 handler3);
    }
    private static class ConcreteVisitors implements Visitor{

        /**
         * 写死的数字代表具体想要执行的逻辑
         */
        public void visit(Handler1 handler1){
            handler1.execute(2);
        }

        public void visit(Handler2 handler2){
            handler2.execute(0);
        }

        @Override
        public void visit(Handler3 handler3) {
            handler3.execute(1);
        }
    }
}
