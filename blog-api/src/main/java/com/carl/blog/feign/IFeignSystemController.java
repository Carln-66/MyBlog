package com.carl.blog.feign;

import com.carl.blog.entities.SysMenu;
import com.carl.blog.entities.SysUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/05/07/18:47
 * @Description: 远程调用blog-system微服务中的接口
 */
@FeignClient(value = "system-server", path = "/system")
public interface IFeignSystemController {
    @ApiImplicitParam(name = "username", value = "用户名", required = true)
    @ApiOperation("Feign接口-通过用户名查询用户信息")
    @GetMapping("/api/feign/user/{username}")
    SysUser findUserByUsername(@PathVariable("username") String username);

    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @ApiOperation("Feign接口-通过用户id查询拥有权限")
    @GetMapping("/api/feign/menu/{userId}")
    List<SysMenu> findMenuListByUserId(@PathVariable("userId") String userId);
}
