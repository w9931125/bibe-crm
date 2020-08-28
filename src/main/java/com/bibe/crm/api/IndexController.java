package com.bibe.crm.api;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.FilesMapper;
import com.bibe.crm.entity.po.Files;
import com.bibe.crm.entity.po.User;
import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.service.AreaService;
import com.bibe.crm.service.UserService;
import com.bibe.crm.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Slf4j
public class IndexController {


    @Resource
    private UserService userService;

    @Resource
    private AreaService areaService;

    @Resource
    private FilesMapper filesMapper;

    private final String path = "/home/bibe-crm/upload";

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public RespVO upload(@RequestParam("file") MultipartFile[] file) throws Exception {
        Map<String, Object> map = new HashMap<>();
        for (MultipartFile multipartFile : file) {
            //文件大小
            long size = multipartFile.getSize();
            //老文件名字
            String originalFilename = multipartFile.getOriginalFilename();
            //文件类型
            String contentType = multipartFile.getContentType();
            //获取文件后缀名
            String[] split = contentType.split("/");
            String ext = "." + split[1];
            //获取下载路径url
            //  String s = ResourceUtils.getURL("classpath:").getPath() + "static/upload";
            String s = path;
            String url = URLDecoder.decode(s);
            Files files = new Files();
            //创建新文件名字
            String newFileName = UUID.randomUUID().toString().replace("-", "") + ext;
            files.setAlias(newFileName);
            files.setName(originalFilename);
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String catalogName = url + "/" + format;
            files.setPath("/" + format);
            File fileDir = new File(catalogName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //下载文件
            multipartFile.transferTo(new File(catalogName, newFileName));
            //保存到数据库
            int i = filesMapper.insertSelective(files);
            if (i > 0) {
                log.info("文件上传成功id为" + files.getId());
            } else {
                log.error("文件上传失败>>>>>>");
            }
            map.put("id", files.getId());
            map.put("name", files.getName());
        }
        return RespVO.ofSuccess(map);
    }

    public static void main(String[] args) {

        String sourceString = "哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈";	//待写入字符串
        byte[] sourceByte = sourceString.getBytes();
        if(null != sourceByte){
            try {
                File file = new File("/Users/xuefengwang/Documents/test.txt");		//文件路径（路径+文件名）
                if (!file.exists()) {	//文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();

                }
                FileOutputStream outStream = new FileOutputStream(file);	//文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();	//关闭文件输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 下载文件
     *
     * @param response
     * @param id
     */
    @PostMapping("/download")
    public void download(HttpServletResponse response, Integer id) {
        try {
            Files files = filesMapper.selectByPrimaryKey(id);
            // String Path = ResourceUtils.getURL("classpath:").getPath() + "/static" + files.getPath();
            String Path = path + files.getPath();
            FileInputStream in = new FileInputStream(new File(URLDecoder.decode(Path), files.getAlias()));
            response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode(files.getName(), "UTF-8"));
            ServletOutputStream os = response.getOutputStream();
            IOUtils.copy(in, os);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(os);
        } catch (Exception e) {
            log.error("文件下载失败");
        }
    }


    /**
     * 删除文件
     *
     * @param ids
     * @return
     */
    @PostMapping("/file/delete")
    public RespVO delete(@RequestParam("ids") Integer [] ids) {
        for (Integer id : ids) {
            Files files = filesMapper.selectByPrimaryKey(id);
            //    path = ResourceUtils.getURL("classpath:").getPath() + "static" + files.getPath() + "/" + files.getAlias();
            //
            //    String a=path+files.getAlias();
            log.info("删除文件地址>>>>>>>"+path+files.getPath() + "/"+files.getAlias());
/*            String decode = URLDecoder.decode(path+files.getPath() + "/"+files.getAlias());
            File file = new File(decode);
            boolean delete = file.delete();*/
            //if (delete) {
                int b = filesMapper.deleteByPrimaryKey(id);
                if (b > 0) {
                    log.info("文件删除成功");
                    return RespVO.ofSuccess();
                }
            //}
        }
        log.info("文件删除失败");
        return RespVO.fail();
    }


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
        } catch (LockedAccountException lock) {
            return RespVO.ofStatus(ExceptionTypeEnum.USERNAME_LOCK);
        }
        //记录当前登录时间ip
        User vo = new User();
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
     *
     * @return
     */
    @GetMapping("/areaList")
    public RespVO area(Integer pid) {
        return areaService.list(pid);
    }

    /**
     * 未授权功能
     *
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

}
