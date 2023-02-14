package top.it6666.service_user.service.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.it6666.common_base.exception.BnTangException;
import top.it6666.common_utils.entity.User;
import top.it6666.common_utils.utils.JwtUtil;
import top.it6666.common_utils.utils.Md5;
import top.it6666.service_user.entity.vo.LoginVo;
import top.it6666.service_user.entity.vo.RegisterVo;
import top.it6666.service_user.mapper.UserMapper;
import top.it6666.service_user.service.UserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-05-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        // 校验参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new BnTangException(20001, "缺少参数");
        }
        // 获取用户
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("mobile", phone));
        if (null == user) {
            throw new BnTangException(20001, "用户不存在");
        }
        // 校验密码
        if (!Md5.encrypt(password).equals(user.getPassword())) {
            throw new BnTangException(20001, "密码错误");
        }
        // 校验是否被禁用
        if (user.getIsDisabled()) {
            throw new BnTangException(20001, "用户被禁用");
        }
        // 使用JWT生成token字符串
        return JwtUtil.getJwtToken(user.getId(), user.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        // 校验参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)
            || StringUtils.isEmpty(code)) {
            throw new BnTangException(20001, "缺少参数");
        }
        // 校验校验验证码
        // 从redis获取发送的验证码
        if (!code.equals(redisTemplate.opsForValue().get(phone))) {
            throw new BnTangException(20001, "验证码错误");
        }

        // 查询数据库中是否存在相同的手机号码
        if (baseMapper.selectCount(new QueryWrapper<User>().eq("mobile", phone)) > 0) {
            throw new BnTangException(20001, "手机号已被使用");
        }

        // 添加注册信息到数据库
        User member = new User();
        member.setNickname(nickname);
        member.setMobile(registerVo.getPhone());
        member.setPassword(Md5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://xiaohuihuit.github.io/images/avatar.jpg");
        this.save(member);
    }

    @Override
    public User getByOpenId(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        return baseMapper.selectOne(queryWrapper);
    }
}