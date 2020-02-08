package cn.bload.forum.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 9:07
 * @describe 类描述:
 */
@Data
@ApiModel("基础搜索对象")
public class BaseQuery {
    @ApiModelProperty("分页数量")
    private Long pageSize;

    @ApiModelProperty("当前页数")
    private Long pageCurrent;

    @ApiModelProperty("分页排序 字段 asc")
    private String pageOrders;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Page page = new Page();

    @JsonIgnore
    public Page createPage(){
        if (pageCurrent == null) pageCurrent = 1L;
        if (pageSize == null) pageSize = 10L;

        if (pageOrders != null){
            List<OrderItem> orderItems = new ArrayList<>();
            String[] orders = pageOrders.split(",");
            for (String order : orders) {
                String[] strs = order.split(" ");
                if (strs.length == 2){
                    String column = strs[0];
                    String orderBy = strs[1];
                    OrderItem orderItem = new OrderItem();
                    orderItem.setColumn(column);
                    if ("asc".equals(orderBy.toLowerCase())){
                        orderItem.setAsc(true);
                    }else{
                        orderItem.setAsc(false);
                    }
                    page.addOrder(orderItem);
                }
            }
        }
        page.setCurrent(pageCurrent);
        page.setSize(pageSize);
        return page;
    }

    public QueryWrapper toQueryWrapper(){
        return toQueryWrapper(false);
    }

    public QueryWrapper toQueryWrapper(boolean null2IsNull){
        Map<String, Object> map = BeanUtil.beanToMap(this);
        map.remove("page");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(map,null2IsNull);
        System.out.println(queryWrapper.getSqlSegment());
        return queryWrapper;
    }
}
