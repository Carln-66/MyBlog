package com.carl.blog.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Auther: Carl
 * @Date: 2021/05/02/12:52
 * @Description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "carl.blog")
public class BlogProperties implements Serializable {
    //会将carl.blog.aliyun下的配置绑定到AliyunProperties对象属性上面
    private AliyunProperties aliyun;
}
