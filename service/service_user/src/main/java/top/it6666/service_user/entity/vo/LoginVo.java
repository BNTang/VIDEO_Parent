package top.it6666.service_user.entity.vo;

import lombok.Data;

/**
 * @author BNTang
 */
@Data
public class LoginVo {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
}