package com.carl.blog.article.req;

import com.carl.blog.entities.Article;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/04/29/14:53
 * @Description:
 */
@Data
@Accessors
@ApiModel(value = "ArticleUserREQ对象", description = "获取指定用户文章的查询条件")
public class ArticleUserREQ extends BaseRequest<Article> {

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty(value = "是否公开（0：不公开；1：公开）", required = true)
    private Integer isPublic;
}
