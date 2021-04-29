package com.carl.blog.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carl.blog.article.req.ArticleListREQ;
import com.carl.blog.entities.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 通过文章id删除文章标签
     * @param articleId
     * @return
     */
    boolean deleteArticleLabel(@Param("articleId") String articleId);

    /**
     * 新增文章标签中间表数据
     * @param articleId 文章id
     * @param labelIds 标签id集合
     * @return
     */
    boolean saveArticleLabel(@Param("articleId") String articleId, @Param("labelIds") List<String> labelIds);

    /**
     * 通过分类Id或标签id查询公开且已审核通过的文章
     * @param page 分页对象
     * @param req 条件
     * @return
     */
    IPage<Article> findListByLabelIdOrCategoryId(IPage<Article> page, @Param("req")ArticleListREQ req);
}
