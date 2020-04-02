package com.yu.security_jwt.demo;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: yu
 * @description:
 * @create: 2020-04-01 16:07
 **/
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 调用前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("token");
            Map<String, Claim> userClaimMap = jwtUtil.verifyToken(token);
            request.setAttribute("name",userClaimMap.get("name").asString());
            request.setAttribute("userId",userClaimMap.get("id").asString());
            return true;
        }catch (Exception e ){
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(JSON.toJSONString(Res.fail.data(e.getMessage())));
        }

        return false;
    }
}
