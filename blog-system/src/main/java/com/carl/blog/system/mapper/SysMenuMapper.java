package com.carl.blog.system.mapper;

import com.carl.blog.entities.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单信息表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询指定id用户所拥有的菜单权限（目录、菜单、按钮）
     * @param userId
     * @return
     */
    List<SysMenu> findByUserId(@Param("userId") String userId);
}
