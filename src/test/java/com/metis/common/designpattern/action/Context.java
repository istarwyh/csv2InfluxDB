package com.metis.common.designpattern.action;


import java.util.Objects;

/**
 * @author xiaohui.wyh
 * @version context v0.1
 * @Date 2021/9/9 7:35 下午
 */
public class Context {
    private Strategy strategy;
    /**
     * 选择策略和策略本身到底应该怎样组合呢？
     */
    private StrategyNode strategyChainHead;

    {
        this.strategyChainHead = new StrategyNode(new DefaultStrategy());
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void setStrategyChainHead(Strategy... strategies){
        if(Objects.isNull(strategies)){
            return;
        }
        StrategyNode preHead = this.strategyChainHead;
        StrategyNode node = preHead;
        for (Strategy strategy : strategies) {
            node.next = new StrategyNode(strategy);
            node = node.next;
        }
        this.strategyChainHead = preHead.next;
    }

    public void executeStrategy(int a,int b){
        int res = strategy.execute(a,b);
        System.out.println(res);
    }

    public void executeStrategyChain(int a,int b){
        StrategyNode node = strategyChainHead;
        while (Objects.nonNull(node)){
            int res = node.strategy.execute(a, b);
            System.out.println(res);
            node = node.next;
        }
    }

    public Strategy defaultStrategy(){
        return new DefaultStrategy()::execute;
    }

    public static class DefaultStrategy implements Strategy{
        @Override
        public int execute(int a, int b) {
            return 0;
        }
    }

}
