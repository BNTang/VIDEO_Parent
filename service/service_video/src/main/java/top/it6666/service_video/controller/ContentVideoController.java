package top.it6666.service_video.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.entity.vo.ContentVideoInfoVO;
import top.it6666.service_video.service.ContentVideoService;

import javax.annotation.Resource;

/**
 * <p>
 * 作品视频 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@RestController
@Api(tags = "作品视频组")
@RequestMapping("/service_video/content_video")
public class ContentVideoController {

    @Resource
    private ContentVideoService contentVideoService;

    /**
     * <b>
     * 新增小节
     * </b>
     */
    @ApiOperation(value = "新增小节")
    @PostMapping("/saveVideoInfo")
    public ResponseResult save(@RequestBody ContentVideoInfoVO contentVideoInfoVO) {
        contentVideoService.saveVideoInfo(contentVideoInfoVO);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 根据ID查询小节
     * </b>
     */
    @ApiOperation(value = "根据ID查询小节")
    @GetMapping("/getVideoInfo/{id}")
    public ResponseResult getVideInfoById(@PathVariable String id) {
        return ResponseResult.ok().data("item", contentVideoService.getContentVideoInfoById(id));
    }

    /**
     * <b>
     * 更新小节
     * </b>
     */
    @ApiOperation(value = "更新小节")
    @PostMapping("/updateVideoInfo")
    public ResponseResult updateCourseInfoById(@RequestBody ContentVideoInfoVO contentVideoInfoVO) {
        contentVideoService.updateVideoInfo(contentVideoInfoVO);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 根据ID删除小节
     * </b>
     */
    @ApiOperation(value = "根据ID删除小节")
    @PostMapping("/deleteVideoInfo/{id}")
    public ResponseResult removeById(@PathVariable String id) {
        if (contentVideoService.deleteVideoById(id)) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.error().message("删除失败");
        }
    }

    /**
     * <b>
     * 根据视频ID删除小节视频
     * </b>
     */
    @ApiOperation(value = "根据视频ID删除小节视频")
    @PostMapping("/deleteContentVideoById/{id}")
    public ResponseResult deleteContentVideoById(@PathVariable String id) {
        contentVideoService.deleteContentVideoById(id);
        return ResponseResult.ok();
    }
}