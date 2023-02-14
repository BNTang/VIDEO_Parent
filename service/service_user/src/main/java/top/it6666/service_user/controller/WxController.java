/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_user.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import lombok.RequiredArgsConstructor;
import top.it6666.common_utils.entity.User;
import top.it6666.common_utils.utils.JwtUtil;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_user.constant.WxConstantUtil;
import top.it6666.service_user.service.UserService;
import top.it6666.service_user.utils.HttpClientUtil;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 微信请求控制器
 * @since Created in 2021/5/29 029 8:27
 **/
@RestController
@RequestMapping("/service_user/user/wx")
@RequiredArgsConstructor
public class WxController {
    private final UserService userService;
    /**
     * 记录当前是否已经登录
     */
    private volatile boolean isLogin;
    /**
     * 记录当前是否扫码登录失败
     */
    private boolean isLoginFail;
    /**
     * 当前登录用户token
     */
    private String jwtToken;

    /**
     * <b>微信登录后端回调业务</b>
     * 
     * @param code
     *            code
     * @param state
     *            站点状态
     */
    @RequestMapping("/callBack")
    public void callback(String code, String state) {
        // 从redis中将state获取出来，和当前传入的state作比较
        // 如果一致则放行，如果不一致则抛出异常：非法访问
        try {
            // 需修改为自己的app环境
            String getToken = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=%s" + "&secret=%s"
                + "&code=%s" + "&grant_type=authorization_code";

            // 拼接请求地址
            getToken = String.format(getToken, WxConstantUtil.WX_OPEN_APP_ID, WxConstantUtil.WX_OPEN_APP_SECRET, code);

            // 回调获得code，通过用户授权的code去获取微信令牌
            Map<String, Object> map = JSON.parseObject(HttpClientUtil.get(getToken));

            // 获取到了关键的令牌和openid后，
            // 就可以正式开始查询微信用户的信息，完成我们要做的微信绑定
            String accessToken = (String)map.get("access_token");
            String openId = (String)map.get("openid");

            // 查询数据库当中有没有该用户
            User user = userService.getByOpenId(openId);

            // 如果没有用户
            if (null == user) {
                String userInfo = "" + "https://api.weixin.qq.com/sns/userinfo?" + "access_token=%s" + "&openid=%s";
                userInfo = String.format(userInfo, accessToken, openId);

                // 获取微信用户信息
                String wxUserInfo = HttpClientUtil.get(userInfo);

                // 解析用户json信息
                Map<String, Object> info = JSON.parseObject(wxUserInfo);

                // 获取昵称与头像
                String nickname = (String)info.get("nickname");
                String headImgUrl = (String)info.get("headimgurl");

                // 向数据库中插入一条记录
                user = new User();
                user.setNickname(nickname);
                user.setOpenid(openId);
                user.setAvatar(headImgUrl);

                // 保存用户信息
                userService.save(user);
            }
            // 使用jwt根据user对象生成token字符串
            jwtToken = JwtUtil.getJwtToken(user.getId(), user.getNickname());
            this.isLogin = true;
        } catch (Exception e) {
            this.isLoginFail = true;
        }
    }

    /**
     * <b>校验是否已经登录</b>
     * 
     * @return 校验结果
     */
    @GetMapping("/checkLogin")
    public ResponseResult checkLogin() {
        ResponseResult result;
        int flag = 1;
        while (true) {
            // 如果登录状态已经,登录,返回用户token
            if (this.isLogin) {
                result = ResponseResult.ok().data("token", this.jwtToken);
                // 状态复位
                isLogin = false;
                // 退出
                break;
            }
            // 扫描登录失败 返回错误信息
            if (isLoginFail) {
                result = ResponseResult.error();
            }
            try {
                // 每500毫秒查询一下状态
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 超时处理
            if (flag > 500) {
                result = ResponseResult.error();
                break;
            }
            flag++;
        }
        return result;
    }
}