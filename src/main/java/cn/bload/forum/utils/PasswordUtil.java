package cn.bload.forum.utils;

import cn.hutool.crypto.SecureUtil;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/18 14:10
 * @describe 类描述:
 */
public class PasswordUtil {
    //密码盐
    private final static String  PASSWORD_SALT = "bl_formu_salt";

    public static String encodePassword(String password){
        return SecureUtil.md5(PASSWORD_SALT + password);
    }
}
