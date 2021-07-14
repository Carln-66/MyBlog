package com.carl.blog.question.api;

import com.carl.blog.question.service.IReplyService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Carl
 * @Date: 2021/05/03/16:30
 * @Description:
 */
@Api(value = "回答管理API接口", description = "不需要通过身份认证即可访问")
@RequestMapping("/api/reply")
@RestController
public class ApiReplyController {

    @Autowired
    private IReplyService replyService;

    @ApiImplicitParam(name = "questionId", value = "问题id", required = true)
    @ApiOperation("通过问题ID递归所有的回答及子评论信息")
    @GetMapping("/list/{questionId}")
    public Result findQuestionId(@PathVariable String questionId) {
        return replyService.findByQuestionId(questionId);
    }
}
