package top.it6666.service_video.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;

/**
 * @author BNTang
 */
@RestController
@RequestMapping("/service_video/user")
@Api(tags = "用户组")
public class LoginController {

    /**
     * <p>
     * 登录
     * </p>
     *
     * @return 登录成功之后的信息
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResponseResult login() {
        return ResponseResult.ok().data("token", "admin-token");
    }

    /**
     * <p>
     * 登录之后的用户信息
     * </p>
     *
     * @return 登录之后的用户信息
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取登录之后的用户信息")
    public ResponseResult info() {
        return ResponseResult.ok()
                .data("roles", "[admin]")
                .data("name", "BNTang")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
