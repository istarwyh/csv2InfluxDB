package com.metis.common.designpattern.action;

import java.util.Scanner;

import lombok.extern.log4j.Log4j2;

/**
 * @author xiaohui.wyh
 * @version ExampleApplication v0.1
 * @Date 2021/9/9 8:18 下午
 */
@Log4j2
public class StrategyTest {
    public static void main(String[] args) {
        Context context = new Context();
        Scanner in = new Scanner(System.in);
        log.warn("Please input two numbers with 'Enter'");
        int para1 = Integer.parseInt(in.next());
        int para2 = Integer.parseInt(in.next());

        // 策略模式,将 控制逻辑 放在外面，以Context中的属性做基本 数据结构 做 业务逻辑 的容器
        // 符合开闭原则，有新的策略时添加新的策略与策略的判断逻辑即可
        ConditionEnum condition = ConditionEnum.judgeBy(para1);
        if (condition == ConditionEnum.ADD) {
            context.setStrategy(add());
        } 
        if (condition == ConditionEnum.CUT) {
            context.setStrategy(cut());
        } 
        if (condition == ConditionEnum.MULTIPLY) {
            context.setStrategy(multiply());
        } 

        context.executeStrategy(para1, para2);

        // context.setStrategyChainHead(add(),cut(),multiply());
        // context.executeStrategyChain(1,2);
        // context.executeStrategyChain(2,2);
        // context.executeStrategyChain(3,2);

        in.close();
        ;
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
}
