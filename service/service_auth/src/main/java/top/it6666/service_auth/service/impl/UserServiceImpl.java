package top.it6666.service_auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.it6666.service_auth.entity.User;
import top.it6666.service_auth.mapper.UserMapper;
import top.it6666.service_auth.service.UserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User selectByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}