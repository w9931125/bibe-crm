package com.bibe.crm.common.config;

import com.bibe.crm.dao.PermissionMapper;
import com.bibe.crm.dao.RolesMapper;
import com.bibe.crm.dao.RolesPermissionRelationMapper;
import com.bibe.crm.dao.UserMapper;
import com.bibe.crm.entity.po.Roles;
import com.bibe.crm.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

     @Autowired
     private UserMapper userMapper;

     @Autowired
     private RolesPermissionRelationMapper rolesPermissionRelationMapper;

     @Autowired
     private RolesMapper rolesMapper;

    @Resource
    private PermissionMapper permissionMapper;

     public ShiroRealm(UserMapper userMapper,RolesPermissionRelationMapper rolesPermissionRelationMapper,RolesMapper rolesMapper,PermissionMapper permissionMapper){
         this.userMapper=userMapper;
         this.rolesMapper=rolesMapper;
         this.rolesPermissionRelationMapper=rolesPermissionRelationMapper;
         this.permissionMapper=permissionMapper;
         this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
     }

     public ShiroRealm(){

     }

     @Override
     public String getName() {
        return "shiroRealm";
     }

    /**
     * 赋予角色和权限:用户进行权限验证时 Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        Integer roleId = user.getRoleId();
        //当前用户角色
        Roles roles = rolesMapper.selectByPrimaryKey(roleId);
        log.info("当前登录角色{}",roles.getName());
        String code = roles.getCode();
        authorizationInfo.addRole(code);
        Set<String> path= null;
        // 获取当前用户对应的权限(这里根据业务自行查询)
        if (code.equals("admin")){
            path = permissionMapper.selectPath();
        }else {
            path = rolesPermissionRelationMapper.getRolePrimary(roles.getId());
        }authorizationInfo.addStringPermissions(path);
//        // 这里可以进行授权和处理
//        Set<String> rolesSet = new HashSet<>();
//        // 获取当前用户对应的权限(这里根据业务自行查询)
//        String roleId = userMapper.selectByPrimaryKey(user.getId()).getRoleId().toString();
//        rolesSet.add(roleId);
//        Set<String> rolePrimary = rolesPermissionRelationMapper.getRolePrimary(roleId);
//        //将查到的权限和角色分别传入authorizationInfo中
//        authorizationInfo.setStringPermissions(rolePrimary);
//        authorizationInfo.setRoles(rolesSet);
        log.info("--------------- 赋予角色和权限成功！ ---------------");
        return authorizationInfo;
    }

    /**
     * 身份认证 - 之后走上面的 授权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken tokenInfo = (UsernamePasswordToken)authenticationToken;
        // 获取用户输入的账号
        String username = tokenInfo.getUsername();
//        // 获取用户输入的密码
//        String password = String.valueOf( tokenInfo.getPassword() );

        // 通过username从数据库中查找 User对象，如果找到进行验证
        // 实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        User user = userMapper.findAllByPhone(username);
        // 判断账号是否存在
        if (user == null) {
            //返回null -> shiro就会知道这是用户不存在的异常
            throw new UnknownAccountException("账号不存在");
        }

        // 判断账号是否被冻结
        if (user.getStatus()==null|| user.getStatus().equals(1)){
            throw new LockedAccountException();
        }
        /**
         * 进行验证 -> 注：shiro会自动验证密码
         * 参数1：principal -> 放对象就可以在页面任意地方拿到该对象里面的值
         * 参数2：hashedCredentials -> 密码
         * 参数3：realmName -> 自定义的Realm
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;
    }

}