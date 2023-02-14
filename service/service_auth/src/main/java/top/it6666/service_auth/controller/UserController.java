package top.it6666.service_auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.Md5;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_auth.entity.User;
import top.it6666.service_auth.service.RoleService;
import top.it6666.service_auth.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/service_auth/admin/user")
@Api(tags = "用户组")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * <b>
     * 获取管理用户，分页列表
     * </b>
     */
    @ApiOperation(value = "获取管理用户，分页列表")
    @GetMapping("/getUserList/{page}/{limit}")
    public ResponseResult index(@PathVariable Long page, @PathVariable Long limit, User userQueryVo) {
        IPage<User> pageParam = new Page<>(page, limit);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            queryWrapper.like("username", userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, queryWrapper);

        return ResponseResult.ok()
                .data("items", pageModel.getRecords())
                .data("total", pageModel.getTotal());
    }

    /**
     * <b>
     * 新增管理用户
     * </b>
     */
    @ApiOperation(value = "新增管理用户")
    @PostMapping("/saveUser")
    public ResponseResult save(@RequestBody User user) {
        user.setPassword(Md5.encrypt(user.getPassword()));
        userService.save(user);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 修改管理用户
     * </b>
     */
    @ApiOperation(value = "修改管理用户")
    @PostMapping("/updateUser")
    public ResponseResult updateById(@RequestBody User user) {
        user.setPassword(Md5.encrypt(user.getPassword()));
        userService.updateById(user);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 删除管理用户
     * </b>
     */
    @ApiOperation(value = "删除管理用户")
    @PostMapping("/removeUser/{id}")
    public ResponseResult remove(@PathVariable String id) {
        userService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 批量删除用户
     * </b>
     */
    @ApiOperation(value = "批量删除用户")
    @PostMapping("/batchRemoveUser")
    public ResponseResult batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return ResponseResult.ok();
    }

    /**
     * <b>
     * 根据用户id查询用户
     * </b>
     */
    @ApiOperation(value = "根据用户id查询用户")
    @GetMapping("/getUserById/{id}")
    public ResponseResult getUser(@PathVariable String id) {
        return ResponseResult.ok().data("user", userService.getById(id));
    }

    /**
     * <b>
     * 根据用户ID获取角色数据
     * </b>
     */
    @ApiOperation(value = "根据用户ID获取角色数据")
    @GetMapping("/getUserRoleData/{userId}")
    public ResponseResult toAssign(@PathVariable String userId) {
        return ResponseResult.ok().data(roleService.findRoleByUserId(userId));
    }

    /**
     * <b>
     * 根据用户ID分配用户角色
     * </b>
     */
    @ApiOperation(value = "根据用户ID分配用户角色")
    @PostMapping("/doAssignRole")
    public ResponseResult doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRelationShip(userId, roleId);
        return ResponseResult.ok();
    }
}