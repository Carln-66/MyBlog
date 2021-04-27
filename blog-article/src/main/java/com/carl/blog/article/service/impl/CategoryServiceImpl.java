package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.article.mapper.CategoryMapper;
import com.carl.blog.article.req.CategoryREQ;
import com.carl.blog.article.service.ICategoryService;
import com.carl.blog.entities.Category;
import com.carl.blog.util.base.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:36
 * @Description: 文章分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public Result queryPage(CategoryREQ req) {

        //查询条件对象
        QueryWrapper<Category> wrapper = new QueryWrapper<>();

        //分类名称
        if (StringUtils.isNotEmpty(req.getName())) {
            wrapper.like("name", req.getName());
        }
        //分类状态
        if (req.getStatus() != null) {
            wrapper.eq("status", req.getStatus());
        }
        //排序
        wrapper.orderByDesc("status").orderByAsc("sort");

        //第一个参数是Page分页对象，第二个参数是查询条件
        IPage<Category> categoryIPage = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(categoryIPage);
    }

    @Override
    public Result findAllNormal() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);    //1:正常，0:停用
        List<Category> categories = baseMapper.selectList(wrapper);
        return Result.ok(categories);
    }

    @Override
    public boolean updateById(Category category) {
        //设置更新时间
        category.setUpdateDate(new Date());
        return super.updateById(category);
    }
}
