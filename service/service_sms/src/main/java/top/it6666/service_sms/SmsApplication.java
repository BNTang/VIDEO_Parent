package top.it6666.service_sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author BNTang
 * @SpringBootApplication(exclude = DataSourceAutoConfiguration.class) 取消数据源自动配置
 */
@ComponentScan({"top.it6666"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}