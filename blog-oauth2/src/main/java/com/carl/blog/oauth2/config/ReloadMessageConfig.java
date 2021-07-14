package com.carl.blog.oauth2.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @Auther: Carl
 * @Date: 2021/05/08/14:57
 * @Description:
 */
@Configuration
public class ReloadMessageConfig {
    /**
     * 加载中文的认证提示信息
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_zh_CN");   //不加后缀名.properties
        return messageSource;
    }
}
