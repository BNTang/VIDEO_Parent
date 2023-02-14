package top.it6666.service_video.client.back;

import org.springframework.stereotype.Component;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.client.VodClient;

/**
 * @author BNTang
 */
@Component
public class VodFeignClientFallBack implements VodClient {
    @Override
    public ResponseResult deleteAliYunVideo(String videoId) {
        return ResponseResult.error().message("熔断处理-调用超时！");
    }
}