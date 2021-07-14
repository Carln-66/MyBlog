package com.carl.blog.question.controller;


import com.carl.blog.entities.Reply;
import com.carl.blog.question.service.IReplyService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 回答信息表 前端控制器
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
@Api(value = "回答管理接口", description = "提供回答的增、删、改、查")
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private IReplyService replyService;

    @ApiOperation("删除回答评论接口")
    @ApiImplicitParam(name = "id", value = "回答评论id", required = true)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return Result.ok(replyService.deleteById(id));
    }

    @ApiOperation("新增回答信息接口")
    @PostMapping
    public Result add(@RequestBody Reply reply) {
        return replyService.add(reply);
    }
}
