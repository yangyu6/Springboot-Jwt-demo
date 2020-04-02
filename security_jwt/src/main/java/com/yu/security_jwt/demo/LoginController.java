package com.yu.security_jwt.demo;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author: yu
 * @description:
 * @create: 2020-04-01 14:01
 **/
@RestController
@RequestMapping("/user")
public class LoginController {
    String name = "admin";
    String password = "1234";
    String userId = "1024";

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("login")
    public Res login(User user) {
        String token = "";
        // 例子没有用数据库，直接使用写死的用户
        // 判断用户密码是否正确
        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
            token = jwtUtil.createToken(user);
        } else {
            return Res.fail.data("用户名或密码不正确");
        }

        return Res.ok.data(token);
    }

    /**
     * 此方法必须登录才能请求，测试token 拦截器用这个
     *
     * @return
     */
    @GetMapping("getUserByInterceptor")
    public Res getUserByInterceptor(HttpServletRequest request) {
        String name = (String) request.getAttribute("name");
        String userId = (String) request.getAttribute("userId");
        User user = new User();
        user.setName(name);
        user.setUserId(userId);
        return Res.ok.data(user);
    }

    /**
     * 此方法必须登录才能请求，测试token  过滤器的
     *
     * @return
     */
    @GetMapping("getUserByFilter")
    public Res getUserByFilter(String name, String userId) {
        User user = new User();
        user.setName(name);
        user.setUserId(userId);
        return Res.ok.data(user);
    }
}
