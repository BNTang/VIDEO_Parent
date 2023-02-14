package top.it6666.service_upload.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_upload.service.UpLoadService;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 上传oss控制器
 * @since Created in 2021/4/3 12:53
 **/
@RestController
@RequestMapping("/service_upload/file")
@Api(tags = "上传OSS")
public class UpLoadController {

    private final UpLoadService upLoadService;

    public UpLoadController(UpLoadService upLoadService) {
        this.upLoadService = upLoadService;
    }

    /**
     * <p>
     * 上传文件
     * </p>
     *
     * @param file 文件
     * @return 上传之后的文件下载地址 url
     */
    @PostMapping("/uploadOssFile")
    public ResponseResult uploadOssFile(MultipartFile file) {
        return ResponseResult.ok().data("url", upLoadService.uploadFile(file));
    }

}