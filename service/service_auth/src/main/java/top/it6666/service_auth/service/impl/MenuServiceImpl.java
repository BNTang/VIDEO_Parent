package top.it6666.service_auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.it6666.service_auth.entity.Menu;
import top.it6666.service_auth.entity.RoleMenu;
import top.it6666.service_auth.entity.User;
import top.it6666.service_auth.mapper.MenuMapper;
import top.it6666.service_auth.service.MenuService;
import top.it6666.service_auth.service.RoleMenuService;
import top.it6666.service_auth.service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 菜单权限 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private UserService userService;

    @Override
    public List<Menu> queryAllMenu() {
        // 1.查询所有的菜单
        List<Menu> menuList = baseMapper.selectList(null);

        // 2.构建树级菜单
        return buildTreeMenu(menuList);
    }

    @Override
    public void removeChildById(String id) {
        // 最终要删除的idList
        List<String> idList = new ArrayList<>();

        idList.add(id);

        // 递归查找出所有的子菜单的id
        selectChildMenu(id, idList);

        // 批量删除
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public List<Menu> selectAllRoleMenu(String roleId) {
        // 1.查询所有的菜单(利用CAST函数把ID转换为了整数: SIGNED，在排序)
        QueryWrapper<Menu> qw = new QueryWrapper<Menu>().orderByAsc("CAST(id AS SIGNED)");
        List<Menu> menuList = baseMapper.selectList(qw);

        // 2.根据角色,查询角色对象的菜单权限
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<RoleMenu>().eq("role_id", roleId);
        List<RoleMenu> roleMenuList = roleMenuService.list(queryWrapper);

        // 3.确定哪些菜单成为选中状态
        menuList.forEach(menu -> roleMenuList.forEach(roleMenu -> {
            if (menu.getId().equals(roleMenu.getPermissionId())) {
                menu.setSelect(true);
            }
        }));

        // 4.构建树级菜单
        return buildTreeMenu(menuList);
    }

    @Override
    public void saveRoleMenuRelationShip(String roleId, String[] menus) {
        // 1.删除原来的角色权限
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));

        // 2.重新构建角色权限
        List<RoleMenu> roleMenuArrayList = Stream.of(menus).map(menu -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setPermissionId(menu);
            return roleMenu;
        }).collect(Collectors.toList());

        // 3.批量保存
        roleMenuService.saveBatch(roleMenuArrayList);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList;

        if (this.isSysAdmin(id)) {
            // 如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllMenuValue();
        } else {
            // 否则就不是系统管理员，根据用户查询菜单权限
            selectPermissionValueList = baseMapper.selectMenuValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<Menu> selectMenuList;

        if (this.isSysAdmin(id)) {
            // 如果是超级管理员，获取所有菜单
            selectMenuList = baseMapper.selectList(null);
        } else {
            selectMenuList = baseMapper.selectMenuByUserId(id);
        }
        // 1.构建树形菜单
        List<Menu> menuList = buildTreeMenu(selectMenuList);

        // 2.返回给前端json数据
        return buildMenuWebList(menuList);
    }

    private List<JSONObject> buildMenuWebList(List<Menu> menuList) {
        List<JSONObject> menus = new ArrayList<>();

        if (menuList.size() == 1) {
            // 左侧一级菜单
            menuList.get(0).getChildren().forEach(oneMenu -> {
                JSONObject oneMenuObj = this.generateJsonObj(oneMenu, false);

                List<JSONObject> children = new ArrayList<>();
                oneMenu.getChildren().forEach(twoMenu -> {
                    JSONObject twoMenuObj = this.generateJsonObj(twoMenu, false);
                    children.add(twoMenuObj);

                    twoMenu.getChildren().forEach(three -> {
                        if (StringUtils.isEmpty(three.getPath())) {
                            return;
                        }
                        JSONObject threeMenu = this.generateJsonObj(three, true);
                        children.add(threeMenu);
                    });
                });
                oneMenuObj.put("children", children);
                menus.add(oneMenuObj);
            });
        }
        return menus;
    }

    /**
     * 生成菜单信息JSON对象
     */
    private JSONObject generateJsonObj(Menu menu, Boolean whetherDisplay) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("path", menu.getPath());
        jsonObj.put("component", menu.getComponent());
        jsonObj.put("hidden", whetherDisplay);

        JSONObject oneMeta = new JSONObject();
        oneMeta.put("title", menu.getName());

        if (!StringUtils.isEmpty(menu.getIcon())) {
            oneMeta.put("icon", menu.getIcon());
        }
        jsonObj.put("meta", oneMeta);

        return jsonObj;
    }

    /**
     * 判断用户是否是系统管理员
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        return !ObjectUtils.isEmpty(user) && "admin".equals(user.getUsername());
    }

    /**
     * 构建树形菜单
     */
    private List<Menu> buildTreeMenu(List<Menu> menuList) {
        // 最终返回的树级菜单
        List<Menu> menus = new ArrayList<>();

        menuList.forEach(menu -> {

            // 查找最顶级的菜单
            if ("0".equals(menu.getPid())) {

                // 设置顶级菜单的级别
                menu.setLevel(1);
                // 构造子菜单
                buildChildrenMenu(menu, menuList);
                menus.add(menu);
            }
        });
        return menus;
    }

    /**
     * 构建子菜单
     */
    private void buildChildrenMenu(Menu menu, List<Menu> menuList) {
        // 从menuList当中查找当前的子菜单
        menuList.forEach(m -> {
            // 判断是不是自己的子菜单
            if (menu.getId().equals(m.getPid())) {
                // 是子菜单
                int level = menu.getLevel() + 1;
                // 设置级别
                m.setLevel(level);
                // 递归设置子菜单
                menu.getChildren().add(m);
                buildChildrenMenu(m, menuList);
            }
        });
    }

    /**
     * 查询子菜单
     */
    private void selectChildMenu(String id, List<String> idList) {
        // 查询出当前id子菜单的条件
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>().eq("pid", id).select("id");

        // 开始查询
        List<Menu> childMenuList = baseMapper.selectList(queryWrapper);

        if (childMenuList.size() > 0) {
            // 取出子菜单id放到集合当中
            childMenuList.forEach(menu -> {
                idList.add(menu.getId());
                this.selectChildMenu(menu.getId(), idList);
            });
        }
    }
}