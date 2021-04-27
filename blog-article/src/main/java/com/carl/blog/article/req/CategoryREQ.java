package com.carl.blog.article.req;

import com.carl.blog.entities.Category;
import com.carl.blog.util.base.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:11
 * @Description: 分类请求类，封装查询条件
 */
@Accessors(chain = true)
@Data
public class CategoryREQ extends BaseRequest<Category> {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 状态（1：正常；0：禁用）
     */
    private Integer status;
}
