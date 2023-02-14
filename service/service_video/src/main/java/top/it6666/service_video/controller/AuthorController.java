package top.it6666.service_video.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.entity.Author;
import top.it6666.service_video.entity.vo.AuthorQueryVO;
import top.it6666.service_video.service.AuthorService;
import top.it6666.service_video.service.ContentService;

/**
 * <p>
 * 创作者 前端控制器 Api 注解代表：分组
 * </p>
 *
 * @author BNTang
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/service_video/author")
@RequiredArgsConstructor
@Api(tags = "作者组")
public class AuthorController {

    /**
     * 使用代码生成器生成的 service 当中已经有很多的基础服务，直接调用即可
     */
    private final AuthorService authorService;

    private final ContentService contentService;

    /**
     * <p>
     * 获取创作者列表
     * </p>
     */
    @ApiOperation(value = "所有的作者列表")
    @GetMapping("/getAuthorList")
    public ResponseResult getAuthorList() {
        return ResponseResult.ok().data("list", authorService.list(null));
    }

    /**
     * <p>
     * 根据Id删除创作者
     * </p>
     */
    @ApiOperation(value = "逻辑删除作者")
    @DeleteMapping("/deleteAuthor/{id}")
    public ResponseResult
        deleteAuthor(@ApiParam(name = "id", value = "作者Id", required = true) @PathVariable String id) {
        authorService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * <p>
     * 分页查询创作者,还可以添加过滤条件进行查询
     * </p>
     */
    @ApiOperation(value = "作者分页列表数据")
    @PostMapping("/pageList/{page}/{limit}")
    public ResponseResult pageList(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
        @ApiParam(name = "authorQuery", value = "查询对象") @RequestBody(required = false) AuthorQueryVO authorQueryVO) {
        // 分页查询
        Page<Author> pageInfo = new Page<>(page, limit);

        authorService.pageQuery(pageInfo, authorQueryVO);

        // 获取当前页的数据
        List<Author> records = pageInfo.getRecords();

        // 获取总记录
        long total = pageInfo.getTotal();

        return ResponseResult.ok().data("total", total).data("rows", records);
    }

    /**
     * <p>
     * 根据id查询作者
     * </p>
     *
     * @param id
     *            作者Id
     * @return 查询到的作者
     */
    @ApiOperation(value = "根据id查询作者")
    @GetMapping("/getAuthorWithId/{id}")
    public ResponseResult
        getAuthorWithId(@ApiParam(name = "id", value = "作者的id", required = true) @PathVariable String id) {
        return ResponseResult.ok().data("item", authorService.getById(id));
    }

    /**
     * <p>
     * 添加作者
     * </p>
     *
     * @param author
     *            需要添加的创作者
     * @return 添加结果
     */
    @ApiOperation(value = "添加作者")
    @PostMapping("/addAuthor")
    public ResponseResult
        addAuthor(@ApiParam(name = "author", value = "作者对象", required = true) @RequestBody Author author) {
        authorService.save(author);
        return ResponseResult.ok();
    }

    /**
     * <p>
     * 更新创作者
     * </p>
     *
     * @param author
     *            更新的创作者
     * @return 更新结果
     */
    @ApiOperation(value = "更新创作者")
    @PostMapping("/updateAuthor")
    public ResponseResult
        updateAuthor(@ApiParam(name = "author", value = "作者对象", required = true) @RequestBody Author author) {
        boolean flag = authorService.updateById(author);
        if (flag) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.error();
        }
    }

    /**
     * <b> 根据作者ID查询作者信息以及作者的作品 </b>
     */
    @GetMapping(value = "getAuthorById/{id}")
    public ResponseResult getById(@PathVariable String id) {
        return ResponseResult.ok().data("author", authorService.getById(id)).data("contentList",
            contentService.selectByAuthorId(id));
    }
}