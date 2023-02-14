package top.it6666.service_auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.it6666.service_auth.entity.Role;
import top.it6666.service_auth.entity.UserRole;
import top.it6666.service_auth.mapper.RoleMapper;
import top.it6666.service_auth.service.RoleService;
import top.it6666.service_auth.service.UserRoleService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    /**
     * <b>
     * 根据用户ID获取角色数据
     * </b>
     */
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        // 1.查询出所有的角色
        List<Role> allRoles = baseMapper.selectList(null);

        // 2.当前用户选中的角色有哪些
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>()
                .eq("user_id", userId)
                .select("role_id");

        // 获取到当前用户所在的角色关系
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);

        // 取出所有的roleId 放到一个集合当中
        List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 根据角色id,取出所有的角色对象
        List<Role> userRoles = new ArrayList<>();

        allRoles.forEach(role -> {
            if (roleIds.contains(role.getId())) {
                userRoles.add(role);
            }
        });

        Map<String, Object> roleMap = new HashMap<>(2);

        // 用户的角色
        roleMap.put("assignRoles", userRoles);

        // 所有的角色
        roleMap.put("allRolesList", allRoles);
        return roleMap;
    }

    /**
     * <b>
     * 根据用户ID分配用户角色
     * </b>
     */
    @Override
    public void saveUserRoleRelationShip(String userId, String[] roleId) {
        // 1.删除原来角色与用户之间的关系
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

        // 2.重新建立关系
        List<UserRole> userRoleArrayList = Stream.of(roleId).map(role -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role);
            return userRole;
        }).collect(Collectors.toList());

        // 3.批量保存关系
        userRoleService.saveBatch(userRoleArrayList);
    }

    @Override
    public List<Role> selectRoleByUserId(String id) {
        // 根据用户id查询拥有的角色id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>()
                .eq("user_id", id)
                .select("role_id"));

        //获取所有角色id
        List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 根据角色id查询所有的角色信息
        List<Role> roleList = new ArrayList<>();

        if (roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}