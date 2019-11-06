package com.happing.one.config;

import com.happing.one.config.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 添加Shiro内置过滤器
        /**
         * shiro内置过滤器：可以实现权限相关的拦截器
         * 常用的过滤器：
         *   anon：无需认证（登录）可以访问
         *   authc：必须认证才可以访问
         *   user：如果使用rememberMe的功能可以直接访问
         *   perms：必须授权才可以访问
         *   role：该资源得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/logout","logout");
        filterMap.put("/login","anon");
        //授权过滤器 当前授权拦截后，shiro会自动跳转到未授权的页面
        // 修改调整的登录页面，默认是login.jsp
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        filterMap.put("/api/auth","authc");
        // filterMap.put("/api/**","perms[uaa:admin]");
        // filterMap.put("/api/**","authc");

        filterMap.put("/api/**","anon");

        Map<String, Filter> filterJwt = new HashMap<>();
        //设置我们自定义的JWT过滤器
        // filterJwt.put("jwt", new JwtFilter());
        // shiroFilterFactoryBean.setFilters(filterJwt);
        // 所有请求通过我们自己的JWT Filter
        // filterMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /**
     * 创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
