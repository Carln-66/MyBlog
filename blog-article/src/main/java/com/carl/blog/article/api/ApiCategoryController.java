package com.carl.blog.article.api;

import com.carl.blog.article.service.ICategoryService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Carl
 * @Date: 2021/04/28/14:15
 * @Description: 控制层接口，不需要通过身份认证就可以进行访问
 */
@Api(value = "分类管理API接口", description = "分类管理API接口，不需要通过身份认证即可访问")
@RestController
@RequestMapping("/api/category")
public class ApiCategoryController {
    @Autowired
    private ICategoryService categoryService;

    @ApiOperation("获取所有正常状态的分类接口--公开API接口")
    @GetMapping("/list")    //localhost:8001/article/api/category/list
    public Result list() {
        return categoryService.findAllNormal();
    }

    @ApiOperation("查询正常状态的分类及分类下的所有标签--公开API接口")
    @GetMapping("/label/list")  //请求地址： localhost:8001/article/api/category/label/list
    public Result findCategoryAndLabel() {
        return categoryService.findCategoryAndLabel();
    }
}
