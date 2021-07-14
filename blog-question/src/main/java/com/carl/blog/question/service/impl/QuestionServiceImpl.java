package com.carl.blog.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.entities.Label;
import com.carl.blog.entities.Question;
import com.carl.blog.feign.IFeignArticleController;
import com.carl.blog.feign.req.UserInfoREQ;
import com.carl.blog.question.mapper.QuestionMapper;
import com.carl.blog.question.req.QuestionUserREQ;
import com.carl.blog.question.service.IQuestionService;
import com.carl.blog.util.base.BaseRequest;
import com.carl.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 问题信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Override
    public Result findHotList(BaseRequest<Question> req) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        //1. 状态为1或2，不能查询0（已删除）
        wrapper.in("status", Arrays.asList(1, 2));
        //2. 按回复数降序排列
        wrapper.orderByDesc("reply");
        IPage page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result findNewList(BaseRequest<Question> req) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        //1. 状态为1或2，不能查询0（已删除）
        wrapper.in("status", Arrays.asList(1, 2));
        //2. 按更新时间降序排列
        wrapper.orderByDesc("update_date");
        IPage<Question> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result findWaitList(BaseRequest<Question> req) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        //1. 状态为1或2，不能查询0（已删除）
        wrapper.in("status", Arrays.asList(1, 2));
        //2. 回复数为0
        wrapper.eq("reply", 0);
        //3. 按创建时间降序排列
        wrapper.orderByDesc("create_date");
        IPage<Question> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result findListByLabelId(BaseRequest<Question> req, String labelId) {
        if (StringUtils.isEmpty(labelId)) {
            return Result.error("标签ID不能为空");
        }
        IPage<Question> page = baseMapper.findListByLabelId(req.getPage(), labelId);
        return Result.ok(page);
    }

    @Autowired
    private IFeignArticleController feignArticleController;

    @Override
    public Result findById(String id) {
        //1. 查询问题详情与标签id
        Question question = baseMapper.findQuestionAndLabelIdsById(id);
        if (question == null) {
            return Result.error("无问题详情");
        }
        //2. Feign远程调用Article服务查询标签信息
        if (CollectionUtils.isNotEmpty(question.getLabelIds())) {
            List<Label> labelList = feignArticleController.getLabelListByIds(question.getLabelIds());
            question.setLabelList(labelList);
        }
        return Result.ok(question);
    }

    @Override
    public Result updateViewCount(String id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error("无效操作");
        }
        //查询问题详情
        Question question = baseMapper.selectById(id);
        if (question == null) {
            return Result.error("问题不存在");
        }
        question.setViewCount(question.getViewCount() + 1);
        baseMapper.updateById(question);
        return Result.ok();
    }

    @Override
    public Result updateOrSave(Question question) {
        //1. 如果是更新，删除question_label问题标签中间表数据，且设置更新时间
        if (StringUtils.isNotEmpty(question.getId())) {
            baseMapper.deleteQuestionLabel(question.getId());
            question.setUpdateDate(new Date());
        }
        //2. 问题数据新增或更新到question表
        super.saveOrUpdate(question);
        //3. 问题所属标签保存到question_label表
        if (CollectionUtils.isNotEmpty(question.getLabelIds())) {
            baseMapper.saveQuestionLabel(question.getId(), question.getLabelIds());
        }
        //4. 响应当前操作的问题id
        return Result.ok(question.getId());
    }

    @Override
    public Result deleteById(String id) {
        return this.updateStatus(id, 0);   //0表示删除
    }

    @Override
    public Result updateThumbUp(String id, int count) {
        if (count != -1 && count != 1) {
            return Result.error("无效操作");
        }
        if (StringUtils.isEmpty(id)) {
            return Result.error("无效操作");
        }
        Question question = baseMapper.selectById(id);
        if (question == null) {
            return Result.error("问题不存在");
        }
        if (question.getThumbUp() <= 0 && count == -1) {
            return Result.error("无效操作");
        }
        if (question.getStatus() == 0) {
            return Result.error("问题已被删除");
        }
        question.setThumbUp(question.getThumbUp() + count);
        return Result.ok(baseMapper.updateById(question));
    }

    @Override
    public Result findListByUserId(QuestionUserREQ req) {
        if (StringUtils.isEmpty(req.getUserId())) {
            return Result.error("无效用户信息");
        }
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.in("status", Arrays.asList(1, 2));
        //根据用户id查询
        wrapper.eq("user_id", req.getUserId());
        //排序
        wrapper.orderByDesc("update_date");
        IPage<Question> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result getQuestionTotal() {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        //问题状态不能为已删除（0）
        wrapper.in("status", Arrays.asList(1, 2));
        Integer total = baseMapper.selectCount(wrapper);
        return Result.ok(total);
    }

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return baseMapper.updateUserInfo(req);
    }

    /**
     * 修改问题状态值
     * @param id 问题id
     * @param status 问题状态
     * @return
     */
    public Result updateStatus(String id, Integer status) {
        //通过问题id查询问题详情
        Question question = baseMapper.selectById(id);
        //设置新状态值
        question.setStatus(status);
        question.setUpdateDate(new Date());

        baseMapper.updateById(question);
        return Result.ok();
    }
}
