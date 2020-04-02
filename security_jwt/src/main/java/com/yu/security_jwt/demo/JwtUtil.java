package com.yu.security_jwt.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yu
 * @description:
 * @create: 2020-03-31 13:20
 **/
@Component
@ToString
@Slf4j
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 密钥
     */
    @Value("${jwt.config.secret}")
    private String secret;

    /**
     * 过期时间 半小时
     **/
    @Value("${jwt.config.ttl}")
    private long ttl;

    /**
     * 生成用户token,设置token超时时间
     */
    public String createToken(User user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + ttl * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                //可以将基本信息放到claims中
                .withClaim("id", user.getUserId())
                .withClaim("name", user.getName())
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //SECRET加密
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 校验token并解析token
     *
     * @return
     */
    public Map<String, Claim> verifyToken(String token) throws Exception {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (TokenExpiredException e) {
            log.error("token已过期");
            throw new Exception("token已过期");
        } catch (JWTVerificationException e) {
            log.error("token不存在或不正确");
            throw new Exception("token不存在或不正确");
        }
    }

}