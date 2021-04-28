package com.carl.blog.article.controller;

import com.carl.blog.article.req.CategoryREQ;
import com.carl.blog.article.service.ICategoryService;
import com.carl.blog.entities.Category;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:55
 * @Description: 文章分类表--前端控制器
 */
@Api(value = "分类管理接口", description = "提供分类的增、删、改、查")
@RequestMapping("/category")
@RestController    //所有方法都会返回json字符串进行响应
public class CategoryController {

    @Autowired  //自动注入服务
    private ICategoryService categoryService;

    /**
     * 分页条件查询分类信息
     *
     * @param req
     * @return 将对象转为json
     */
    @ApiOperation("根据分类名称与状态查询分类列表接口")
    @PostMapping("/search") //请求路径：localhost:8001/article/category/search
    public Result search(@RequestBody CategoryREQ req) {
        return categoryService.queryPage(req);
    }

    /**
     * 按照id查询
     */
    @ApiOperation("查询类别详情接口")
    @ApiImplicitParam(name = "id", value = "类别ID", required = true)
    @GetMapping("/{id}")    //  localhost:8001/article/category/{id}
    public Result view(@PathVariable("id") String id) {
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @ApiOperation("修改类别信息接口")
    @PutMapping //putMapping
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok();
    }

    @ApiOperation("新增类别信息接口")
    @PostMapping    //post方式调用接口    /category
    public Result save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok();
    }

    @ApiImplicitParam(name = "id", value = "类别ID", required = true)
    @ApiOperation("删除类别接口")
    @DeleteMapping("/{id}")     //  /category/{id}
    public Result delete(@PathVariable("id") String id) {
        categoryService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("获取所有正常状态的分类接口")
    @GetMapping("/list")    //localhost:8001/article/category/list
    public Result list() {
        return categoryService.findAllNormal();
    }

    @ApiOperation("查询正常状态的分类及分类下的所有标签")
    @GetMapping("/label/list")  //请求地址： localhost:8001/article/category/label/list
    public Result findCategoryAndLabel() {
        return categoryService.findCategoryAndLabel();
    }
}
