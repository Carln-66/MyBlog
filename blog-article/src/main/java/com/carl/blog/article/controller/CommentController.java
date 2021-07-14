package com.carl.blog.article.controller;


import com.carl.blog.article.service.ICommentService;
import com.carl.blog.entities.Comment;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论信息表 前端控制器
 * </p>
 *
 * @author Carl
 * @since 2021-04-30
 */
@Api(value = "评论管理接口", description = "提供评论的增、删、改、查")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    //该接口应该是公开api接口，将其放入ApiCommentController类中
//    @ApiOperation("通过文章id递归查询所有评论")
//    @ApiImplicitParam(name = "articleId", value = "文章ID", required = true)
//    @GetMapping("/list/{articleId}")
//    public Result findByArticleId(@PathVariable("articleId") String articleId) {
//        return commentService.findByArticleId(articleId);
//    }

    @ApiImplicitParam(name = "id", value = "评论id", required = true)
    @ApiOperation("删除评论接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return commentService.deleteById(id);
    }

    @ApiOperation("新增评论信息接口")
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return Result.ok();
    }
}
