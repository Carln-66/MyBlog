package com.carl.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: Carl
 * @Date: 2021/05/12/10:50
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {
     public static void main(String[] args) {
           SpringApplication.run(GatewayServerApplication.class, args);
      }
}
