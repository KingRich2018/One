package com.happing.one.config.shiro;

import com.happing.one.domain.SysUser;
import com.happing.one.service.SysUserService;
import com.happing.one.untils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;

    }
    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加资源的授权字符串
        //
        //到数据库查询当前登录用户的授权字符串
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser =(SysUser)subject.getPrincipal();//就是用户信息

        info.addStringPermission(sysUser.getPerms());
        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String cellphone = JwtUtil.getUserName(token);
        if (cellphone == null) {
            throw new AuthenticationException("token无效");
        }

        SysUser sysUser = sysUserService.getSysUser(cellphone);
        if (sysUser == null) {
            throw new AuthenticationException("用户不存在!");
        }

        if (!JwtUtil.verify(token, cellphone, sysUser.getPassword())) {
            throw new AuthenticationException("token无效");
        }

        return new SimpleAuthenticationInfo(sysUser,token,"my_realm");

    }
}
