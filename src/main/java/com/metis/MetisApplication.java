package com.metis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Properties;

/**
 * 1. 告诉Spring递归地搜索metis包和其子包中直接或间接标记为@Component的类 2.
 * 将上述被特定注解的类通过反射实例化(Class->Object),以Map形式保存.Map的key为类名,value为类对象. 3.
 * 再一次遍历上述类获取其中所有字段,
 * 如果字段key上有@Autowired或@Resource->从Bean容器中拿到对应的对象value,也有可能先拿到的是接口,那么获取接口对应实现类再将其实例化取值
 * 4. 完成对象都放入IOC容器中
 */
@ComponentScan
/**
 * 基于classpath的内容执行合理的默认行为,例如 1.嵌入版的Tomcat（tomcat-embed-core.jar）自动建立并进行合理的默认配置
 * 2.spring-webmvc.jar 中的Spring MVC DispatcherServlet自动配置和注册 –- 不需要web.xml
 * 
 * 当需要配置多数据源的时候,通过exclude排除不需要的自动化配置类 DataSourceAutoConfiguration.class 会自动查找
 * application.yml 或者 properties 文件里的 spring.datasource.* 相关属性并自动配置单数据源
 */
@EnableAutoConfiguration
// (exclude =
// {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
/**
 * 声明这是一个注解，@SpringBootConfiguration 封装了一些定义注解的注解(元注解)，如注解适用类型与生命周期等 -->
 * 用一个@SpringBootApplication也就可以了,因为 @SpringBootApplication =
 * 
 * @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
 * @author MBin_王艺辉istarwyh
 */
@SpringBootConfiguration
public class MetisApplication {
    static {
        // 也可以在application.properties中配置
        // application.properties文件会取antx.properties(自动配置取值的文件)去对应的配置值。
        System.setProperty("spring.profiles.active", "testing");
        // 热部署开关
        System.setProperty("spring.devtools.restart.enabled", "false");
        
        Map<String, String> envMap = System.getenv();
        String[] propertiesArray = getPropertiesArray();
        printArguments(envMap);
        printArgumentsWithoutExcludedKey(arguments2Map(propertiesArray),"java.class.path");
    }

    /**
     * TODO:有时候会报loadContext Failure,主要是某些类不能被初始化，但是后面又好了，那是为什么呢？
     * @param args 随着命令行指定可以被传进来的程序参数
     */
    public static void main(String[] args) {

        System.out.println("-----------------------This will be printed twice-----------------------");
        // 打印传入进来的程序参数。
        printArguments(arguments2Map(args));
        SpringApplication.run(MetisApplication.class, args);
    }

    private static String[] getPropertiesArray() {
        Properties properties = System.getProperties();
        String propertiesString = properties.toString().replace("{","").replace("}", "");
        String[] propertiesArray = propertiesString.split(",");
        return propertiesArray;
    }

    private static void printArgumentsWithoutExcludedKey(Map<String, String> map,String excludedKey){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(!Objects.isNull(excludedKey) && excludedKey.equals(entry.getKey())){continue;}
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static void printArguments(Map<String, String> map){
        printArgumentsWithoutExcludedKey(map, null);
    }

    private static Map<String, String> arguments2Map(String[] args) {
        Map<String, String> arguments = new HashMap<String, String>();

        if (args == null || args.length == 0) {
            return arguments;
        }

        for (String arg : args) {
            int index = arg.indexOf("=");
            // 没有=，或者=是第一个，都出错。
            if (index < 1) {
                throw new RuntimeException("param must be key value pair");
            }

            String key = arg.substring(0, index).trim();
            String value = arg.substring(index + 1).trim();
            arguments.put(key, value);
        }

        return arguments;
    }

}