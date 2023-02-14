package top.it6666.service_pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author BNTang
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.it6666"})
@MapperScan("top.it6666.service_pay.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}