/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_sms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_sms.service.SmsService;

/**
 * @author BNTang
 **/
@RestController
@RequestMapping("/service_sms/api/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    /**
     * 发送完短信后, 把生成的验证码写到redis
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public ResponseResult code(@PathVariable String phone) {
        // 查看redis当中是否有当前手机对应的验证码 手机号当作key
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            // 已经发送过了 还没有过期
            return ResponseResult.ok();
        }

        // 生成新的验证码
        code = randomCode();
        Map<String, Object> param = new HashMap<>(1);
        // 把验证码写到参数当中去
        param.put("code", code);
        // 发送短信
        boolean isSend = smsService.send(phone, "SMS_169111508", param);

        // 发送成功
        if (isSend) {
            // 把验证码写到Redis当中
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return ResponseResult.ok();
        }
        // 提示用户失败
        return ResponseResult.error().message("发送短信失败");
    }

    /**
     * 生成4位的数字验证码
     * 
     * @return 验证码
     */
    public String randomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }
}