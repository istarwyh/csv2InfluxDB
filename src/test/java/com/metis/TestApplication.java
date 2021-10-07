package com.metis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(    
    scanBasePackages = {"com.metis"}
    // 当Mock了底层数据源或者需要自己配置多个数据源时，屏蔽掉原本单数据源的自动加载类
    // ,exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}
)
public class TestApplication {
    public static void main() {
        SpringApplication.run(TestApplication.class);
    }
}