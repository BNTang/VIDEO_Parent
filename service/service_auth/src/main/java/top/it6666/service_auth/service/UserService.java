package top.it6666.service_auth.service;

import top.it6666.service_auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名从数据库中取出用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUserName(String username);
}