package com.yu.springsecurityjwt.security.handle;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author yu
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    /**
     * todo 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // 删除用户缓存记录
        // 记录用户退出日志

    }
}
