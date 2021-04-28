package com.carl.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.article.req.CategoryREQ;
import com.carl.blog.entities.Category;
import com.carl.blog.util.base.Result;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:32
 * @Description: 文章分类表-服务类（业务层）
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 分页条件查询分类信息
     * @param req 条件
     * @return 返回状态信息
     */
    Result queryPage(CategoryREQ req);

    /**
     * 查询正常状态的分类
     */
    Result findAllNormal();

    /**
     * 查询正常状态的分类以及分类下的所有标签
     */
    Result findCategoryAndLabel();
}
