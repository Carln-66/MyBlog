package com.carl.blog.system.feign;

import com.carl.blog.entities.SysMenu;
import com.carl.blog.entities.SysUser;
import com.carl.blog.feign.IFeignSystemController;
import com.carl.blog.system.service.ISysMenuService;
import com.carl.blog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/05/07/18:52
 * @Description:
 */
@RestController
public class FeignSystemController implements IFeignSystemController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * Feign接口-通过用户名查询用户信息
     * @param username 用户名
     * @return
     */
    @Override
    public SysUser findUserByUsername(String username) {
        return sysUserService.findByUsername(username);
    }

    /**
     * Feign接口-通过用户id查询拥有权限
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysMenu> findMenuListByUserId(String userId) {
        return sysMenuService.findByUserId(userId);
    }
}
