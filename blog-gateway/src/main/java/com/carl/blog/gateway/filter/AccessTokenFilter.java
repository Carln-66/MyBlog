package com.carl.blog.gateway.filter;

import com.nimbusds.jose.JWSObject;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

/**
 * @Auther: Carl
 * @Date: 2021/05/12/15:01
 * @Description:
 */
@Component
public class AccessTokenFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 校验请求头中的令牌是否有效，查询Redis中是否存在，不存在则无效jwt
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //请求对象
        ServerHttpRequest request = exchange.getRequest();
        //响应对象
        ServerHttpResponse response = exchange.getResponse();
        //获取token
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = StringUtils.substringAfter(authorization, "Bearer ");
        if (StringUtils.isEmpty(token)) {
            //如果为空，可能是白名单的请求，则直接放行
            return chain.filter(exchange);
        }
        //响应错误信息
        String message = null;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JSONObject jsonObject = jwsObject.getPayload().toJSONObject();
            String jti = jsonObject.get("jti").toString();
            Object value = redisTemplate.opsForValue().get(jti);
            if (value == null) {
                logger.info("令牌已过期 {}", token);
                message = "您的身份已过期，请重新认证";
            }
        } catch (ParseException e) {
            logger.error("解析令牌失败 {}", token);
            message = "无效令牌";
        }
        if (message == null) {
            //如果令牌存在，则通过
            return chain.filter(exchange);
        }
        //响应错误提示信息
        //封装响应信息
        JSONObject result = new JSONObject();
        result.put("code", 1401);
        result.put("message", message);

        //转换响应消息内容对象为字节
        byte[] bits = result.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //设置响应状态码401
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //设置响应对象内容并且指定编码，否则在浏览器中会出现中文乱码
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        //返回响应对象
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        //此过滤器在AuthenticationFilter之后执行
        return 10;
    }
}
