package com.carl.blog.article.api;

import com.carl.blog.article.service.IAdvertService;
import com.carl.blog.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Carl
 * @Date: 2021/05/02/15:08
 * @Description:
 */
@Api(value = "广告管理API接口", description = "广告管理API接口，不需要通过身份认证即可访问")
@RestController
@RequestMapping("/api/advert")
public class ApiAdvertController {

    @Autowired
    private IAdvertService advertService;

    @ApiOperation("查询指定广告查询的所有广告信息(状态正常)")
    @ApiImplicitParam(name = "position", value = "广告位置编号", required = true)
    @GetMapping("/show/{position}")
    public Result show(@PathVariable("position") int position) {
        return advertService.findByPosition(position);
    }
}
