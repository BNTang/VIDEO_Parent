package top.it6666.service_auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_auth.entity.Menu;
import top.it6666.service_auth.service.MenuService;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单权限 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@Api(tags = "菜单组")
@RestController
@RequestMapping("/service_auth/admin/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 查询所有菜单
     */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/getAllMenu")
    public ResponseResult indexAllMenu() {
        return ResponseResult.ok().data("children", menuService.queryAllMenu());
    }

    /**
     * 递归删除菜单
     */
    @ApiOperation(value = "递归删除菜单")
    @PostMapping("removeMenu/{id}")
    public ResponseResult remove(@PathVariable String id) {
        // 删除当前菜单以及子菜单
        menuService.removeChildById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据角色获取菜单
     */
    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/getMenuWithRoleId/{roleId}")
    public ResponseResult toAssign(@PathVariable String roleId) {
        return ResponseResult.ok().data("children", menuService.selectAllRoleMenu(roleId));
    }

    /**
     * 给角色分配权限
     */
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssignRoleAuth")
    public ResponseResult doAssign(@RequestParam String roleId, @RequestParam String[] menus) {
        menuService.saveRoleMenuRelationShip(roleId, menus);
        return ResponseResult.ok();
    }

    /**
     * 新增菜单
     */
    @ApiOperation(value = "新增菜单")
    @PostMapping("/saveMenu")
    public ResponseResult save(@RequestBody Menu permission) {
        menuService.save(permission);
        return ResponseResult.ok();
    }

    /**
     * 修改菜单
     */
    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    public ResponseResult updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return ResponseResult.ok();
    }
}