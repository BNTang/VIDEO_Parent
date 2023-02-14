package top.it6666.service_auth.service;

import com.alibaba.fastjson.JSONObject;
import top.it6666.service_auth.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询所有菜单
     *
     * @return 菜单信息
     */
    List<Menu> queryAllMenu();

    /**
     * 递归删除菜单
     *
     * @param id 菜单ID
     */
    void removeChildById(String id);

    /**
     * 根据角色获取菜单
     *
     * @param roleId 角色ID
     * @return 菜单信息
     */
    List<Menu> selectAllRoleMenu(String roleId);

    /**
     * 给角色分配权限
     *
     * @param roleId 角色ID
     * @param menus  分配的权限
     */
    void saveRoleMenuRelationShip(String roleId, String[] menus);

    /**
     * 根据用户ID查询用户权限
     *
     * @param id 用户ID
     * @return 用户权限
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id返回前端的菜单
     *
     * @param id 用户id
     * @return 前端的菜单
     */
    List<JSONObject> selectPermissionByUserId(String id);
}
