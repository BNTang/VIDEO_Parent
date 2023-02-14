package top.it6666.service_video.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.client.back.VodFeignClientFallBack;

/**
 * @author BNTang
 */
@FeignClient(name = "service-vod", fallback = VodFeignClientFallBack.class)
@Component
public interface VodClient {

    /**
     * <b> 根据 videoId 删除视频 </b>
     *
     * @param videoId
     *            视频ID
     * @return 删除结果
     */
    @PostMapping(value = "/service_vod/vod/delete_vod/{videoId}")
    ResponseResult deleteAliYunVideo(@PathVariable("videoId") String videoId);
}