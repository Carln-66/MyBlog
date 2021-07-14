package com.carl.blog.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Carl
 * @Date: 2021/05/06/19:39
 * @Description:
 */
@ApiModel("用户注册信息请求类")
@Data
public class RegisterREQ implements Serializable {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    private String reqPassword;

}
