package com.yu.springsecurityjwt.security.service;

import com.yu.springsecurityjwt.system.SysUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author yu
 */
@Component
public class SysPermissionService {


    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            /**
             * todo 如果不是管理员，查询db获取用户所有角色，这里手动创建提供
             */

            Set<String> perms = new HashSet<>();
            roles.addAll(perms);
        }
        return roles;
    }

    /**
     * 获取用户的数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getDataPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            /**
             * todo 如果不是管理员，查询db获取用户所有权限，这里手动创建提供
             */

            Set<String> userPerms = new HashSet<>();
            perms.addAll(userPerms);
        }
        return perms;
    }
}
