package com.carl.blog.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/9:48
 * @Description: 表对应的实体类
 */
@ApiModel(value = "category对象", description = "类别信息")
@TableName("category")     //category实体类对应的表blog_category
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，分布式id
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态（1：正常；2：禁用）
     */
    @ApiModelProperty(value = "状态（1：正常；2：禁用）")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "排创建时间序")
    private Date createDate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    //创建集合用于保存同一分类下的所有标签，在com/carl/blog/article/mapper/xml/CategoryMapper.xml用到
    @ApiModelProperty(value = "分类下的标签集合")
    @TableField(exist = false)
    private List<Label> labelList;

}
