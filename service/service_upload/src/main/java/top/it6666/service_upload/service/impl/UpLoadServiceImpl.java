/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.service_upload.service.UpLoadService;
import top.it6666.service_upload.utils.OssConstant;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description OSS上传业务实现
 * @since Created in 2021/4/3 12:57
 **/
@Service
public class UpLoadServiceImpl implements UpLoadService {

    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以上海为例，其它Region请按实际情况填写。
        String endpoint = OssConstant.ENDPOINT;

        // 云账号AccessKey有所有API访问权限
        String accessKeyId = OssConstant.ASSESS_KEY_ID;
        String accessKeySecret = OssConstant.ASSESS_KEY_SECRET;
        String bucketName = OssConstant.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            // 处理文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid + file.getOriginalFilename();

            // 把同一天上传的文件 放到同一个文件夹当中  2020/10/1/fileName
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date + "/" + fileName;

            ossClient.putObject(bucketName, fileName, inputStream);

            // https://bntang.oss-cn-shanghai.aliyuncs.com/01-%E8%AE%A1%E7%AE%97%E6%9C%BA%E8%AF%AD%E8%A8%
            // 80%E6%A6%82%E8%BF%B01.png
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}