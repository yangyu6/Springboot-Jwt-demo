package com.yu.security_jwt;

import com.yu.security_jwt.demo.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityJwtApplicationTests {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        //System.out.println(jwtUtil.toString());
        System.out.printf(String.valueOf("订单号已存在".equals("\\u8ba2\\u5355\\u53f7\\u5df2\\u5b58\\u5728")) );
    }

}
