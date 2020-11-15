package com.bibe.crm.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.dao.*;
import com.bibe.crm.entity.dto.ImportDTO;
import com.bibe.crm.entity.po.*;
import com.bibe.crm.entity.vo.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * excel工具
 */
@Component
@Slf4j
public class ImportExcelUtil {
    @javax.annotation.Resource
    private AreaMapper areaMapper;
    @javax.annotation.Resource
    private TransferMapper transferMapper;
    @javax.annotation.Resource
    private CustomerMapper customerMapper;
    @javax.annotation.Resource
    private CustomerContactMapper customerContactMapper;
    @javax.annotation.Resource
    private FilesMapper filesMapper;
    @javax.annotation.Resource
    private UserMapper userMapper;
    @javax.annotation.Resource
    private TalentImportVerifyHandler talentImportVerifyHandler;

    public  RespVO cancel(String version){
        transferMapper.deleteByVersion(version);
        return RespVO.ofSuccess();
    }

    public  void submit(String version){
        List<Transfer> data = transferMapper.findAllByVersion(version);
        //过滤
        ArrayList<Transfer> transfers = removeDuplicate(data);
        Customer customer=new Customer();
        CustomerContact customerContact=new CustomerContact();
        for (Transfer i : transfers) {
            boolean b = checkForDuplicates(i.getName(), i.getPhone());
            if (b){
                continue;
            }
            //客户
            customer.setName(i.getName()!=null?i.getName():null);
            customer.setIndustry(i.getIndustry()!=null?i.getIndustry():null);
            customer.setUserId(i.getUserId()!=null?i.getUserId():null);
            customer.setGroupId(i.getGroupId()!=null?i.getGroupId():null);
            customer.setAreaId(i.getAreaId()!=null?i.getAreaId():null);
            customer.setIntention((i.getIntention()!=null? Integer.valueOf(i.getIntention()):null));
            customer.setType(i.getType()!=null? Integer.valueOf(i.getType()):null);
            customer.setCustomerType(i.getCustomerType());
            customer.setAddress(i.getAddress());
            customer.setCreateTime(new Date());
            customerMapper.insertSelective(customer);
            //联系人
            customerContact.setPosition(i.getPosition()!=null?i.getIndustry():null);
            customerContact.setPhone(i.getPhone());
            customerContact.setName(i.getContactName());
            customerContact.setQq(i.getQq()!=null?i.getQq():null);
            customerContact.setLandline(i.getLandline()!=null?i.getLandline():null);
            customerContact.setEmail(i.getEmail()!=null?i.getEmail():null);
            customerContact.setType(Integer.valueOf(1).toString());
            customerContact.setCustomerId(customer.getId());
            customerContact.setCreateTime(new Date());
            customerContactMapper.insertSelective(customerContact);
        }
        log.info("导入数据同步完成。。。。。。。。。");
        int i = transferMapper.deleteByVersion(version);
        System.out.println("本次删除多少条"+i);
    }


    private boolean checkForDuplicates(String name, String phone) {
        List<Integer> list = transferMapper.count(name,phone);
        //个数大于1则为重复
        if (list.size()>1){
            return true;
        }else{
            return false;
        }
        //return list.stream().anyMatch(e -> e.getName().equals(name) && e.getPhone().equals(phone));
    }

    private ArrayList<Transfer> removeDuplicate(List<Transfer> fileVOS) {
        Set<Transfer> set = new TreeSet<Transfer>(new Comparator<Transfer>() {
            @Override
            public int compare(Transfer o1, Transfer o2) {
                //字符串,则按照asicc码升序排列
                return o1.getName().compareTo(o2.getName());
            }
        });
        set.addAll(fileVOS);
        set.forEach(i->{
            //去空
            i.setName(i.getName().replaceAll(" ",""));
            i.setPhone(i.getPhone().replaceAll("-",""));
        });
        return new ArrayList<Transfer>(set);
    }

    /**
     * 验证用户录入数量
     * @param userId
     * @return
     */
    private  boolean checkUserCustomer(Integer userId,Integer sum){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getNumber().equals(-1)){
            return true;
        }
        //用户客户数量
        Integer count = customerMapper.countByUserId(userId);
        if (user.getNumber()-count-sum>-1){
            return true;
        }else {
            return false;
        }
    }

    public RespVO importExcel(MultipartFile file,Integer userId,Integer groupId,Integer sum){
        if (userId!=null){
            //验证录入数量
            if (!checkUserCustomer(userId,sum)) {
                return RespVO.fail(ExceptionTypeEnum.USER_NUMBER_ERROR);
            }
        }
        boolean flag=true;
        StringBuffer sourceString=new StringBuffer();
        ImportParams params = new ImportParams();
        // 表头设置为1行
        params.setHeadRows(1);
        // 标题行设置为0行，默认是0，可以不设置
        params.setTitleRows(0);
        // 开启Excel校验
        params.setNeedVerify(true);
        params.setVerifyHandler(talentImportVerifyHandler);
        ExcelImportResult<ImportDTO> result = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(), ImportDTO.class, params);
        } catch (Exception e) {
            log.error("出现问题了"+e);
            return RespVO.fail(ExceptionTypeEnum.EXCEL_ERROR);
        }
        List<ImportDTO> list = result.getList();
/*      System.out.println("是否校验失败: " + result.isVerfiyFail());
        System.out.println("校验失败的集合:" + JSONObject.toJSONString(result.getFailList()));
        System.out.println("校验通过的集合:" + JSONObject.toJSONString(list));*/
        for (ImportDTO entity : result.getFailList()) {
            String msg = "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg()+System.getProperty("line.separator");
            sourceString.append(msg);
            System.out.println(msg);
            flag=false;
        }
        Map map=new HashMap();
        byte[] sourceByte = sourceString.toString().getBytes();
        //导入出错处理
        String uuid = UUID.randomUUID().toString();
        if(!flag) {
            try {
                String path="/home/bibe-crm/upload/logs";
                String fileName="log.txt";
                String aliasName= uuid+".txt";
                File ipuntFile = new File(path+"/"+aliasName);        //文件路径（路径+文件名）
                if (!ipuntFile.exists()) {    //文件不存在则创建文件，先创建目录
                    File dir = new File(ipuntFile.getParent());
                    dir.mkdirs();
                    ipuntFile.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(ipuntFile);    //文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();    //关闭文件输出流
                //记录文件
                Files files=new Files();
                files.setPath("/logs");
                files.setAlias(aliasName);
                files.setName(fileName);
                files.setCreateTime(new Date());
                filesMapper.insertSelective(files);
                map.put("fileId",files.getId());
                map.put("fileName",fileName);
                map.put("flag", flag);
                map.put("version",null);
                return RespVO.ofSuccess(map);
            } catch (IOException e) {
                log.error("excel日志文件出错"+e);
            }
        }
        List<Transfer> data=new ArrayList<>();
        list.forEach(i->{
            //正确处理
            Transfer transfer=new Transfer();
            String areaName = i.getAreaName();
            //excel中转数据
            BeanUtils.copyProperties(i,transfer);
            //获取地区id
            transfer.setAreaId(areaMapper.findIdByName(areaName));
            transfer.setUserId(userId);
            transfer.setGroupId(groupId);
            transfer.setVersion(uuid);
            transfer.setCustomerType(userId);
            if (userId!=null){
                transfer.setCustomerType(0);
            }else {
                transfer.setCustomerType(1);
            }
            data.add(transfer);
        });
        map.put("fileId",null);
        map.put("fileName",null);
        map.put("flag", flag);
        map.put("version",uuid);
        transferMapper.insertList(data);
        return RespVO.ofSuccess(map);
    }


    /**
     * 下载模板
     * @param resp
     * @return
     */
    public void downloadExcel(HttpServletResponse resp, HttpServletRequest request) throws Exception {
        String fileName = "企业客户模板.xls";
        Resource res = new ClassPathResource("excel/" + fileName);
        resp.reset();
        String userAgent =request.getHeader("USER-AGENT");
        if(userAgent.indexOf("MSIE")!=-1||userAgent.indexOf("Edge")!=-1||userAgent.indexOf("Trident")!=-1){
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            resp.setHeader("Content-Disposition","attachment;filename="+fileName);
        }else if(userAgent.indexOf("Firefox")!=-1){
            fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            resp.setHeader("Content-Disposition","attachment;filename="+fileName);
        }else{
            fileName = URLEncoder.encode(fileName, "UTF-8");
            resp.setHeader("Content-Disposition","attachment;filename="+fileName);
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(res.getInputStream());
            int i = 0;
            os.flush();
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert os != null;
                os.close();
                assert bis != null;
                bis.close();
            } catch (IOException e) {
                log.info("Exception:{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
