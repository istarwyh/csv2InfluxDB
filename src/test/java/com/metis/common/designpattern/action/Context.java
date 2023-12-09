package com.metis.common.designpattern.action;


import java.util.Objects;

/**
 * @author xiaohui.wyh
 * @version context v0.1
 * @Date 2021/9/9 7:35 下午
 */
public class Context {
    private Strategy strategy;

    private StrategyNode strategyChainHead;

    {
        this.strategyChainHead = new StrategyNode(new DefaultStrategy());
    }

    /**
     * todo 只用一个Strategy 演示不足以说明策略模式和责任链模式的区别
     */
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }
    // 放入的是策略接口,不涉及判断从而一路往下执行的就是组合模式
    // 加入了判断就可以为责任链模式
    // 这里只是链表,但可以做成复杂的二叉树乃至多叉树
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
        return new DefaultStrategy();
    }

    public static class DefaultStrategy implements Strategy{
        @Override
        public int execute(int a, int b) {
            return 0;
        }
    }

}
