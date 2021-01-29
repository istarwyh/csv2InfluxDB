package com.metis.annotation;

import com.metis.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class KthLogAspect {
    /**
     * 用 @Pointcut 声明自定义的注解类(@Interface) KthLog 为切面
     */
    @Pointcut("@annotation(com.metis.annotation.KthLog)")
    private void pointcut() {
    }

    /**
     * 在声明KthLog为切面后，通过使用 @KthLog() 就可以对请求进行拦截,并通过将这个切面映射到一个 对象 上来调用 切面类 中的方法(这里是 value() 返回值)
     * @param kthLogger 通过再使用 @annotation()，将demo.annotation.KthLog映射为kthLogger
     */
    @Before("pointcut() && @annotation(kthLogger)")
    public void advice(JoinPoint joinPoint, KthLog kthLogger) {
        System.out.println("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-日志内容-[" + kthLogger.value() + "]");
    }

    @After("pointcut() && @annotation(kthLogger)")
    public void then(KthLog kthLogger) {
        System.out.println("--- 日志打印结束 ---");

    }

    /**
     * @param proceedingJoinPoint ProceedingJoinPoint是JoinPoint的子接口,该对象只用在@Around的切面方法中
     * @param kthLogger
     * @return 截取user对象，并修改其值。这里模拟前端从0开始计数，而后端从1开始计数的场景。在这种场景下，对后端返回的计数到前端时加1.
     * @throws Throwable
     */
    @Around(value = "pointcut() && @annotation(kthLogger)")
    public Object adviceAround(ProceedingJoinPoint proceedingJoinPoint, KthLog kthLogger) throws Throwable {
        System.out.println("--- 日志打印开始 ---");
        Object result;
        try {
            //从使用了自定义的切面注解@KthLog的方法中拿到返回值
           result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new Throwable("执行proceed()时出错");
        }
        //并对返回值进行增强处理。这里假设User的id需要从 1 以开始计数，而前端是从 0 开始计数
        if(result != null){
            if(result instanceof User){
                final User user = (User) result;
                user.setId( user.getId()+1 );
                return user;
            }else if( result instanceof List){
                final List<User> userList = (List<User>)result;
                for( User u : userList){
                    u.setId( u.getId() + 1);
                }
                return userList;
            }else{
                throw new Exception("result不是UserDO和List<UserDO>的实例（is-a)");
            }
        }else{
            throw new NullPointerException("没有找到指定编号的user");
        }
    }
}