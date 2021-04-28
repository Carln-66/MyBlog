package com.carl.blog.article.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carl.blog.article.req.LabelREQ;
import com.carl.blog.entities.Label;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author Carl
 * @since 2021-04-28
 */
public interface LabelMapper extends BaseMapper<Label> {
    /**
     * 若自定义sql需要分页查询，只要在mapper写不带分页功能的查询sql语句，mybatis-plus会自动将sql进行分页
     *第一个参数必须传入分页对象Page，第二个参数是查询条件的参数
     * 最终会将查询到的数据封装到IPage实现类中
     * @return
     */
    IPage<Label> queryPage(IPage<Label> page, @Param(("req")) LabelREQ labelREQ);
}
