package com.carl.blog.feign;

import com.carl.blog.entities.Label;
import com.carl.blog.feign.req.UserInfoREQ;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/05/04/16:48
 * @Description:
 */
//value指定目标微服务名称，path指定目标微服务上下文路径contextPath值，若没有设定该值，则不写path
@FeignClient(value = "article-server", path = "/article")
public interface IFeignArticleController {

    //allowMultiple = true 表示是数组格式的参数
    //dataType = "String" 表示数组中参数的类型
    @ApiImplicitParam(allowMultiple = true, dataType = "String", name = "ids", value = "标签ID集合", required = true)
    @ApiOperation("Feign接口，根据标签ids查询对应的标签信息")
    @GetMapping("/api/feign/label/list/{ids}")
    List<Label> getLabelListByIds(@PathVariable("ids") List<String> labelIds);

    @ApiOperation("feign接口-更新文章表和评论表中的用户信息")
    @PutMapping("/feign/article/user")
    boolean updateUserInfo(@RequestBody UserInfoREQ req);
}
