package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carl.blog.article.req.ArticleREQ;
import com.carl.blog.entities.Article;
import com.carl.blog.article.mapper.ArticleMapper;
import com.carl.blog.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.util.base.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Override
    public Result queryPage(ArticleREQ req) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        baseMapper.selectPage(req.getPage(), wrapper);
        //标题
        if (StringUtils.isNotEmpty(req.getTitle())) {
            wrapper.like("title", req.getTitle());
        }
        //状态
        if (req.getStatus() != null) {
            wrapper.eq("status", wrapper);
        }
        //排序
        wrapper.orderByDesc("update_date");
        IPage<Article> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result findArticleAndLabelById(String id) {
        return Result.ok(baseMapper.findArticleAndLabelById(id));
    }
}
