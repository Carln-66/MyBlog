package com.carl.blog.system.api;

import com.carl.blog.system.req.RegisterREQ;
import com.carl.blog.system.service.ISysUserService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Carl
 * @Date: 2021/05/06/19:33
 * @Description:
 */
@Api(value = "用户管理接口", description = "，不需要通过身份认证即可访问")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class ApiSysUserController {
    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("校验用户名是否存在接口")
    @GetMapping("/username/{username}")
    public Result checkUsername(@PathVariable("username") String username) {
        System.out.println("调用接口");
        return sysUserService.checkUserName(username);
    }

    @ApiOperation("注册用户接口")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterREQ req) {
        return sysUserService.register(req);
    }
}
