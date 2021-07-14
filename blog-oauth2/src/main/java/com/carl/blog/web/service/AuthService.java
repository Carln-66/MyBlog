package com.carl.blog.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.carl.blog.util.base.Result;
import com.carl.blog.util.enums.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Carl
 * @Date: 2021/05/08/16:17
 * @Description:
 */
@Service
public class AuthService {

    @Autowired  //负载均衡的client
    private LoadBalancerClient loadBalancerClient;

    /**
     * 通过刷新令牌获取新的认证令牌
     *
     * @param header       请求头：客户端信息，clientId: clientSecret
     * @param refreshToken 刷新令牌
     * @return
     */
    public Result refreshToken(String header, String refreshToken) throws HttpProcessException {
        //采用客户端负载均衡，从Nacos服务器中获取对应服务的ip和端口号
        ServiceInstance serviceInstance = loadBalancerClient.choose("auth-server");
        if (serviceInstance == null) {
            return Result.error("未找到有效认证服务器，请稍后重试");
        }

        //请求刷新令牌url
        String refreshTokenUrl = serviceInstance.getUri().toString() + "/auth/oauth/token";

        //封装刷新令牌请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "refresh_token");
        map.put("refresh_token", refreshToken);

        //构建配置请求参数
        Header[] headers = HttpHeader.custom() //自定义请求
                .contentType(HttpHeader.Headers.APP_FORM_URLENCODED)    //数据类型
                .authorization(header) //请求头（客户信息）
                .build();

        //请求配置
        HttpConfig config = HttpConfig.custom().headers(headers).url(refreshTokenUrl).map(map);

        //发送请求，响应认证信息
        String token = HttpClientUtil.post(config);
        JSONObject jsonToken = JSON.parseObject(token);

        //若响应内容中包含了error属性，则获取新的认证失败
        if (StringUtils.isNotEmpty(jsonToken.getString("error"))) {
            return Result.build(ResultEnum.TOKEN_PAST);
        }
        return Result.ok(jsonToken);
    }
}
