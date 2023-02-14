/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_auth.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description
 * @since Created in 2021/5/2 002 21:03
 **/
public interface IndexService {

    /**
     * 根据token获取用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    Map<String, Object> getUserInfo(String name);

    /**
     * 获取当前登陆用户的菜单
     * @param name 用户名
     * @return 用户菜单信息
     */
    List<JSONObject> getMenu(String name);
}