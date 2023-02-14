/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_auth.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_auth.service.IndexService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description
 * @since Created in 2021/5/2 002 21:02
 **/
@Api(tags = "工作人员-用户组")
@RestController
@RequestMapping("/service_auth/admin/index")
public class IndexController {
    @Resource
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("info")
    public ResponseResult info() {
        // 获取当前登录用户用户名
        return ResponseResult.ok().data(indexService.getUserInfo(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    /**
     * 获取当前登陆用户的菜单
     */
    @ApiOperation(value = "获取当前登陆用户的菜单")
    @GetMapping("menu")
    public ResponseResult getMenu() {
        // 获取当前登录用户用户名
        List<JSONObject> menuList = indexService.getMenu(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseResult.ok().data("menuList", menuList);
    }
}