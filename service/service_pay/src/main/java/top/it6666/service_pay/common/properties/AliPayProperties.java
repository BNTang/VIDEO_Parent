/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_pay.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description
 * @since Created in 2021/6/6 006 18:14
 **/
@Data
@Component
@ConfigurationProperties(prefix = "pay.alibaba")
public class AliPayProperties {
    private String appId;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publvicKey;
    /**
     * 支付宝服务地址
     */
    private String serverUrl;
    /**
     * 回调地址
     */
    private String returnUrl;
    /**
     * 异步回调
     */
    private String notyfyUrl;
}