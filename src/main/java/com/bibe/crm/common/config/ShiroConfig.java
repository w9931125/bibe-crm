package com.bibe.crm.common.config;


import com.bibe.crm.dao.PermissionMapper;
import com.bibe.crm.dao.RolesMapper;
import com.bibe.crm.dao.RolesPermissionRelationMapper;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.service.PermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wang on 2019/8/26
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
     *
     * @return
     */

    @Resource
    private UserMapper userMapper;

    @Resource
    private RolesPermissionRelationMapper rolesPermissionRelationMapper;

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private PermissionService permissionService;

    @Resource
    private PermissionMapper permissionMapper;

    public ShiroConfig(UserMapper userMapper,RolesPermissionRelationMapper rolesPermissionRelationMapper,PermissionService permissionService,PermissionMapper permissionMapper){
        this.userMapper=userMapper;
        this.rolesPermissionRelationMapper=rolesPermissionRelationMapper;
        this.permissionService=permissionService;
        this.permissionMapper=permissionMapper;
    }



    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public ShiroFilterFactoryBean webFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>(16);
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(permissionService.loadFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(MyRealm());
        return securityManager;
    }

    
    @Bean
    public ShiroRealm MyRealm() {
        ShiroRealm myRealm = new ShiroRealm(userMapper,rolesPermissionRelationMapper,rolesMapper,permissionMapper);
        //注入加密密码
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    /**
     * 密码加密算法设置
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

/*    public static void main(String[] args) {
        String password= "djb12v3";
        Md5Hash md5Hash = new Md5Hash(password,null,2);
        String s = md5Hash.toString();
        System.out.println(s);
    }*/
}
