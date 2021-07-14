package com.carl.blog.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/05/06/13:15
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel("提交密码修改请求参数")
public class SysUserUpdatePasswordREQ extends SysUserCheckPasswordREQ{

    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
    @ApiModelProperty(value = "确认密码", required = true)
    private String repPassword;
}
