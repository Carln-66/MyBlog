package com.carl.blog.article.controller;

import com.carl.blog.article.req.CategoryREQ;
import com.carl.blog.article.service.ICategoryService;
import com.carl.blog.util.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:55
 * @Description: 文章分类表--前端控制器
 */
@RequestMapping
@RestController("/category")     //所有方法都会返回json字符串进行响应
public class CategoryController {

    @Autowired  //自动注入服务
    private ICategoryService categoryService;

    /**
     * 分页条件查询分类信息
     * @param req
     * @return 将对象转为json
     */
    @PostMapping("/search") //请求路径：category/search
    public Result search(@RequestBody CategoryREQ req) {
        return categoryService.queryPage(req);
    }
}
