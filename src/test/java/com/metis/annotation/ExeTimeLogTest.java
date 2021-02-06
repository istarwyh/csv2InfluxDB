package com.metis.annotation;

import javax.annotation.Resource;

import com.github.istarwyh.Array;
import com.github.istarwyh.ListNode;
import com.metis.paas.SolutionTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.metis.annotation.log.LogExeTimeAspect;

/**
 * @Description: ExeTimeLogTest
 * @Author: YiHui
 * @Date: 2021-02-04 19:25
 * @Version: ing
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExeTimeLogTest {
    @Resource
    private FooService service;
    @Resource
    private LogExeTimeAspect aspect;
    /**
     * 这里在运行时注入的是代理后的SolutionTestProxy的对象
     */
    @Resource
    private SolutionTest st;

    @Test
    public void test1(){
            service.doSomething();
            System.out.println("-----------------");
            System.out.println(aspect.getMessages());
    }

    @Test
    public void test2(){
        service.doSomethingWrong();
        System.out.println(aspect.getMessages());
        System.out.println(aspect.getExceptions());
    }

    @Test
    public void test3(){
        ListNode l1 = ListNode.createListNodeByArray(Array.getArr(1, 2, 3, 4));
        st.reorderList(l1);
        System.out.println(l1);
    }
}
