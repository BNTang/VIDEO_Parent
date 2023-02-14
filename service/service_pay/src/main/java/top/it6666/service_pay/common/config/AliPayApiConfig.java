/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_pay.common.config;

import org.springframework.context.annotation.Configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import lombok.RequiredArgsConstructor;
import top.it6666.service_pay.common.properties.AliPayProperties;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 阿里支付Api配置
 * @since Created in 2021/6/6 006 18:08
 **/
@Configuration
@RequiredArgsConstructor
public class AliPayApiConfig {
    private final AliPayProperties aliPayProperties;
    private static final String JSON = "json";
    private static final String RSA2 = "RSA2";
    private static final String CHARSET = "UTF-8";

    /**
     * 获取阿里支付客户端
     */
    public AlipayClient getAliPayClient() {
        return new DefaultAlipayClient(aliPayProperties.getServerUrl(), aliPayProperties.getAppId(),
            aliPayProperties.getPrivateKey(), JSON, CHARSET, aliPayProperties.getPublvicKey(), RSA2);
    }
}