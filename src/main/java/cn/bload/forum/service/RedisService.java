package cn.bload.forum.service;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/9 15:54
 * @describe 类描述:
 */
public interface RedisService {
    /**
     * 缓存邮箱验证码（会验证发送时间）
     * @param email 邮箱
     * @param code 验证码
     */
    void cacheEmailCode(String email, String code);

    /**
     * 获取缓存邮箱验证码
     * @param email 邮箱
     * @return code 验证码
     */
    Object getEmailCode(String email);


    /**
     * 缓存图形验证码
     * @param token 图形验证码token
     * @param code 验证码
     */
    void cacheCaptch(String token, String code);

    /**
     * 获取图形验证码code
     * @param token 图形验证码token
     * @return code 验证码
     */
    Object getCaptch(String token);

    /**
     * 删除图形验证码
     * @param token
     */
    void delCaptch(String token);

    /**
     * 缓存的用户在线状态
     * @param userId 用户id
     */
    void cacheUserOnline(Integer userId);
    /**
     * 删除缓存的用户在线状态
     * @param userId 用户id
     */
    void delCacheUserOnline(Integer userId);

    /**
     * 获取用户在线状态
     * @param userId 用户id
     * @return
     */
    Boolean getUserOnline(Integer userId);


}
