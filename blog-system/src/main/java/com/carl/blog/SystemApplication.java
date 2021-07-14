package com.carl.blog;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: Carl
 * @Date: 2021/05/05/13:48
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2Doc
@EnableDiscoveryClient
public class SystemApplication {
     public static void main(String[] args) {
           SpringApplication.run(SystemApplication.class, args);
      }
}
