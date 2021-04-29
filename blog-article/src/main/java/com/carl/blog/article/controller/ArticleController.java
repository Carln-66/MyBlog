package com.carl.blog.article.controller;


import com.carl.blog.article.req.ArticleREQ;
import com.carl.blog.article.service.IArticleService;
import com.carl.blog.entities.Article;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章信息表 前端控制器
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
@Api(value = "文章管理接口", description = "提供文章的增、删、改、 查")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation("根据文章标题和状态查询文章分页列表接口")
    @PostMapping("/search") //请求地址: localhost:8001/article/article/search
    public Result search(@RequestBody ArticleREQ req) {
        return articleService.queryPage(req);
    }

    @ApiOperation("查询文章详情接口")
    @ApiImplicitParam(name = "id", value = "文章ID", required = true)
    @GetMapping("/{id}")
    public Result view(@PathVariable String id) {
        return articleService.findArticleAndLabelById(id);
    }

    @ApiOperation("修改文章信息接口")
    @PutMapping //put请求 localhost:8001/article/article
    public Result update(@RequestBody Article article) {
        return articleService.updateOrSave(article);
    }
}
