package com.carl.blog.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Auther: Carl
 * @Date: 2021/05/10/17:12
 * @Description:
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级别权限控制
@EnableResourceServer   //标识为资源服务器，请求资源接口时必须在请求头带上access_token
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);   //jwt管理令牌
//        resources.resourceId("article-resource");
    }

    /**
     * 资源服务器的安全配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()    //采用token进行管理身份，而没有采用session，所以不需要创建HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()    //请求的授权配置
                .antMatchers("/v2/api-docs", "/v2/feign-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**")
                .permitAll()    //将swagger接口文档相关的url放行
                .antMatchers("/api/**")     //放行以/api开头的接口
                .permitAll()
                .antMatchers("/**")     //对于其他请求都需要通过身份认证
                .access(("#oauth2.hasScope('all')"))    //所有请求都需要有all权限
                .anyRequest()
                .authenticated();
    }
}
