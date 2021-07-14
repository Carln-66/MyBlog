package com.carl.blog.question.service;

import com.carl.blog.entities.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.util.base.Result;

/**
 * <p>
 * 回答信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
public interface IReplyService extends IService<Reply> {
    /**
     * 通过问题id递归查询所有回答信息
     * @param questionId
     * @return
     */
    Result findByQuestionId(String questionId);

    /**
     * 通过回答id递归删除
     * @param id 回答id
     * @return
     */
    Result deleteById(String id);

    /**
     * 新增回答信息及更新问题表中的回复数
     * @param reply
     * @return
     */
    Result add(Reply reply);
}
