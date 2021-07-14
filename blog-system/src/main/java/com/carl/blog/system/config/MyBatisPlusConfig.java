package com.carl.blog.system.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/11:51
 * @Description: MyBatis Plus配置类
 */
@EnableTransactionManagement    //开启事务管理
@MapperScan("com.carl.blog.system.mapper")     //扫描mapper接口
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
