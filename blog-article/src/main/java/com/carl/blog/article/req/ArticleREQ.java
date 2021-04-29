package com.carl.blog.article.req;

import com.carl.blog.entities.Article;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/04/28/14:36
 * @Description: 文章的请求类
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ArticleREQ对象", description = "文章查询条件")
public class ArticleREQ extends BaseRequest<Article> {
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章状态（0：已删除；1：未审核；2：审核通过；3：审核未通过）")
    private Integer status;
}
