package com.carl.blog.article.service;

import com.carl.blog.article.req.ArticleListREQ;
import com.carl.blog.article.req.ArticleREQ;
import com.carl.blog.article.req.ArticleUserREQ;
import com.carl.blog.entities.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.util.base.Result;
import com.carl.blog.util.enums.ArticleStatusEnum;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
public interface IArticleService extends IService<Article> {
    /**
     * 条件分页查询文章列表
     * @return
     */
    Result queryPage(ArticleREQ req);

    /**
     * 通过文章id查询文章详情及标签
     * @param id
     * @return
     */
    Result findArticleAndLabelById(String id);

    /**
     * 修改或新增文章
     * @param article
     * @return
     */
    Result updateOrSave(Article article);

    /**
     * 修改状态
     * @param id 文章id
     * @param articleStatusEnum 状态枚举类
     * @return
     */
    Result updateStatus(String id, ArticleStatusEnum articleStatusEnum);

    /**
     * 根据用户ID查询公开或未公开的文章列表
     * @return
     */
    Result findListByUserId(ArticleUserREQ req);

    /**
     * 根据文章id更新点赞数
     * @param id 文章id
     * @param count 点赞+1，取消点赞-1
     * @return
     */
    Result updateThumbUp(String id, int count);

    /**
     * 更新文章浏览次数
     * @param id 文章id
     * @return
     */
    Result updateViewCount(String id);

    /**
     * 通过分类Id或标签id查询公开且已审核通过的文章
     * @Param req 分类id或标签id，分类对象
     * @return
     */
    Result findListByLabelIdOrCategoryId(ArticleListREQ req);
}
