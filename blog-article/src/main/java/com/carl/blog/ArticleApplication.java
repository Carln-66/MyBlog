package com.carl.blog;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/10:43
 * @Description:
 */
@SpringBootApplication
@EnableSwagger2Doc
public class ArticleApplication {
     public static void main(String[] args) {
           SpringApplication.run(ArticleApplication.class, args);
      }
}
