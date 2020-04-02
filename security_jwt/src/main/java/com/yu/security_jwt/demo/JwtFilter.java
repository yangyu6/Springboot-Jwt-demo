package com.yu.security_jwt.demo;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * JWT过滤器，拦截 /secure的请求
 */
@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/main/excludefilter", "/user/login", "/logout", "/register")));

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        // 地址是否不需要拦截
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (!allowedPath) {
            response.setCharacterEncoding("UTF-8");
            //获取 header里的token
            final String token = request.getHeader("token");
            try {
                // 添加参数
                Map<String, Claim> userClaimMap = jwtUtil.verifyToken(token);

                requestWrapper.addParameter("name", userClaimMap.get("name").asString());
                requestWrapper.addParameter("userId", userClaimMap.get("id").asString());
            } catch (Exception e) {
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(JSON.toJSONString(Res.fail.data(e.getMessage())));
                return;
            }
        }

        chain.doFilter(requestWrapper, res);
    }

    @Override
    public void destroy() {
    }
}