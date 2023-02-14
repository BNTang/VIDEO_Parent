package top.it6666.service_upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description OSS云上传服务
 * @since Created in 2021/4/3 12:17
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"top.it6666"})
public class UpLoadApplication {
    public static void main(String[] args) {
        /*
        exclude = DataSourceAutoConfiguration.class：默认会加载数据库相关信息，现在是上传服务，里面用不到数据库相关信息,
        不让工程加载数据库相关信息
        */
        SpringApplication.run(UpLoadApplication.class, args);
    }
}