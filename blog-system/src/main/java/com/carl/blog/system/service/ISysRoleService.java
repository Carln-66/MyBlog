package com.carl.blog.system.service;

import com.carl.blog.entities.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.system.req.SysRoleREQ;
import com.carl.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 条件分页查询角色列表
     * @param req
     * @return
     */
    Result queryPage(SysRoleREQ req);

    /**
     * 通过角色id删除角色信息及角色菜单关系表
     * @param id 角色id
     * @return
     */
    Result deleteById(String id);

    /**
     * 根据角色id查询角色拥有的权限菜单ids
     * @param id
     * @return
     */
    Result findMenuIdsById(String id);

    /**
     * 新增角色菜单权限数据到sys_role_menu
     * @param roleId 角色id
     * @return 菜单id集合
     */
    Result saveRoleMenu(String roleId, List<String> menuIds);
}
