/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_vod.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.it6666.common_base.exception.BnTangException;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_vod.service.VodService;
import top.it6666.service_vod.utils.ALiYunVodSdkUtils;
import top.it6666.service_vod.utils.VodConstant;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 视频点播与上传请求接口与业务
 * @since Created in 2021/4/17 22:06
 **/
@RestController
@RequestMapping("/service_vod/vod")
@Api(tags = "视频组")
public class VodController {
    @Resource
    private VodService vodService;

    /**
     * <b> 视频上传 </b>
     */
    @ApiOperation(value = "视频上传")
    @PostMapping("/upload")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return ResponseResult.ok().message("视频上传成功!").data("videoId", videoId);
    }

    /**
     * <b> 删除视频 </b>
     */
    @ApiOperation(value = "删除视频")
    @PostMapping("/delete_vod/{videoId}")
    public ResponseResult removeVideo(@PathVariable String videoId) {
        vodService.deleteVideo(videoId);
        return ResponseResult.ok().message("视频删除成功!");
    }

    /**
     * 根据视频id获取视频凭证
     */
    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public ResponseResult getPlayAuth(@PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client =
                ALiYunVodSdkUtils.initVodClient(VodConstant.ACCESS_KEY_ID, VodConstant.ACCESS_KEY_SECRET);

            // 创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 向request设置视频id
            request.setVideoId(id);

            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return ResponseResult.ok().data("playAuth", response.getPlayAuth());
        } catch (Exception e) {
            throw new BnTangException(20001, "获取凭证失败");
        }
    }
}