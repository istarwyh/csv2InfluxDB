package com.metis.common.designpattern.action;


/**
 * @author xiaohui.wyh
 * @version StrategyNode v0.1
 * @Date 2021/9/9 9:08 下午
 */
public class StrategyNode {
    public StrategyNode next;
    public Strategy strategy;


    public StrategyNode(Strategy strategy) {
        this.strategy = strategy;
    }
}


