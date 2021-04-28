package com.carl.blog.article.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carl.blog.article.req.LabelREQ;
import com.carl.blog.entities.Label;
import com.carl.blog.article.mapper.LabelMapper;
import com.carl.blog.article.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carl.blog.util.base.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Override
    public Result queryPage(LabelREQ req) {
        //条件分页查询
        IPage<Label> page = baseMapper.queryPage(req.getPage(), req);
        return Result.ok(page);
    }

    /**
     * 更新标签的更新时间
     * @param label
     * @return
     */
    @Override
    public boolean updateById(Label label) {
        label.setUpdateDate(new Date());
        return super.updateById(label);
    }
}
