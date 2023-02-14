package top.it6666.service_auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_auth.entity.Role;
import top.it6666.service_auth.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
@Api(tags = "角色组")
@RestController
@RequestMapping("/service_auth/admin/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/getRolePageList/{page}/{limit}")
    public ResponseResult index(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                Role role) {
        // 开启分页
        Page<Role> pageParam = new Page<>(page, limit);

        // 构建查询条件
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name", role.getRoleName());
        }

        // 根据查询条件进行分页
        roleService.page(pageParam, wrapper);

        // 返回结果
        return ResponseResult.ok()
                .data("items", pageParam.getRecords())
                .data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/getRoleById/{id}")
    public ResponseResult get(@PathVariable String id) {
        return ResponseResult.ok().data("item", roleService.getById(id));
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/saveRole")
    public ResponseResult save(@RequestBody Role role) {
        roleService.save(role);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/updateRole")
    public ResponseResult updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/removeRole/{id}")
    public ResponseResult remove(@PathVariable String id) {
        roleService.removeById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id列表删除角色(批量删除)")
    @PostMapping("/batchRemoveRole")
    public ResponseResult batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return ResponseResult.ok();
    }
}