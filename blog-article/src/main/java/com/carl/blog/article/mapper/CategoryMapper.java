package com.carl.blog.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.blog.entities.Category;

import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:19
 * @Description: 文章分类表Mapper接口
 * BaseMapper 接口是mybatis提供的，提供了许多常用方法供直接使用
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询正常状态的分类以及分类下的所有标签
     * @return
     */
    List<Category> findCategoryAndLabel();
}
