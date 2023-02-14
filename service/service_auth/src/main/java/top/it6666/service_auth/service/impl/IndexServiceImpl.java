/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.it6666.common_base.exception.BnTangException;
import top.it6666.service_auth.entity.Role;
import top.it6666.service_auth.entity.User;
import top.it6666.service_auth.service.IndexService;
import top.it6666.service_auth.service.MenuService;
import top.it6666.service_auth.service.RoleService;
import top.it6666.service_auth.service.UserService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description
 * @since Created in 2021/5/2 002 21:09
 **/
@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>(4);
        User user = userService.selectByUserName(username);

        if (ObjectUtils.isEmpty(user)) {
            throw new BnTangException(200001, "没有该用户");
        }

        // 根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        if (roleNameList.size() == 0) {
            // 前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        // 根据用户id获取操作权限值
        List<String> permissionValueList = menuService.selectPermissionValueByUserId(user.getId());

        // 把权限缓存到redis当中
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", user.getSalt());
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        // 根据用户id获取用户菜单权限
        return menuService.selectPermissionByUserId(userService.selectByUserName(username).getId());
    }
}