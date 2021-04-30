package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carl.blog.article.req.ArticleListREQ;
import com.carl.blog.article.req.ArticleREQ;
import com.carl.blog.article.req.ArticleUserREQ;
import com.carl.blog.entities.Article;
import com.carl.blog.article.mapper.ArticleMapper;
import com.carl.blog.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.util.base.Result;
import com.carl.blog.util.enums.ArticleStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Override
    @Transactional
    public Result updateOrSave(Article article) {
        //1. 如果id不为空，则是更新操作
        if (StringUtils.isNotEmpty(article.getId())) {
            // 更新：先删除文章中间表数据，再新增到中间表
            baseMapper.deleteArticleLabel(article.getId());
            // 更新：将更新时间设置为当前时间
            article.setUpdateDate(new Date());
        }
        // 如果文章不是公开的，直接审核通过，否则待审核
        if (article.getIsPublic() == 0) {   //不公开
            article.setStatus(ArticleStatusEnum.SUCCESS.getCode());
        } else {
            article.setStatus(ArticleStatusEnum.WAIT.getCode());
        }
        //更新或保存文章信息
        super.saveOrUpdate(article);
        //新增标签数据到文章标签中间表中
        if (CollectionUtils.isNotEmpty(article.getLabelIds())) {
            baseMapper.saveArticleLabel(article.getId(), article.getLabelIds());
        }
        //返回文章标签
        return Result.ok(article.getId());
    }

    @Override
    public Result updateStatus(String id, ArticleStatusEnum articleStatusEnum) {
        //查询当前数据库中的数据状态
        Article article = baseMapper.selectById(id);
        //将状态更新
        article.setStatus(articleStatusEnum.getCode());
        article.setUpdateDate(new Date());
        baseMapper.updateById(article);
        return Result.ok();
    }

    @Override
    public Result findListByUserId(ArticleUserREQ req) {
        if (StringUtils.isEmpty(req.getUserId())) {
            return Result.error("无效用户信息");
        }
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", req.getUserId());
        if (req.getIsPublic() != null) {
            wrapper.eq("is_public", req.getIsPublic());
        } else {
            return Result.error("非法请求");
        }
        //排序
        wrapper.orderByDesc("update_date");
        IPage<Article> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result updateThumbUp(String id, int count) {
        if (count != -1 && count != 1) {
            return Result.error("无效操作");
        }
        if (StringUtils.isEmpty(id)) {
            return Result.error("无效操作");
        }
        //查询这篇文章现有数据，查询到之后将点赞数进行更新
        Article article = baseMapper.selectById(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        if (article.getThumbUp() <= 0 && count == -1) {
            return Result.error("无效操作");
        }
        article.setThumbUp(article.getThumbUp() + count);
        baseMapper.updateById(article);
        return Result.ok();
    }

    @Override
    public Result updateViewCount(String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error("id为空，操作无效");
        }
        Article article = baseMapper.selectById(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        article.setViewCount(article.getViewCount() + 1);
        baseMapper.updateById(article);
        return Result.ok();
    }

    @Override
    public Result findListByLabelIdOrCategoryId(ArticleListREQ req) {
        IPage<Article> page = baseMapper.findListByLabelIdOrCategoryId(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public Result getArticleTotal() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        //状态是审核通过
        wrapper.eq("status", ArticleStatusEnum.SUCCESS.getCode());
        //状态是公开
        wrapper.eq("is_public", 1);
        Integer total = baseMapper.selectCount(wrapper);
        return Result.ok(total);
    }

    @Override
    public Result selectCategoryTotal() {
        List<Map<String , Object>> maps = baseMapper.selectCategoryTotal();
        //将分类名称单独提取到集合中
        List<Object> nameList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            nameList.add(map.get("name"));
        }
        //封装响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("nameAndValueList", maps);
        data.put("nameList", nameList);
        return Result.ok(data);
    }
}
