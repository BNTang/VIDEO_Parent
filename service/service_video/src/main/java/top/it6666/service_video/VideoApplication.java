package top.it6666.service_video;

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
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("top.it6666.service_video.mapper")
@ComponentScan(basePackages = {"top.it6666"})
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}