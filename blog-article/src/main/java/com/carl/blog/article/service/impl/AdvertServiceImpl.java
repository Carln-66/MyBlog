package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.article.mapper.AdvertMapper;
import com.carl.blog.article.req.AdvertREQ;
import com.carl.blog.article.service.IAdvertService;
import com.carl.blog.entities.Advert;
import com.carl.blog.util.aliyun.AliyunUtil;
import com.carl.blog.util.base.Result;
import com.carl.blog.util.properties.BlogProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 广告信息表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
@Service
public class AdvertServiceImpl extends ServiceImpl<AdvertMapper, Advert> implements IAdvertService {

    @Autowired
    private BlogProperties blogProperties;

    @Override
    public Result queryPage(AdvertREQ req) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<Advert>();
        if (StringUtils.isNotEmpty(req.getTitle())) {
            wrapper.like("title", req.getTitle());
        }
        if (req.getStatus() != null) {
            wrapper.eq("status", req.getStatus());
        }
        IPage<Advert> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    @Transactional
    public Result deleteById(String id) {
        //1. 先通过广告ID查询图片地址
        String imageUrl = baseMapper.selectById(id).getImageUrl();

        //2. 删除表中的广告信息
        baseMapper.deleteById(id);

        //3. 删除oss上的图片
        if (StringUtils.isNotEmpty(imageUrl)) {
            AliyunUtil.delete(imageUrl, blogProperties.getAliyun());
        }
        return null;
    }

    @Override
    public Result findByPosition(int position) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        //状态正常 status = 1
        wrapper.eq("status", 1);
        //位置
        wrapper.eq("position", position);
        //排序
        wrapper.orderByAsc("sort");
        List<Advert> list = baseMapper.selectList(wrapper);
        return Result.ok(list);
    }
}
