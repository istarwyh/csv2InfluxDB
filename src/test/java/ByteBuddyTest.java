import com.metis.annotation.TraceAdvice;
import com.metis.controller.Greeting;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.junit.jupiter.api.Test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Description: ByteBuddyTest
 * @Author: YiHui
 * @Date: 2021-01-18 23:23
 * @Version: https://my.oschina.net/u/4657223/blog/4863547
 */
public class ByteBuddyTest {
    public static void premain( Instrumentation inst,String allClassName,String methodName) {

        // ByteBuddy 的 API 用来修改
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .with(AgentBuilder.PoolStrategy.Default.EXTENDED)
                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .disableClassFormatChanges();

        agentBuilder = agentBuilder
                // 匹配目标类的全类名
                .type(ElementMatchers.named(allClassName))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {

                        return builder.visit(
                                // 织入切面
                                Advice.to(TraceAdvice.class)
                                        // 匹配目标类的方法
                                        .on(ElementMatchers.named(methodName))
                        );
                    }
                });
        agentBuilder.installOn(inst);
    }

    /**
     * generate "Hello World" program at runtimes
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Object helloWorld = new ByteBuddy()
                .subclass(Object.class)
                .name("com.metis.HelloWorld")
                .defineMethod("greeting", String.class, Modifier.PUBLIC)
                .intercept(FixedValue.value("hello world!:-)"))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();

        Method method = helloWorld.getClass().getMethod("greeting");
        System.out.println(method.invoke(helloWorld));
    }

    @Test
    public void test2() throws Exception {
        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();

        // 增强
        premain(inst,"com.metis.controller.Greeting","sayHello");
        // after enable --> 调用
        Class<?> greetingType = Greeting.class.
                getClassLoader().loadClass(Greeting.class.getName());
        Method sayHello = greetingType.getDeclaredMethod("sayHello", String.class);
        sayHello.invoke(new Greeting(), "WangYiHui");
    }

}
