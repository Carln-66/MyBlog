package com.carl.blog.system.controller;


import com.carl.blog.entities.SysRole;
import com.carl.blog.system.req.SysRoleREQ;
import com.carl.blog.system.service.ISysRoleService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
@Api(value = "角色管理接口", description = "提供角色的增、删、改、查")
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation("根据角色名称查询角色列表接口")
    @PostMapping("/search")
    public Result search(@RequestBody SysRoleREQ req) {
        return sysRoleService.queryPage(req);
    }

    @ApiOperation("新增角色接口")
    @PostMapping
    public Result save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.ok();
    }

    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    @ApiOperation("查询角色接口")
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") String id) {
        return Result.ok(sysRoleService.getById(id));
    }

    @ApiOperation("更新角色接口")
    @PutMapping
    public Result update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateDate(new Date());
        sysRoleService.updateById(sysRole);
        return Result.ok();
    }

    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    @ApiOperation("删除角色信息及角色菜单关系接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return sysRoleService.deleteById(id);
    }

    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    @ApiOperation("根据角色id查询其有权限的菜单ids接口")
    @GetMapping("/{id}/menu/ids")   //  localhost:8003/system/role/{id}/menu/ids
    public Result findMenuIdsById(@PathVariable("id") String id) {
        return sysRoleService.findMenuIdsById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(allowMultiple = true, dataType = "String", name = "menuIds", value = "菜单id列表", required = true)
    })
    @ApiOperation("新增角色菜单关系数据接口")
    @PostMapping("/{id}/meny/save")
    public Result saveRoleMenu(@PathVariable("id") String id,
                               @RequestBody List<String> menuIds) {
        return sysRoleService.saveRoleMenu(id, menuIds);
    }
}
