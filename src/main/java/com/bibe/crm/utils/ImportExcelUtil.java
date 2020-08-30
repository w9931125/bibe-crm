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
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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

    public  RespVO cancel(String version){
        transferMapper.deleteByVersion(version);
        return RespVO.ofSuccess();
    }

    public void submit(String version){
        List<Transfer> transfers = transferMapper.findAllByVersion(version);
        Customer customer=new Customer();
        CustomerContact customerContact=new CustomerContact();
        transfers.forEach(i->{
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
        });
        log.info("导入数据同步完成。。。。。。。。。");
        //transferMapper.deleteByVersion(version);
    }

    public RespVO importExcel(MultipartFile file,Integer userId,Integer groupId){
        boolean flag=true;
        StringBuffer sourceString=new StringBuffer();
        ImportParams params = new ImportParams();
        // 表头设置为1行
        params.setHeadRows(1);
        // 标题行设置为0行，默认是0，可以不设置
        params.setTitleRows(0);
        // 开启Excel校验
        params.setNeedVerfiy(true);
        ExcelImportResult<ImportDTO> result = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(),
                    ImportDTO.class, params);
        } catch (Exception e) {
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
        //正确处理
        Transfer transfer=new Transfer();
        List<Transfer> data=new ArrayList<>();
        list.forEach(i->{
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
    public void downloadExcel(HttpServletResponse resp) {
        String fileName = null;
        String fileNames = "企业客户模板.xls";
        String downloadName = fileNames; //下载之后的名字
        try {
            fileName = new String(downloadName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Resource res = new ClassPathResource("excel/" + fileNames);

        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        //resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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
        log.info("--------Resource:{},fileName:{},OutputStream:{},BufferedInputStream:{}", res, fileName, os.toString(), bis.toString());
    }

}
