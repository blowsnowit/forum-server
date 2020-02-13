package cn.bload.forum.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bload.forum.annotation.NeedLogin;
import cn.bload.forum.exception.UnLoginException;
import cn.bload.forum.service.RedisService;
import cn.bload.forum.utils.TokenUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/6 13:55
 * @describe 类描述: 鉴权
 */

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            NeedLogin annotation = method.getAnnotation(NeedLogin.class);
            if (annotation != null){
                //检查是否登录了
                if (!checkIsLogin(request)){
                    throw new UnLoginException();
                }
            }

            //更新用户在线状态
            if (checkIsLogin(request)){
                Integer userId = getUserId(request);
                redisService.cacheUserOnline(userId);
            }
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
