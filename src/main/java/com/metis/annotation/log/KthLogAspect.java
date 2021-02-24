package com.metis.annotation.log;

import com.metis.annotation.log.KthLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 这里不使用Slf4j,那么日志就不会进入日志文件,在控制台输出就是输出了
 * @author MBin_王艺辉istarwyh
 */
@Component
@Aspect
public class KthLogAspect {
    /**
     *  pointcut切点
     * 用 @Pointcut 声明自定义的注解类(@Interface) KthLog 为切面
     */
    @Pointcut("@annotation(com.metis.annotation.log.KthLog)")
    private void pointcut() {
    }

    /**
     * 在声明KthLog为切面后，通过使用 @KthLog() 就可以对请求进行拦截,并通过将这个切面映射到一个 对象 上来调用 切面类 中的方法(这里是 value() 返回值)
     * @param joinPoint 连接点,某个程序执行的某个特定未知,如某个方法的调用前/调用后/方法抛出异常后的位置
     * @param kthLogger 通过再使用 @annotation()，将com.metis.annotation.log.KthLog映射为kthLogger
     */
    @Before("pointcut() && @annotation(kthLogger)")
    public void advice(JoinPoint joinPoint, KthLog kthLogger) {
        System.out.println("--- 日志打印开始 ---");
        System.out.println("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-日志内容-[" + kthLogger.title() + "]");
    }

    @After("pointcut() && @annotation(kthLogger)")
    public void then(KthLog kthLogger) {
        System.out.printf("--- 日志[%s]打印结束 ---\n",kthLogger);
    }
}