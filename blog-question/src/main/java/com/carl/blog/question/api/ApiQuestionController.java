package com.carl.blog.question.api;

import com.carl.blog.entities.Question;
import com.carl.blog.question.service.IQuestionService;
import com.carl.blog.util.base.BaseRequest;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Carl
 * @Date: 2021/05/02/16:26
 * @Description:
 */
@Api(value = "问答管理接口", description = "问答管理接口，不需要通过身份认证即可访问")
@RestController
@RequestMapping("/api/question")
public class ApiQuestionController {

    @Autowired
    private IQuestionService questionService;

    @ApiOperation("分页查询热门回答列表接口")
    @PostMapping("/hot")    //  localhost:8002/question/api/question/hot
    public Result findHotList(@RequestBody BaseRequest<Question> req) {
        return questionService.findHotList(req);
    }

    @ApiOperation("查询最新问答列表接口")
    @PostMapping("/new")
    public Result findNewList(@RequestBody BaseRequest<Question> req) {
        return questionService.findNewList(req);
    }

    @ApiOperation("分页查询等待回答列表接口")
    @PostMapping("/wait")
    public Result findWaitList(@RequestBody BaseRequest<Question> req) {
        return questionService.findWaitList(req);
    }

    @ApiImplicitParam(name = "labelId", value = "标签ID", required = true)
    @ApiOperation("根据标签ID分页查询问答列表接口")
    @PostMapping("/list/{labelId}")
    public Result findListByLabelId(@RequestBody BaseRequest<Question> req,
                                    @PathVariable("labelId") String labelId) {
        return questionService.findListByLabelId(req, labelId);
    }
    @ApiImplicitParam(name = "id", value = "问题ID", required = true)
    @ApiOperation("查询问题详情接口")
    @GetMapping("/{id}")
    public Result view(@PathVariable("id") String id) {
        return questionService.findById(id);
    }

    @ApiImplicitParam(name = "id", value = "问题id", required = true)
    @ApiOperation("更新浏览数接口")
    @PutMapping("/viewCount/{id}")
    public Result updateViewCount(@PathVariable("id") String id) {
        return questionService.updateViewCount(id);
    }
}
