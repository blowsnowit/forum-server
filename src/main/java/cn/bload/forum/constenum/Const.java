package cn.bload.forum.constenum;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/9 12:26
 * @describe 类描述:
 */
public interface Const {

    /**
     * 缓存邮箱验证码
     */
    String REDIS_EMAIL_CODE = "cache_email_code_";
    /**
     * 记录邮箱验证码发送时间
     */
    String REDIS_EMAIL_CODE_TIME = "cache_email_code_time_";

    /**
     * 邮箱验证码过期时间 5分钟内有效
     */
    Long REDIS_EMAIL_CODE_EXPIRE = 5 * 60L;

    /**
     * 邮箱验证码发送限制时间 1分钟
     */
    Long REDIS_EMAIL_CODE_EXPIRE_LIMIT = 60L;


    /**
     * 缓存图形验证码key前缀
     */
    String REDIS_CAPTCH = "cache_captch_";

    /**
     * 缓存图片验证码过期时间
     */
    Long REDIS_CAPTCH_EXPIRE = 5 * 60L;


    /**
     * 缓存用户在线状态
     */
    String REDIS_USER_ONLINE = "cache_user_online_";

    /**
     * 缓存用户在线状态过期时间
     * 5分钟没有请求接口 意味掉线
     */
    Long REDIS_USER_ONLINE_EXPIRE = 5 * 60L;
}
