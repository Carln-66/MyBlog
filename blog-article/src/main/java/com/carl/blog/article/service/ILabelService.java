package com.carl.blog.article.service;

import com.carl.blog.article.req.LabelREQ;
import com.carl.blog.entities.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.util.base.Result;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
public interface ILabelService extends IService<Label> {
    /**
     * 条件分页查询标签列表
     * @param req
     * @return
     */
    Result queryPage(LabelREQ req);
}
