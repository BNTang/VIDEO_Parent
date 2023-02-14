/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 视频组业务
 * @since Created in 2021/4/17 22:10
 **/
public interface VodService {

    /**
     * <p>
     * 上传视频
     * </p>
     *
     * @param file 视频文件
     * @return 上传结果
     */
    String uploadVideo(MultipartFile file);

    /**
     * <p>
     * 根据 videoId 删除对应的视频
     * </p>
     *
     * @param videoId 删除结果
     */
    void deleteVideo(String videoId);
}