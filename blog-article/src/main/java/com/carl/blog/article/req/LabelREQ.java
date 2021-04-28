package com.carl.blog.article.req;

import com.carl.blog.entities.Label;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/04/28/12:11
 * @Description:
 */
@ApiModel(value = "LabelREQ对象", description = "标签查询条件")
@Data
@Accessors
public class LabelREQ extends BaseRequest<Label> {

    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty("分类id")
    private String categoryId;

}
