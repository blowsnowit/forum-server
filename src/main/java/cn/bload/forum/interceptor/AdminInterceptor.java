package cn.bload.forum.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.exception.UnLoginException;
import cn.bload.forum.service.RedisService;
import cn.bload.forum.service.UserService;
import cn.bload.forum.utils.TokenUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/6 13:55
 * @describe 类描述: 鉴权
 */

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        //判断是不是管理员访问
        //不是管理员访问直接拦截他
        Integer userId = getUserId(request);
        if (userId == null){
            throw new UnLoginException("未登录");
        }
        if (!userService.isOp(userId)){
            throw new MyRuntimeException("非管理员访问");
        }

        return true;
    }

    Integer getUserId(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)){
            return null;
        }
        return TokenUtil.getUserId(token);
    }

    boolean checkIsLogin(HttpServletRequest request){
        return getUserId(request) != null;
    }
}
