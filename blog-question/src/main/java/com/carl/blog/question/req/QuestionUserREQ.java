package com.carl.blog.question.req;

import com.carl.blog.entities.Question;
import com.carl.blog.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: Carl
 * @Date: 2021/05/03/15:22
 * @Description: 关于查询个人问题列表的请求类
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "QuestionUserREQ对象", description = "获取指定用户问题的查询条件")
public class QuestionUserREQ extends BaseRequest<Question> {
    @ApiModelProperty(value = "用户ID")
    private String userId;
}
