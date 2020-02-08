package cn.bload.forum.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import cn.bload.forum.exception.UnLoginException;
import cn.bload.forum.model.User;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.TokenUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/18 11:24
 * @describe 类描述:
 */

public class BaseController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;

    protected Integer getUserId(){
        String token = request.getHeader("Authorization");
        if (token == null || token.equals("")){
            return null;
        }
        Integer userId = TokenUtil.getUserId(token);
        if (userId == null){
            return null;
        }
        return userId;
    }

    protected Integer getUserIdCheck(){
        Integer userId = getUserId();
        if (userId == null){
            throw new UnLoginException();
        }
        return userId;
    }

    protected User getUser(){
        Integer userId = getUserId();
        return userService.getById(userId);
    }


    protected <T> Page<T> getPage(){
        Page<T> page = new Page<>();
        String size = request.getParameter("size");
        String current = request.getParameter("current");
        String orderBys = request.getParameter("orderBys");
        if (size != null){
            page.setSize(Long.parseLong(size));
        }
        if (current != null){
            page.setCurrent(Long.parseLong(current));
        }
        if (orderBys != null){
            String[] split = orderBys.split(",");
            for (String orderBy : split) {
                String[] s = orderBy.split(" ");
                if (s.length == 2){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setColumn(s[0]);
                    if ("asc".equals(s[1])){
                        orderItem.setAsc(true);
                    }
                    page.addOrder(orderItem);
                }

            }
        }
        return page;
    }

}
