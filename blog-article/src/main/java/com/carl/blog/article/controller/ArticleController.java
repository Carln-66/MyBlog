package com.carl.blog.article.controller;


import com.carl.blog.article.req.ArticleREQ;
import com.carl.blog.article.req.ArticleUserREQ;
import com.carl.blog.article.service.IArticleService;
import com.carl.blog.entities.Article;
import com.carl.blog.util.base.Result;
import com.carl.blog.util.enums.ArticleStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('article:search')") //在请求方法之前会校验用户是否有对应的权限，如果有则可以调用到此方法，如果没有则调用失败
    @ApiOperation("根据文章标题和状态查询文章分页列表接口")
    @PostMapping("/search") //请求地址: localhost:8001/article/article/search
    public Result search(@RequestBody ArticleREQ req) {
        return articleService.queryPage(req);
    }

    @PreAuthorize("hasAnyAuthority('article:view', 'article:audit')")   //有两个权限之一就可以调用此接口
    @ApiOperation("查询文章详情接口")
    @ApiImplicitParam(name = "id", value = "文章ID", required = true)
    @GetMapping("/{id}")
    public Result view(@PathVariable String id) {
        //如果想调用用户信息调用以下方法即可
//        SysUser userInfo = AuthUtil.getUserInfo();
        return articleService.findArticleAndLabelById(id);
    }

    @ApiOperation("修改文章信息接口")
    @PutMapping //put请求 localhost:8001/article/article
    public Result update(@RequestBody Article article) {
        return articleService.updateOrSave(article);
    }

    @ApiOperation("新增文章信息接口")
    @PostMapping
    public Result save(@RequestBody Article article) {
        return articleService.updateOrSave(article);
    }

    @ApiImplicitParam(name = "id", value = "文章id", required = true)
    @ApiOperation("删除文章接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        //伪删除，只是更改状态
        return articleService.updateStatus(id, ArticleStatusEnum.DELETE);
    }

    @PreAuthorize("hasAuthority('article:audit')")
    @ApiImplicitParam(name = "id", value = "文章id", required = true)
    @ApiOperation("审核文章通过接口")
    @GetMapping("/audit/success/{id}")
    public Result success(@PathVariable("id") String id) {
        //审核通过
        return articleService.updateStatus(id, ArticleStatusEnum.SUCCESS);
    }

    @PreAuthorize("hasAuthority('article:audit')")
    @ApiImplicitParam(name = "id", value = "文章id", required = true)
    @ApiOperation("审核文章不通过接口")
    @GetMapping("/audit/fail/{id}")
    public Result fail(@PathVariable("id") String id) {
        //审核通过
        return articleService.updateStatus(id, ArticleStatusEnum.FAIL);
    }

    @ApiOperation("根据用户ID查询公开或未公开的文章列表接口")
    @PostMapping("/user")   //article/user
    public Result findListByUserId(@RequestBody ArticleUserREQ req) {
        return articleService.findListByUserId(req);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章ID", required = true),
            @ApiImplicitParam(name = "count", value = "文章点赞操作数", required = true)
    })

    @ApiOperation("更新点赞数")
    @PutMapping("/thumb/{id}/{count}")
    public Result updateThumbUp(@PathVariable("id") String id, @PathVariable("count") int count) {
        return articleService.updateThumbUp(id, count);
    }

    @ApiOperation("统计审核通过并公开的文章总记录数")
    @GetMapping("/total")
    public Result getArticleTotal() {
        return articleService.getArticleTotal();
    }

    @ApiOperation("统计各个分类下的文章数")
    @GetMapping("/category/total")
    public Result categoryTotal() {
        return articleService.selectCategoryTotal();
    }

    @ApiOperation("统计半年来的文章发布数量")
    @GetMapping("/month/total")
    public Result selectMonthArticleTotal() {
        return articleService.selectMonthArticleTotal();
    }
}
