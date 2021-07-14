package com.carl.blog.question.controller;


import com.carl.blog.entities.Question;
import com.carl.blog.question.req.QuestionUserREQ;
import com.carl.blog.question.service.IQuestionService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 问题信息表 前端控制器
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
@Api(value = "问答管理接口", description = "提供问答的增、删、改、查")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @ApiOperation("修改问题信息接口")
    @PutMapping
    public Result update(@RequestBody Question question) {
        return questionService.updateOrSave(question);
    }

    @ApiOperation("新增问题信息接口")
    @PostMapping
    public Result save(@RequestBody Question question) {
        return questionService.updateOrSave(question);
    }

    @ApiOperation("删除问题信息接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        return questionService.deleteById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题id", required = true),
            @ApiImplicitParam(name = "count", value = "点赞数（1或-1）", required = true)
    })
    @ApiOperation("更新点赞数")
    @PutMapping("/thumb/{id}/{count}")
    public Result updateThumbUp(@PathVariable("id") String id,
                                @PathVariable("count") int count) {
        return questionService.updateThumbUp(id, count);
    }

    @ApiOperation("根据用户id查询问题列表")
    @PostMapping("/user")
    public Result findListByUserId(@RequestBody QuestionUserREQ req) {
        return questionService.findListByUserId(req);
    }

    @ApiOperation("查询提问总记录数")
    @GetMapping("/total")   //question/question/total
    public Result questionTotal() {
        return questionService.getQuestionTotal();
    }
}
