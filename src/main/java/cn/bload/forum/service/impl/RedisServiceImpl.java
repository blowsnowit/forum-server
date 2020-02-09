package cn.bload.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bload.forum.constenum.Const;
import cn.bload.forum.exception.MyRuntimeException;
import cn.bload.forum.service.RedisService;
import cn.bload.forum.utils.RedisOperator;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/9 15:54
 * @describe 类描述:
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    protected RedisOperator redisOperator;


    @Override
    public void cacheEmailCode(String email, String code) {
        //同时注意 邮箱验证码发送间隔时间
        Object lastSendTime = redisOperator.get(Const.REDIS_EMAIL_CODE_TIME + email);
        if (lastSendTime != null){
            throw new MyRuntimeException("邮箱验证码发送过快");
        }
        redisOperator.set(Const.REDIS_EMAIL_CODE + email,code, Const.REDIS_EMAIL_CODE_EXPIRE);
        redisOperator.set(Const.REDIS_EMAIL_CODE_TIME + email,System.currentTimeMillis(), Const.REDIS_EMAIL_CODE_EXPIRE_LIMIT);
    }

    @Override
    public Object getEmailCode(String email) {
        return redisOperator.get(Const.REDIS_EMAIL_CODE + email);
    }

    @Override
    public void cacheCaptch(String token, String code) {
        redisOperator.set(Const.REDIS_CAPTCH + token,code,Const.REDIS_CAPTCH_EXPIRE);
    }

    @Override
    public Object getCaptch(String token) {
        return redisOperator.get(Const.REDIS_CAPTCH + token);
    }

}
