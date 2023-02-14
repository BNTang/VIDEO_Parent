/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.common_security.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.common_utils.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 未授权的统一处理方式
 * @since Created in 2021/5/2 002 12:14
 **/
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ResponseUtil.out(response, ResponseResult.error());
    }
}