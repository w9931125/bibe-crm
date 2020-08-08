package com.bibe.crm.api;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.AreaService;
import com.bibe.crm.service.UserService;
import com.bibe.crm.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;



@RestController
public class IndexController {


    @Resource
    private UserService userService;

    @Resource
    private AreaService areaService;

    @PostMapping("/login")
    public RespVO login(User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        //获取ip
        String host = currentUser.getSession().getHost();
        SecurityUtils.getSubject().getSession().setTimeout(-1000L);
        try {
            currentUser.login(token);
        } catch (IncorrectCredentialsException ice) {
            return RespVO.ofStatus(ExceptionTypeEnum.USERNAME_PASSWORD_ERROR);
        } catch (UnknownAccountException uae) {
            return RespVO.ofStatus(ExceptionTypeEnum.USERNAME_NOT_EXISTS);
        }catch (LockedAccountException lock){
            return RespVO.ofStatus(ExceptionTypeEnum.USERNAME_LOCK);
        }
        //记录当前登录时间ip
        User vo=new User();
        vo.setLoginTime(new Date());
        vo.setHost(host);
        vo.setId(ShiroUtils.getUserInfo().getId());
        userService.updateBy(vo);
        return RespVO.ofSuccess(ShiroUtils.getUserInfo());
    }

    /**
     * 返回暂未登录信息
     *
     * @return
     */
    @GetMapping("/notLogin")
    public RespVO notLogin() {
        return RespVO.ofStatus(ExceptionTypeEnum.NOT_LOGIN);
    }


    /**
     * 地区
     * @return
     */
    @GetMapping("/areaList")
    public RespVO area(Integer pid) {
        return RespVO.ofSuccess(areaService.list(pid));
    }

    /**
     * 未授权功能
     * @return
     */
    @GetMapping("/403")
    public RespVO notUnauthorizedUrl() {
        return RespVO.ofStatus(ExceptionTypeEnum.ACCESS_DENIED);
    }

    /**
     * 退出登录成功
     *
     * @return
     */
    @GetMapping("/logout")
    public RespVO logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RespVO.ofSuccess();
    }

    @PostMapping("/upload")
    public RespVO upload(MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();
        String path="/file/";
        String a=path+filename;
        File sc=new File(a);
        file.transferTo(sc);
        return RespVO.ofSuccess();
    }
}
