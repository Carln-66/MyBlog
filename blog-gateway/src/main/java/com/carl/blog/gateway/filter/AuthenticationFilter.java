package com.carl.blog.gateway.filter;

import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Auther: Carl
 * @Date: 2021/05/12/13:59
 * @Description: 定义验证请求头是否带有Authorization
 */
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    // 数组为了程序以后方便扩展
    private static final String[] white = {"/api/"};

    @Override
    public Mono <Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //请求对象
        ServerHttpRequest request = exchange.getRequest();
        //响应对象
        ServerHttpResponse response = exchange.getResponse();
        // /question/api/question/1
        String path = request.getPath().pathWithinApplication().value();
        //  公开api开头的情况应该放行
        if (StringUtils.indexOfAny(path, white) != -1) {
            //放行
            return chain.filter(exchange);
        }
        //请求头信息
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            //没有带authorization请求头，则响应错误信息
            //封装响应信息
            JSONObject message = new JSONObject();
            message.put("code", 1401);
            message.put("message", "缺少身份凭证");

            //转换响应消息内容对象为字节
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            //设置响应状态码401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //设置响应对象内容并且指定编码，否则在浏览器中会出现中文乱码
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            //返回响应对象
            return response.writeWith(Mono.just(buffer));
        }
        //如果请求头不为空，验证通过，则放行此过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //过滤器执行顺序，越小越优先执行
        return 0;
    }
}
