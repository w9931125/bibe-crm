package com.bibe.crm.utils;

import com.bibe.crm.common.config.ShiroRealm;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.entity.po.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;


public class ShiroUtils {
    /** 私有构造器 **/
    private ShiroUtils(){ }


    /**
     * 获取当前用户Session
     * @Return SysUserEntity 用户信息
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     * @Return SysUserEntity 用户信息
     */
    public static User getUserInfo() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static void  reloadAuthorizing(Object principal) throws Exception{
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm myShiroRealm = (ShiroRealm) rsm.getRealms().iterator().next();

        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, realmName);
        subject.runAs(principals);
        if(myShiroRealm.isAuthenticationCachingEnabled()) {
            myShiroRealm.getAuthenticationCache().remove(principals);
        }
        if(myShiroRealm.isAuthorizationCachingEnabled()) {
            // 删除指定用户shiro权限
            myShiroRealm.getAuthorizationCache().remove(principals);
        }
        // 刷新权限
        subject.releaseRunAs();
    }

//
//    /**
//     * 从缓存中获取指定用户名的Session
//     * @param username
//     */
//    private static Session getSessionByUsername(String username){
//        // 获取当前已登录的用户session列表
//        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
//        User user;
//        Object attribute;
//        // 遍历Session,找到该用户名称对应的Session
//        for(Session session : sessions){
//            attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//            if (attribute == null) {
//                continue;
//            }
//            user = (User) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
//            if (user == null) {
//                continue;
//            }
//            if (Objects.equals(user.getUsername(), username)) {
//                return session;
//            }
//        }
//        return null;
//    }

}

