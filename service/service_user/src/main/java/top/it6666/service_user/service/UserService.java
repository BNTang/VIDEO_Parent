package top.it6666.service_user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.it6666.common_utils.entity.User;
import top.it6666.service_user.entity.vo.LoginVo;
import top.it6666.service_user.entity.vo.RegisterVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-05-22
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * 
     * @param loginVo
     *            登录信息
     * @return token
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     * 
     * @param registerVo
     *            注册信息
     */
    void register(RegisterVo registerVo);

    /**
     * <b> 根据openId获取用户 </b>
     * 
     * @param openId
     *            微信openId
     * @return 通过openId获取的用户
     */
    User getByOpenId(String openId);
}