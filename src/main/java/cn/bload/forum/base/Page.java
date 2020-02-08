package cn.bload.forum.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 9:30
 * @describe 类描述:
 */
@ApiModel("分页对象")
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    @ApiModelProperty(hidden = true)
    private List<T> records;

    @ApiModelProperty(hidden = true)
    private long total;

    @ApiModelProperty(value = "分页数量",hidden = true)
    private long size;

    @ApiModelProperty(value = "当前页码",hidden = true)
    private long current;

    @ApiModelProperty(value = "排序",hidden = true)
    private List<OrderItem> orders;


}
