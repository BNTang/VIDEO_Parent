package top.it6666.service_user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import top.it6666.common_base.exception.BnTangException;
import top.it6666.common_utils.entity.User;
import top.it6666.common_utils.utils.JwtUtil;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_user.entity.vo.LoginVo;
import top.it6666.service_user.entity.vo.RegisterVo;
import top.it6666.service_user.service.UserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-05-22
 */
@RestController
@RequestMapping("/service_user/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginVo loginVo) {
        String token = userService.login(loginVo);
        System.out.println(token);
        return ResponseResult.ok().data("token", token);
    }

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return ResponseResult.ok();
    }

    /**
     * 获取用户登陆成功之后的用户信息
     * 
     */
    @ApiOperation(value = "获取用户登陆成功之后的用户信息")
    @GetMapping("/auth/getLoginInfo")
    public ResponseResult getLoginInfo(HttpServletRequest request) {
        try {
            // 查询数据库根据用户id获取用户信息
            return ResponseResult.ok().data("userInfo", userService.getById(JwtUtil.getUserIdByJwtToken(request)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BnTangException(20001, "获取失败");
        }
    }

    /**
     * 根据用户id获取用户信息
     */
    @ApiOperation(value = "根据用户id获取用户信息")
    @PostMapping("/getUserInfoOrderById/{userId}")
    public User getUserInfoOrderById(@PathVariable String userId) {
        return userService.getById(userId);
    }
}