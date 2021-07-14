package com.carl.blog.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: Carl
 * @Date: 2021/05/06/13:12
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel("校验密码请求参数")
public class SysUserCheckPasswordREQ implements Serializable {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
}
