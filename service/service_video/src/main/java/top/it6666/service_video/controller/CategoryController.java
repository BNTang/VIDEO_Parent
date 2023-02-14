package top.it6666.service_video.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.service.CategoryService;

import javax.annotation.Resource;

/**
 * <p>
 * 科目分类 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/service_video/category")
@Api(tags = "分类组")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * <p>
     * 添加分类，通过上传excel的方式添加
     * </p>
     */
    @PostMapping("/addCategory")
    @ApiOperation(value = "添加分类")
    public ResponseResult addCategory(MultipartFile file) {
        // 调用业务上传excel
        categoryService.saveCategory(file);
        return ResponseResult.ok();
    }

    /**
     * <p>
     * 获取分类列表树形结构
     * </p>
     */
    @GetMapping("/getTreeCategory")
    @ApiOperation(value = "获取分类列表树形结构")
    public ResponseResult getTreeCategory() {
        return ResponseResult.ok().data("list", categoryService.getTreeCategory());
    }
}