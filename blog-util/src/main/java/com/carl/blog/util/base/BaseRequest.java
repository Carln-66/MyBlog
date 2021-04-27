package com.carl.blog.util.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: Carl
 * @Date: 2021/04/27/8:55
 * @Description: 实现分页功能
 */
@Data
@Accessors(chain = true)
public class BaseRequest<T> implements Serializable {
    @ApiModelProperty(value = "页码", required = true)     //用于生成swagger文档
    private long current;   //当前页码
    @ApiModelProperty(value = "每页显示数量", required = true)
    private long size;      //当前页显示数据数量

    /**
     * @return 封装分页对象
     */
    @ApiModelProperty(hidden = true)    //hidden=true：表示不在swagger接口文档中显示
    public IPage<T> getPage() {
        return new Page<T>().setCurrent(this.current).setSize(this.size);
    }
}
