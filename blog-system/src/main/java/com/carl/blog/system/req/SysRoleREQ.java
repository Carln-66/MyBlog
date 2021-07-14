package com.carl.blog.system.req;

import com.carl.blog.entities.SysRole;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/05/05/17:25
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysRoleREQ对象", description = "角色查询条件")
public class SysRoleREQ extends BaseRequest<SysRole> {
    @ApiModelProperty(value = "角色名称")
    private String name;
}
