package com.carl.blog.question.feign;

import com.carl.blog.feign.IFeignQuestionController;
import com.carl.blog.feign.req.UserInfoREQ;
import com.carl.blog.question.service.IQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Carl
 * @Date: 2021/05/06/17:38
 * @Description:
 */
@RestController
@Api(value = "被远程调用的问题微服务接口", description = "被远程调用的问题微服务接口")
public class FeignArticleController implements IFeignQuestionController {
    @Autowired
    private IQuestionService questionService;

    @Override
    public boolean updateUserInfo(UserInfoREQ req) {
        return questionService.updateUserInfo(req);
    }
}
