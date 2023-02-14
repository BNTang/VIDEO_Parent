/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 上传服务接口
 * @since Created in 2021/4/3 12:56
 **/
public interface UpLoadService {

    /**
     * <p>
     * 上传OSS文件
     * </p>
     *
     * @param file 文件
     * @return 上传之后的url
     */
    String uploadFile(MultipartFile file);

}