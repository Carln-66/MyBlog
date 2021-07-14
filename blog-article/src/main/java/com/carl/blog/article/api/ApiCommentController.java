package com.carl.blog.article.api;

import com.carl.blog.article.service.ICommentService;
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
 * @Date: 2021/05/01/13:56
 * @Description:
 */
@Api(value = "评论管理API接口", description = "不需要通过身份认证就可以访问接口")
@RequestMapping("/api/comment")
@RestController
public class ApiCommentController {

    @Autowired
    private ICommentService commentService;

    @ApiOperation("通过文章id递归查询所有评论")
    @ApiImplicitParam(name = "articleId", value = "文章ID", required = true)
    @GetMapping("/list/{articleId}")
    public Result findByArticleId(@PathVariable("articleId") String articleId) {
        return commentService.findByArticleId(articleId);
    }
}
