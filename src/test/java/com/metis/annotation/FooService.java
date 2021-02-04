package com.metis.annotation;

import com.metis.annotation.log.ExeTimeLog;
import com.metis.annotation.log.LogExecutionTime;
import org.springframework.stereotype.Service;

/**
 * @Description: FooService
 * @Author: YiHui
 * @Date: 2021-02-04 19:28
 * @Version: ing
 */
@Service
public class FooService {

    @ExeTimeLog
    public void doSomething() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @LogExecutionTime
    public void doSomethingWrong() {
        try {
            Thread.sleep(500L);
            throw (new RuntimeException("something went wrong"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
