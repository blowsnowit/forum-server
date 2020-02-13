package cn.bload.forum.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 9:30
 * @describe 类描述:
 */
@ApiModel("分页对象")
public class Page<T> implements IPage<T> {
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

    @ApiModelProperty(value = "是否count",hidden = true)
    private boolean optimizeCountSql;

    @ApiModelProperty(value = "是否搜索count",hidden = true)
    private boolean isSearchCount;

    @ApiModelProperty(value = "其他参数",hidden = true)
    @Getter
    @Setter
    private Object others;

    public Page() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.isSearchCount = true;
    }

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    public Page<T> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public Page<T> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    @Override
    public List<T> getRecords() {
        return records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }


    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }
    @Override
    public boolean isSearchCount() {
        return this.total < 0L ? false : this.isSearchCount;
    }
}
