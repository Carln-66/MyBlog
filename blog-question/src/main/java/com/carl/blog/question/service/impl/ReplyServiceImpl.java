package com.carl.blog.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.blog.entities.Question;
import com.carl.blog.entities.Reply;
import com.carl.blog.question.mapper.QuestionMapper;
import com.carl.blog.question.mapper.ReplyMapper;
import com.carl.blog.question.service.IReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 回答信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Override
    public Result findByQuestionId(String questionId) {
        if (StringUtils.isEmpty(questionId)) {
            return Result.error("问题id不能为空");
        }
        List<Reply> list = baseMapper.findByQuestionId(questionId);
        return Result.ok(list);
    }

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional  //异常回滚
    public Result deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error("回答评论id不能为空");
        }
        //搜集要删除的回答id
        List<String> ids = new ArrayList<>();
        ids.add(id);    //将当前传递的回答评论id放入集合中
        //递归搜集子评论id
        this.getIds(ids, id);
        //查询问题信息
        //1. 查询回答信息
        Reply reply = baseMapper.selectById(id);
        //批量删除回答的评论信息
        int size = baseMapper.deleteBatchIds(ids);
        if (size > 0) {
            //通过上面1中查询到的回答信息的问题id去查询问题信息
            Question question = questionMapper.selectById(reply.getQuestionId());
            //更新表中的回复数
            question.setReply(question.getReply() - size);
            questionMapper.updateById(question);
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result add(Reply reply) {
        //新增回答信息
        boolean ok = this.save(reply);
        if (ok) {
            //更新问题表中的回复数
            Question question = questionMapper.selectById(reply.getQuestionId());
            question.setReply(question.getReply() + 1);
            questionMapper.updateById(question);
        }
        return Result.ok();
    }

    /**
     * 递归搜集所有要删除的评论id
     * @param ids 要删除的所有评论id集合
     * @param parentId 父评论id
     */
    private void getIds(List<String> ids, String parentId) {
        //查询子评论信息
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<Reply> replies = baseMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(replies)) {
            for (Reply reply : replies) {
                //获取要删除的评论id
                String id = reply.getId();
                ids.add(id);
                //递归继续将id作为parentId查询子评论
                this.getIds(ids, id);
            }
        }
    }
}
