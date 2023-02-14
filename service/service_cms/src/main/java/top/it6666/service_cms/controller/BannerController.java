package top.it6666.service_cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_cms.entity.Banner;
import top.it6666.service_cms.service.BannerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-05-15
 */
@Api(tags = "首页banner组")
@RestController
@RequestMapping("/service_cms/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    /**
     * 查询所有banner
     */
    @ApiOperation(value = "查询所有banner")
    @GetMapping("/getAllBanner")
    public ResponseResult getAllBanner() {
        List<Banner> list = bannerService.selectAllBanner();
        return ResponseResult.ok().data("list", list);
    }

    /**
     * 分页查询banner
     */
    @ApiOperation(value = "分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public ResponseResult pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<Banner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return ResponseResult.ok()
                .data("items", pageBanner.getRecords())
                .data("total", pageBanner.getTotal());
    }

    /**
     * 添加banner
     */
    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public ResponseResult addBanner(@RequestBody Banner crmBanner) {
        bannerService.save(crmBanner);
        return ResponseResult.ok();
    }

    /**
     * 获取Banner
     */
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable String id) {
        return ResponseResult.ok().data("item", bannerService.getById(id));
    }

    /**
     * 修改Banner
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public ResponseResult updateById(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return ResponseResult.ok();
    }

    /**
     * 删除Banner
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable String id) {
        bannerService.removeById(id);
        return ResponseResult.ok();
    }
}