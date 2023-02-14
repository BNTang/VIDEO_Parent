/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_pay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import top.it6666.common_utils.entity.User;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 门户系统用户Feign调用客户端
 * @since Created in 2021/6/6 006 9:02
 **/
@Component
@FeignClient(name = "service-user")
public interface UserClient {
    /**
     * 根据用户id获取用户信息
     * 
     * @param userId
     *            用户Id
     * @return 用户信息
     */
    @PostMapping(value = "/service_user/user/getUserInfoOrderById/{userId}")
    User getUserInfoOrderById(@PathVariable("userId") String userId);
}
