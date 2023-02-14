package top.it6666.service_auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.it6666.service_auth.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
public interface RoleService extends IService<Role> {

    /**
     * <b>
     * 根据用户ID获取角色数据
     * </b>
     *
     * @param userId 用户ID
     * @return 角色数据
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * <b>
     * 根据用户ID分配用户角色
     * </b>
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void saveUserRoleRelationShip(String userId, String[] roleId);

    /**
     * 根据用户id查询角色
     *
     * @param id 用户ID
     * @return 用户对应的角色
     */
    List<Role> selectRoleByUserId(String id);
}