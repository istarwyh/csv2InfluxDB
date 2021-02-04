package com.metis.annotation;

import com.metis.annotation.log.ExeTimeLogAspect;
import com.metis.annotation.log.LogExeTimeAspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

    @Test
    public void test1() throws Exception {
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
}
