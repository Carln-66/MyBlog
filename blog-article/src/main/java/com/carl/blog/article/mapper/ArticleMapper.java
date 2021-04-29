package com.carl.blog.article.mapper;

import com.carl.blog.entities.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 通过文章id查询文章详情及标签
     * @param id
     * @return
     */
    Article findArticleAndLabelById(String id);
}
