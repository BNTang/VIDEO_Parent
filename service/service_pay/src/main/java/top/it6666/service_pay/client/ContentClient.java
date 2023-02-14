/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_pay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import top.it6666.common_utils.entity.ContentWebVO;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 作品内容远程调用Feign客户端
 * @since Created in 2021/6/6 006 8:59
 **/
@Component
@FeignClient(name = "service-video")
public interface ContentClient {
    /**
     * 根据作品id查询课程信息
     * 
     * @param contentId
     *            作品Id
     * @return 课程信息
     */
    @PostMapping("/service_video/content/getContentInfoOrder/{contentId}")
    ContentWebVO getContentInfoOrder(@PathVariable("contentId") String contentId);
}