package com.yu.springsecurityjwt.security.service;

import com.yu.springsecurityjwt.security.LoginUser;
import com.yu.springsecurityjwt.system.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author yu
 */
@Component
public class SysLoginService {

    @Autowired
    JwtUtil jwtUtil;
    @Resource
    private AuthenticationManager authenticationManager;
    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password) {

        // 用户验证
        Authentication authentication = null;
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            e.printStackTrace();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return jwtUtil.createToken(loginUser);
    }
}
