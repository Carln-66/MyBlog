package com.carl.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.blog.entities.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id查询当前用户所拥有的角色
     *
     * @param id 用户id
     * @return
     */
    List<String> findRoleIdsById(@Param("id") String id);

    /**
     * 根据用户id删除用户角色表中的数据
     *
     * @param id 用户id
     * @return
     */
    boolean deleteUserRoleByUserId(@Param("userId") String id);

    /**
     * 新增用户角色关系表数据
     * @param userId 用户id
     * @param roleIds 角色id
     * @return
     */
    boolean saveUserRole(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);
}
