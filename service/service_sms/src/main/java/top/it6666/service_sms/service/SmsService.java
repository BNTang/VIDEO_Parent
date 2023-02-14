/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_sms.service;

import java.util.Map;

/**
 * @author BNTang
 **/
public interface SmsService {
    /**
     * 发送短信
     * 
     * @param phone
     *            手机号
     * @param sms
     *            sms模板
     * @param param
     *            参数
     * @return 发送结果
     */
    boolean send(String phone, String sms, Map<String, Object> param);
}