/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_sms.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import top.it6666.service_sms.service.SmsService;

/**
 * @author BNTang
 **/
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean send(String phoneNumbers, String templateCode, Map<String, Object> param) {
        if (StringUtils.isEmpty(phoneNumbers)) {
            return false;
        }

        DefaultProfile profile =
            DefaultProfile.getProfile("cn-hangzhou", "LTAI4GH19ep2P8ssW2E2N6A", "NAmWiBmKXofw7hs4JRcx7SLhF1MN6P");

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", "BNTang");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            client.getCommonResponse(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}