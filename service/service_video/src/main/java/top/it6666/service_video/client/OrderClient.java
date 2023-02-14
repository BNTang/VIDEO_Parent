/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 订单微服务 Feign 客户端调用
 * @since Created in 2021/6/6 006 9:53
 **/
@Component
@FeignClient(name = "service-pay")
public interface OrderClient {
    /**
     * 根据 `用户id` 和 `作品id` 查询该用户是否已经购买该作品请求
     * 
     * @param userid
     *            用户id
     * @param contentId
     *            作品id
     * @return 是否购买过该作品
     */
    @GetMapping("/service_pay/pay-order/isBuyContent/{userid}/{contentId}")
    boolean isBuyContent(@PathVariable String userid, @PathVariable String contentId);
}