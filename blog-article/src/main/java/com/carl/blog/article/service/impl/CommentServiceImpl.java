package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.blog.entities.Comment;
import com.carl.blog.article.mapper.CommentMapper;
import com.carl.blog.article.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-04-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Override
    public Result findByArticleId(String articleId) {
        if (StringUtils.isEmpty(articleId)) {
            return Result.error("文章id不能为空");
        }
        List<Comment> list = baseMapper.findByArticleId(articleId);
        return Result.ok(list);
    }

    @Override
    public Result deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error("评论id不能为空");
        }

        //要删除的所有评论id
        List<String> ids = new ArrayList<>();
        //将当前评论id放入集合中
        ids.add(id);
        //递归所有的评论id，并将id装到要删除的集合中
        this.getIds(ids, id);
        //批量删除集合中的评论id
        baseMapper.deleteBatchIds(ids);
        return Result.ok();
    }

    private void getIds(List<String> ids, String parentId) {
        //查询子评论信息
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        baseMapper.selectList(wrapper);
        List<Comment> commentList = baseMapper.selectList(wrapper);
        //若子评论不为空，则取出每条评论的评论id
        if (CollectionUtils.isNotEmpty(commentList)) {
            for (Comment comment : commentList) {
                String id = comment.getId();
                //将当前查询到的评论id放到要删除的id集合中
                ids.add(id);
                //递归继续查询子评论id
                this.getIds(ids, id);
            }
        }
    }
}
