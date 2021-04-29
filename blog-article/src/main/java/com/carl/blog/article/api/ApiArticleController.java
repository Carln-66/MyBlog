package com.carl.blog.article.api;

import com.carl.blog.article.service.IArticleService;
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
 * @Date: 2021/04/29/11:04
 * @Description:
 */
@Api(value = "文章管理API接口", description = "文章管理API接口，不需要通过身份  认证即可访问")
@RestController
@RequestMapping("/api/article")
public class ApiArticleController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation("查询文章详情接口")
    @ApiImplicitParam(name = "id", value = "文章ID", required = true)
    @GetMapping("/{id}")
    public Result view(@PathVariable String id) {
        return articleService.findArticleAndLabelById(id);
    }
}
