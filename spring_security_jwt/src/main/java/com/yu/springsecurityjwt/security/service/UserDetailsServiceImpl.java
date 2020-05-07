package com.yu.springsecurityjwt.security.service;

import com.yu.springsecurityjwt.security.LoginUser;
import com.yu.springsecurityjwt.system.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author yu
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * todo 验证用户名 ，查询db用户信息和权限,此处手动新建一个
         */
        SysUser user = new SysUser();
        user.setUserName("大米饭");
        user.setPassword("111333");
        user.setUserId(1L); //1为管理员id
        return createLoginUser(user);
    }

    /**
     * 根据系统用户查询权限，新建一个userdetails
     *
     * @param user
     * @return
     */

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getRolePermission(user));
    }
}
