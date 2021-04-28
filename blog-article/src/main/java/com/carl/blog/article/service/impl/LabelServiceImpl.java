package com.carl.blog.article.service.impl;

import com.carl.blog.entities.Label;
import com.carl.blog.article.mapper.LabelMapper;
import com.carl.blog.article.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
