package top.it6666.service_video.controller.portal;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.service.portal.IndexPortalService;

import javax.annotation.Resource;

/**
 * @author BNTang
 */
@RestController
@RequestMapping("/service_video/index")
@Api(tags = "门户首页组")
public class IndexPortalController {

    @Resource
    private IndexPortalService indexPortalService;

    /**
     * 获取门户首页所有数据
     */
    @GetMapping("/getIndexData")
    public ResponseResult index() {
        return ResponseResult.ok()
                .data("contentList", indexPortalService.getContentIndexList())
                .data("authorList", indexPortalService.getAuthorIndexList())
                .data("categoryList", indexPortalService.getCategoryIndexList());
    }
}