package com.carl.blog.util.properties;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Auther: Carl
 * @Date: 2021/05/02/12:59
 * @Description:
 */
@Getter
@Setter
public class AliyunProperties implements Serializable {

    /**
     * oss端点信息
     */
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 存储空间名称
     */
    private String bucketName;

    /**
     * bucket名称，访问文件时作为基础URL
     */
    private String bucketDomain;
}
