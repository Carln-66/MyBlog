package com.carl.blog.system.req;

import com.carl.blog.entities.SysUser;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/05/05/18:32
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserREQ对象", description = "用户查询条件")
public class SysUserREQ extends BaseRequest<SysUser> {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}
