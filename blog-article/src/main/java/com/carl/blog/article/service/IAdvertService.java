package com.carl.blog.article.service;

import com.carl.blog.article.req.AdvertREQ;
import com.carl.blog.entities.Advert;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carl.blog.util.base.Result;

/**
 * <p>
 * 广告信息表 服务类
 * </p>
 *
 * @author Carl
 * @since 2021-05-02
 */
public interface IAdvertService extends IService<Advert> {
    /**
     * 分页查询广告列表
     * @param req
     * @return
     */
    Result queryPage(AdvertREQ req);

    /**
     * 根据Id删除广告及图片
     * @param id
     * @return
     */
    Result deleteById(String id);

    /**
     * 查询指定广告查询的所有广告信息(状态正常)
     * @param position
     * @return
     */
    Result findByPosition(int position);
}
