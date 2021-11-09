package com.metis.annotation;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.metis.controller.Greeting;

import org.junit.jupiter.api.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * @Description: com.metis.annotation.ByteBuddyTest
 * @Author: YiHui
 * @Date: 2021-01-18 23:23
 * @Version: https://my.oschina.net/u/4657223/blog/4863547
 */
public class ByteBuddyTest {
    /**
     * 使用Instrumentation的实例去修改已有方法
     * @param inst
     * @param allClassName 需要匹配的目标类的全类名
     * @param methodName   需要匹配目标类中的方法名
     */
    public static void premain( Instrumentation inst,String allClassName,String methodName) {
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .with(AgentBuilder.PoolStrategy.Default.EXTENDED)
                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .disableClassFormatChanges();

        agentBuilder = agentBuilder
                .type(ElementMatchers.named(allClassName))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {

                        return builder.visit(
                                // 将自己的TraceAdvice织入method所在的切面
                                Advice.to(TraceAdvice.class)
                                      .on(ElementMatchers.named(methodName))
                        );
                    }
                });
        agentBuilder.installOn(inst);
    }

    /**
     * generate "greeting" program at runtimes
     * @throws Exception
     */
    @Test
    public void test_bytebuddy_define_method() throws Exception {
        Object helloWorld = new ByteBuddy()
                .subclass(Object.class)
                .name("com.metis.HelloWorld")
                .defineMethod("greeting", String.class, Modifier.PUBLIC)
                .intercept(FixedValue.value("hello world!:-)"))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();

        Method method = helloWorld.getClass().getMethod("greeting");  
        System.out.println(method.invoke(helloWorld));
    }

    /**
     * test the "premain" program
     * @throws Exception
     */
    @Test
    public void test_premain() throws Exception {
        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        // Greeting的元数据
        Class<Greeting> classOfGreeting = Greeting.class;
        // 方法所在类的全限定名
        String allClassName = classOfGreeting.getName();
        // 类中所拥有的第一个方法名。如果是空的类，那就找Object的第一个方法名--“wait”
        String methodName = classOfGreeting.getMethods()[0].getName();
        // ”增强“allClassName下的method
        premain(inst,allClassName,methodName);
        // 在增强后调用,下面第一行即classOfGreeting.getClassLoader().loadClass(allClassName)
        Method sayHello  = Class.forName(allClassName)
                // declare包括private方法
                .getDeclaredMethod(methodName, String.class);
        
	    //将此对象的 accessible 标志设置为指示的布尔值。
		//值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
		//值为 false 则指示反射的对象应该实施 Java 语言访问检查。
        sayHello.setAccessible(true);
        // "WangYiHui" === args[1],是需要传入的参数
        sayHello.invoke(new Greeting(), "WangYiHui");
    }
}
