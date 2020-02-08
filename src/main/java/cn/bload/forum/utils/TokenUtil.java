package cn.bload.forum.utils;

import java.util.Date;

import cn.bload.forum.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/18 14:51
 * @describe 类描述:
 */
public class TokenUtil {
    private static final String SLAT = "blowsnowforum";
    private static final int EXPIRE_TIME =  30 * 12 * 60 * 1000;

    public static String getToken(User user) {
        // 将 user id 保存到 token 里面
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SLAT)
                .setId(user.getUserId().toString())
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();
        return token;
    }

    public static Integer getUserId(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SLAT).parseClaimsJws(token).getBody();
            return Integer.valueOf(claims.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
