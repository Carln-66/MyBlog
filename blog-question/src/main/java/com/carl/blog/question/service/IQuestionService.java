package com.carl.blog.question.service;

import com.carl.blog.entities.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.feign.req.UserInfoREQ;
import com.carl.blog.question.req.QuestionUserREQ;
import com.carl.blog.util.base.BaseRequest;
import com.carl.blog.util.base.Result;

/**
 * <p>
 * 问题信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
public interface IQuestionService extends IService<Question> {
    /**
     * 分页查询热门问答列表
     * @param req
     * @return
     */
    Result findHotList(BaseRequest<Question> req);

    /**
     * 分页查询最新问答列表
     * @param req
     * @return
     */
    Result findNewList(BaseRequest<Question> req);

    /**
     * 分页查询等待回答列表
     * @param req
     * @return
     */
    Result findWaitList(BaseRequest<Question> req);

    /**
     * 根据标签id分页查询问题列表
     * @param req 分页相关对象
     * @param labelId 标签id
     * @return
     */
    Result findListByLabelId(BaseRequest<Question> req, String labelId);

    /**
     * 通过问题id查询详情
     * @param id 问题id
     * @return
     */
    Result findById(String id);

    /**
     * 更新问题浏览数
     * @param id 问题id
     * @return
     */
    Result updateViewCount(String id);

    /**
     * 修改或新增问题数据
     * @param question
     * @return
     */
    Result updateOrSave(Question question);

    /**
     * 假删除，只是将状态更新为0，表示删除
     * @param id 问题id
     * @return
     */
    Result deleteById(String id);

    /**
     * 更新点赞数
     * @param id 问题id
     * @param count 点赞操作数，1：点赞；-1：取消点赞
     * @return
     */
    Result updateThumbUp(String id, int count);

    /**
     * 根据用户id查询问题列表
     * @param req
     * @return
     */
    Result findListByUserId(QuestionUserREQ req);

    /**
     * 统计提问总记录
     * @return
     */
    Result getQuestionTotal();

    /**
     * 更新问题表和回答表的信息
     * @Param req
     * @return
     */
    boolean updateUserInfo(UserInfoREQ req);
}
