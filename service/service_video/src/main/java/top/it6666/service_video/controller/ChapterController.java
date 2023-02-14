package top.it6666.service_video.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.entity.Chapter;
import top.it6666.service_video.service.ChapterService;

import javax.annotation.Resource;

/**
 * <p>
 * 作品章节 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/service_video/chapter")
@Api(tags = "作品章节组")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    /**
     * <b>
     * 获取章节嵌套形式的数据结构列表
     * </b>
     */
    @ApiOperation(value = "获取章节嵌套形式的数据结构列表")
    @GetMapping("/getChapterContentVideo/{contentId}")
    public ResponseResult getChapterSection(@PathVariable String contentId) {
        return ResponseResult.ok().data("items", chapterService.getChapterSection(contentId));
    }

    /**
     * <b>
     * 新增章节
     * </b>
     */
    @PostMapping("/addChapter")
    @ApiOperation(value = "新增章节")
    public ResponseResult addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 根据 ID 查询章节
     * </b>
     */
    @GetMapping("/getChapterById/{id}")
    @ApiOperation(value = "根据 ID 查询章节")
    public ResponseResult getChapterById(@ApiParam(name = "id", value = "章节ID", required = true)
                                         @PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return ResponseResult.ok().data("item", chapter);
    }

    /**
     * <b>
     * 根据ID修改章节
     * </b>
     */
    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("/updateChapterById")
    public ResponseResult updateChapterById(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 根据ID删除章节
     * </b>
     */
    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("/deleteChapterById/{id}")
    public ResponseResult deleteChapterById(@PathVariable String id) {
        chapterService.deleteChapterById(id);
        return ResponseResult.ok();
    }
}