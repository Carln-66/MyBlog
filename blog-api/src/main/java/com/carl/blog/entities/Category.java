package com.carl.blog.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/9:48
 * @Description:
 */
@TableName("category")     //category实体类对应的表blog_category
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，分布式id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（1：正常；2：禁用）
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

}
