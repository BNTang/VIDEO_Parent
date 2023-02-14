package top.it6666.service_upload.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 读取配置的属性信息
 * @since Created in 2021/4/3 12:46
 **/
@Component
public class OssConstant implements InitializingBean {
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    public static String ENDPOINT;
    public static String ASSESS_KEY_ID;
    public static String ASSESS_KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * 在属性文件加载完毕后属性也设置完毕之后, 会自动调用
     */
    @Override
    public void afterPropertiesSet() {
        ENDPOINT = endpoint;
        ASSESS_KEY_ID = accessKeyId;
        ASSESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
    }
}