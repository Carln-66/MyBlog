package com.carl.blog.article.feign;

import com.carl.blog.article.service.IArticleService;
import com.carl.blog.article.service.ILabelService;
import com.carl.blog.entities.Label;
import com.carl.blog.feign.IFeignArticleController;
import com.carl.blog.feign.req.UserInfoREQ;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/05/04/16:56
 * @Description:
 */
@Api(value = "被远程调用的文章微服务接口", description = "被远程调用的文章微服务接口")
@RestController
public class FeignArticleController implements IFeignArticleController {

    @Autowired
    private ILabelService labelService;

    @Override
    public List<Label> getLabelListByIds(List<String> labelIds) {
        //IService<Label>中已经实现了此批量查询标签信息的功能，mybatis-plus已经提供的
        return labelService.listByIds(labelIds);
    }

    @Autowired
    private IArticleService articleService;

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return articleService.updateUserInfo(req);
    }
}
