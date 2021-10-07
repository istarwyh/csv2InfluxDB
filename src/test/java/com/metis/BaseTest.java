package com.metis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
/**
 * 通过@SpringBootTest可以在这里也使用IoC容器中的对象
 * 注解@SpringBootTest启动MetisApplication(因为被@SpringBootConfiguration被注解了吗),但并不走它的main()
 */
@SpringBootTest(classes = TestApplication.class)
public class BaseTest {
    static {
        System.setProperty("spring.profiles.active", "testing");
    }

    /**
     * 只是为了不让它报 java.lang.Exception: No runnable methods
     */
    @Test
    public void forIgnoreNoRunnableMethods(){}
}
