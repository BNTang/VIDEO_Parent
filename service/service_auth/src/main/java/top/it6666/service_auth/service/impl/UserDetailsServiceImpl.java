/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_auth.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.it6666.common_security.entity.SecurityUser;
import top.it6666.service_auth.entity.User;
import top.it6666.service_auth.service.MenuService;
import top.it6666.service_auth.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description 用户验证业务
 * @since Created in 2021/5/2 002 16:42
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    /***
     * 根据账号获取用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUserName(username);

        // 判断用户是否存在
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        // 返回UserDetails实现类
        top.it6666.common_security.entity.User curUser = new top.it6666.common_security.entity.User();
        BeanUtils.copyProperties(user, curUser);

        // 查询用户权限
        List<String> authorities = menuService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}