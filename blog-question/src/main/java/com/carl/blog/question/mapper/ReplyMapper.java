package com.carl.blog.question.mapper;

import com.carl.blog.entities.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 回答信息表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
public interface ReplyMapper extends BaseMapper<Reply> {
    /**
     * 通过问题id递归查询所有回答的信息
     * @return
     */
    List<Reply> findByQuestionId(String questionId);
}
