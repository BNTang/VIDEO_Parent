package top.it6666.service_video.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.it6666.common_utils.entity.ContentWebVO;
import top.it6666.common_utils.utils.JwtUtil;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.client.OrderClient;
import top.it6666.service_video.entity.Content;
import top.it6666.service_video.entity.vo.ChapterVO;
import top.it6666.service_video.entity.vo.ContentVO;
import top.it6666.service_video.service.ChapterService;
import top.it6666.service_video.service.ContentService;

/**
 * <p>
 * 作品表 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/service_video/content")
@Api(tags = "作品组")
public class ContentController {
    @Resource
    private ContentService contentService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private OrderClient orderClient;

    /**
     * <p>
     * 保存基本信息
     * </p>
     */
    @ApiOperation(value = "保存基本信息")
    @PostMapping("/addContentInfo")
    public ResponseResult addContentInfo(@RequestBody ContentVO contentVO) {
        // 调用contentService处理添加作品信息业务
        return ResponseResult.ok().data("contentId", contentService.saveContentInfo(contentVO));
    }

    /**
     * <p>
     * 根据ID获取一条作品简介信息数据
     * </p>
     */
    @ApiOperation(value = "根据ID获取一条作品简介信息数据")
    @GetMapping("/getContentWithInfoId/{id}")
    public ResponseResult getContentWithInfoId(@PathVariable String id) {
        return ResponseResult.ok().data("contentVO", contentService.getContentWithInfoId(id));
    }

    /**
     * <p>
     * 修改作品基本信息
     * </p>
     */
    @ApiOperation(value = "修改作品基本信息")
    @PostMapping("updateContentInfo")
    public ResponseResult updateContentInfo(@RequestBody ContentVO contentVO) {
        contentService.updateContentInfo(contentVO);
        return ResponseResult.ok();
    }

    /**
     * <b> 获取作品的预览信息 </b>
     */
    @ApiOperation(value = "获取作品的预览信息")
    @GetMapping("/getContentPreView/{id}")
    public ResponseResult getContentPreView(@PathVariable String id) {
        return ResponseResult.ok().data("item", contentService.getContentPreView(id));
    }

    /**
     * <b> 发布作品 </b>
     */
    @ApiOperation(value = "发布作品")
    @PostMapping("/sendContent/{id}")
    public ResponseResult sendContent(@PathVariable String id) {
        contentService.sendContentWithId(id);
        return ResponseResult.ok();
    }

    /**
     * <b> 分页查询作品列表方法 </b>
     */
    @ApiOperation(value = "分页查询作品列表方法")
    @GetMapping("/getContentPageQuery/{page}/{limit}")
    public ResponseResult contentPageQuery(@PathVariable Long page, @PathVariable Long limit, ContentVO contentVO) {
        Page<Content> pageParam = new Page<>(page, limit);
        contentService.contentPageQuery(pageParam, contentVO);
        return ResponseResult.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
    }

    /**
     * <b> 根据作品id删除对应视频信息 </b>
     */
    @ApiOperation(value = "根据作品id删除对应视频信息")
    @PostMapping("/deleteContent/{id}")
    public ResponseResult deleteContent(@PathVariable String id) {
        contentService.deleteContentWithId(id);
        return ResponseResult.ok();
    }

    /**
     * 根据作品id获取作品详情信息
     */
    @ApiOperation(value = "根据作品id获取作品详情信息")
    @GetMapping("/getContentDeatail/{contentId}")
    public ResponseResult getById(@PathVariable String contentId, HttpServletRequest request) {
        // 查询作品信息和作者信息
        ContentWebVO contentWebVo = contentService.selectContentDetailById(contentId);

        // 查询当前作品的章节信息
        List<ChapterVO> chapterVoList = chapterService.getChapterContentVideo(contentId);

        String uid = JwtUtil.getUserIdByJwtToken(request);
        boolean isBuy = false;
        if (!StringUtils.isEmpty(uid)) {
            // 查询是否已经付费过了
            isBuy = orderClient.isBuyContent(uid, contentId);
        }

        return ResponseResult.ok().data("content", contentWebVo).data("chapterVoList", chapterVoList)
            .data("isBuyContent", isBuy);
    }

    /**
     * 根据作品id查询作品信息
     */
    @ApiOperation(value = "根据作品id查询作品信息")
    @PostMapping("/getContentInfoOrder/{contentId}")
    public ContentWebVO getContentInfoOrder(@PathVariable String contentId) {
        // 查询作品信息和作者信息
        return contentService.selectContentDetailById(contentId);
    }
}