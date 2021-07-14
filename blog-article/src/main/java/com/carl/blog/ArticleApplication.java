package com.carl.blog;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/10:43
 * @Description:
 */
@EnableFeignClients
@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient  //开启服务注册发现功能
public class ArticleApplication {
     public static void main(String[] args) {
           SpringApplication.run(ArticleApplication.class, args);
      }
}
