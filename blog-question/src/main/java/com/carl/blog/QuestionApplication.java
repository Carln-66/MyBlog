package com.carl.blog;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: Carl
 * @Date: 2021/05/02/16:04
 * @Description:
 */
@EnableFeignClients
@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient  //开启nacos服务注册与发现功能
public class QuestionApplication {
     public static void main(String[] args) {
           SpringApplication.run(QuestionApplication.class, args);
      }
}
