package com.carl.blog.system.mapper;

import com.carl.blog.entities.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过角色id删除角色菜单关系表数据
     * @param roleId 角色id
     * @return
     */
    boolean deleteRoleMenuByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色id查询角色拥有的权限菜单ids
     * @param id 角色id
     * @return 菜单id集合
     */
    List<String> findMenuIdsById(@Param("id") String id);

    /**
     * 新增角色菜单权限数据到sys_role_menu
     * @param roleId 角色id
     * @param menuIds 菜单id集合
     * @return
     */
    boolean saveRoleMenu(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds);
}
