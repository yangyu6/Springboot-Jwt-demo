package com.yu.springsecurityjwt.system;

import com.yu.springsecurityjwt.security.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: yu
 * @description:
 * @create: 2020-04-01 14:01
 **/
@RestController
public class LoginController {
    String name = "admin";
    String password = "1234";
    String userId = "1024";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    SysLoginService sysLoginService;

    @PostMapping("login")
    public String login(String name, String pass) {
        String token = sysLoginService.login(name, pass);
        return "登录成功,token=" + token;
    }

    @PreAuthorize("false")
    @PostMapping("test1")
    public String test1() {
        return "访问成功";
    }

}
