package com.carl.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.entities.SysMenu;
import com.carl.blog.system.req.SysMenuREQ;
import com.carl.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-05-05
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 条件查询菜单列表
     * @param req
     * @return
     */
    Result queryList(SysMenuREQ req);

    /**
     * 根据菜单id删除
     * @param id 菜单id
     * @return
     */
    Result deleteById(String id);

    /**
     * 查询指定id用户所拥有的权限菜单树（目录、菜单、按钮）
     * @param userId
     * @return
     */
    Result findUserMenuTree(String userId);

    /**
     * 通过用户id查询所拥有的权限信息
     * @param userId
     * @return
     */
    List<SysMenu> findByUserId(String userId);

}
