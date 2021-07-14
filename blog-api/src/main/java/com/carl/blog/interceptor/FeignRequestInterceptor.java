package com.carl.blog.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Carl
 * @Date: 2021/05/11/21:00
 * @Description: 使用Feign进行远程调用时，先经过此拦截器，在其拦截其中将请求头带上访问令牌
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //通过RequestContextHolder工具来获取请求相关变量
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isNotEmpty(token)) {    //Bearer xxxxxxx
                //使用feign远程调用时，请求头就会带上访问令牌
                requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
            }
        }
    }
}
