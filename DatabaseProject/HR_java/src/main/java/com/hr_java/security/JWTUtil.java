package com.hr_java.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hr_java.Model.VO.UserJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间10分钟
    private static final long EXPIRE_TIME = 10*60*1000;
    // 过期时间15天
    private static final long REFRESH_EXPIRE_TIME = 15*60*60*1000;
    private static final String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7nPo0frwtUS405rlUgY0\n" +
            "lBbDk+31NywOMvE4JfX3dOibfVEIsabi8Gw43HyWSY5CVbbChz0wrNT2tae3/fSs\n" +
            "4Yc0fSp1P/M4dUPF97Dvwjx1b33viUqCe/2zp5PL5YgmNpoErGy4RHUXAOlARaam\n" +
            "UWrbSiSWk6UhEroRG3PwirqyAbzoJC3G1W82AL9cHj0B20seW6VQqCieWVTjiptT\n" +
            "WYSuyZVFRpMLD9kjFR21qEzz8DXdcBZly3FFUtngI/QDg+Tg4HmmfC8qpHoGYO5u\n" +
            "DpHG2rKgnhGKgXWQ+oHyVth6WocglK/Ty86q6e2l/uULh3Ou2WQoZKAjZgcgiaXO\n" +
            "0QIDAQAB";

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, UserJWT user, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", user.getName())
                    .withClaim("uid",user.getUid())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static Long getUID(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return 返回是否已过期，false为未过期，true为过期·
     */
    public static Boolean getExp(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("exp").asDate().compareTo(new Date())<=0;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 生成签名,5min后过期
     * @param user User JWT
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(UserJWT user, String secret) {
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", user.getName())
                .withClaim("uid",user.getUid())
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 生成签名,15day后过期
     * @param uid
     * @return 加密的token
     */
    public static String sign_refreshToken(Long uid) {
        Date date = new Date(System.currentTimeMillis()+REFRESH_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(key);
        // 附带username信息
        return JWT.create()
                .withClaim("uid",uid)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static String getKey(){
        return key;
    }
}