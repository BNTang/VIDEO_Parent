/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.common_base.exception.BnTangException;
import top.it6666.service_vod.service.VodService;
import top.it6666.service_vod.utils.ALiYunVodSdkUtils;
import top.it6666.service_vod.utils.VodConstant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 视频组业务实现类
 * @since Created in 2021/4/17 22:13
 **/
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件名
            String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("");

            //截取标题
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            // 获取上传请求
            UploadStreamRequest request = new UploadStreamRequest(
                    VodConstant.ACCESS_KEY_ID,
                    VodConstant.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            // 开始上传
            UploadVideoImpl uploader = new UploadVideoImpl();

            // 上传完毕后,服务器给于响应
            UploadStreamResponse response = uploader.uploadStream(request);

            // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();

            if (!response.isSuccess()) {
                String errorMessage =
                        "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new BnTangException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new BnTangException(20001, "vod 服务上传失败");
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        try {
            // 获取客户端请求
            DefaultAcsClient client = ALiYunVodSdkUtils.initVodClient(
                    VodConstant.ACCESS_KEY_ID,
                    VodConstant.ACCESS_KEY_SECRET);

            // 创建删除请求
            DeleteVideoRequest request = new DeleteVideoRequest();

            // 设置要删除的视频ID
            request.setVideoIds(videoId);

            // 删除请求后服务器给予的响应
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new BnTangException(20001, "视频删除失败");
        }
    }
}