package com.bibe.crm.api;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.entity.dto.*;
import com.bibe.crm.entity.po.Files;
import com.bibe.crm.entity.vo.*;
import com.bibe.crm.service.CustomerProgressService;
import com.bibe.crm.service.CustomerService;
import com.bibe.crm.utils.ExcelUtil;
import com.bibe.crm.utils.ImportExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 程序入口
 */
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {


    @Resource
    private ImportExcelUtil importExcelUtil;

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerProgressService customerProgressService;


    /**
     * 取消导入
     * @param version
     * @return
     */
    @PostMapping("/cancel")
    public RespVO cancel(String version){
        return importExcelUtil.cancel(version);
    }

    /**
     * 提交数据
     * @param version
     * @return
     */
    @PostMapping("/submit")
    public RespVO submit(String version){
        new Thread(new Runnable() {
            @Override
            public void run() {
                importExcelUtil.submit(version);
            }
        }).start();
        return RespVO.ofSuccess();
    }


    /**
     * 获取导入行数
     * @param file
     * @return
     */
    @PostMapping("/importSum")
    public RespVO importSum(@RequestParam("file") MultipartFile file) {
        List<ImportSumDTO> personVoList = null;
        try {
            personVoList = ExcelUtil.importExcel(file, ImportSumDTO.class);
        } catch (IOException e) {
            log.error("客户导入失败{}",e);
            return RespVO.fail(ExceptionTypeEnum.EXCEL_ERROR);
        }
        return RespVO.ofSuccess(personVoList.size());
    }


    /**
     * 导入客户
     * @param file
     * @param userId
     * @param groupId
     * @return
     */
    @PostMapping("/import")
    public RespVO importExcel(@RequestParam("file") MultipartFile file,Integer userId,Integer groupId){
        return importExcelUtil.importExcel(file,userId,groupId);
    }


    /**
     * 下载模版
     * @return
     */
    @GetMapping("/download")
    public void download(HttpServletResponse resp) throws Exception {
        importExcelUtil.downloadExcel(resp);
    }


    /**
     * 全部客户导出
     * @param dto
     * @param resp
     */
    @PostMapping("/export/customer")
    public void  customer (@RequestBody FindCustomerDTO dto,HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        IPage<CustomerVO> customerVOIPage = customerService.pageList(dto, dto.getPage());
        try {
            ExcelUtil.exportExcel(customerVOIPage.getRecords(), "全部客户表", "全部客户表", CustomerVO.class, "全部客户表", resp);
        } catch (IOException e) {
            log.error("全部客户导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }



    /**
     * 我的客户-导出
     * @param dto
     * */
    @PostMapping("/export/myCustomer")
    public void  myCustomer (@RequestBody FindCustomerDTO dto,HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        IPage<CustomerVO> customerVOIPage = customerService.myPageList(dto, dto.getPage());
        try {
            ExcelUtil.exportExcel(customerVOIPage.getRecords(), "我的客户", "我的客户", CustomerVO.class, "我的客户", resp);
        } catch (IOException e) {
            log.error("全部客户导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }


    /**
     * 公共客户-导出
     * @param dto
     * */
    @PostMapping("/export/publicCustomer")
    public void  publicCustomer (@RequestBody FindCustomerGroupDTO dto, HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        IPage<PublicCustomerVO> customerVOIPage = customerService.customerGroupPageList(dto, dto.getPage());
        try {
            ExcelUtil.exportExcel(customerVOIPage.getRecords(), "公共客户", "公共客户", PublicCustomerVO.class, "公共客户", resp);
        } catch (IOException e) {
            log.error("公共客户导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }


    /**
     * 联系跟进-导出
     * @param dto
     * */
    @PostMapping("/export/progress")
    public void  progress (@RequestBody ProgressDTO dto, HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        IPage<ProgressVO> customerVOIPage = customerProgressService.pageList(dto, dto.getPage());
        try {
            ExcelUtil.exportExcel(customerVOIPage.getRecords(), "联系跟进", "联系跟进", ProgressVO.class, "联系跟进", resp);
        } catch (IOException e) {
            log.error("公共客户导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 按日期-导出
     * @param countDTO
     * */
    @PostMapping("/export/countDay")
    public void  countDay (@RequestBody CountDTO countDTO, HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        List<CountDayVO> countByDate = customerService.getCountByDate(countDTO);
        String title = getTitle(countDTO);
        try {
            ExcelUtil.exportExcel(countByDate, title+"日期统计", title+"日期统计", CountDayVO.class, title+"日期统计", resp);
        } catch (IOException e) {
            log.error("日期统计导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 按分类-导出
     * @param countDTO
     * */
    @PostMapping("/export/countSort")
    public void  countSort (@RequestBody CountDTO countDTO, HttpServletResponse resp) {
        long start = System.currentTimeMillis();
        List<CountSortVO> countBySort = customerService.getCountBySort(countDTO);
        try {
            String title = getTitle(countDTO);
            ExcelUtil.exportExcel(countBySort, title+"分类统计", title+"分类统计", CountSortVO.class, title+"分类统计", resp);
        } catch (IOException e) {
            log.error("分类统计导出失败{}",e);
        }
        log.debug("导出excel所花时间：" + (System.currentTimeMillis() - start));
    }

    private String  getTitle(CountDTO countDTO){
        String title = null;
        switch (countDTO.getFlag()){
            case 1:
                title="新增客户数";
                break;
            case 2:
                title="联系跟进次数";
                break;
            case 3:
                title="跟进客户数";
                break;
        }
        return title;
    }

//    public HSSFWorkbook userExcel(List<UserRegisterSum> list) {
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        HSSFSheet sheet = wb.createSheet("用户注册统计"); //创建table工作薄
//
//        String excelHeader[] = {"日期", "累计注册用户数", "累计用户注销数", "留存注册用户数"};
//
//        //第0行表头
//        HSSFRow row = sheet.createRow(0);
//        for (int i = 0; i < excelHeader.length; i++) {
//            sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
//            HSSFCell cell = row.createCell(i);
//            cell.setCellValue(excelHeader[i]);
//            //cell.setCellStyle(style);
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            row = sheet.createRow(0 + 1 + i);
//            //日期
//            HSSFCell cell2 = row.createCell(0);
//            if (list.get(i).getCreateDate() != null) {
//                sheet.autoSizeColumn(i, true);
//                cell2.setCellValue(list.get(i).getCreateDate());
//            }
//            //累计注册用户
//            HSSFCell cell3 = row.createCell(1);
//            if (list.get(i).getRegisterNum() != null) {
//                sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
//                cell3.setCellValue(list.get(i).getRegisterNum().toString());
//            }
//            //累计用户注销数
//            HSSFCell cell4 = row.createCell(2);
//            if (list.get(i).getLogoutNum() != null) {
//                sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
//                cell4.setCellValue(list.get(i).getLogoutNum().toString());
//            }
//            //留存
//            HSSFCell cell5 = row.createCell(3);
//            if (list.get(i).getNowNum() != null) {
//                sheet.autoSizeColumn(i, true);// 根据字段长度自动调整列的宽度
//                cell5.setCellValue(list.get(i).getNowNum().toString());
//            }
//        }
////        FileOutputStream output=new FileOutputStream("/Users/xuefengwang/Downloads/user.xls");
////        wb.write(output);
////        output.flush();
//        return wb;
//    }

//    @PostMapping("/userExcel")
//    public void userExcel(@RequestBody HttpReqBodyBasic<ReportDTO> dto, HttpServletResponse response,HttpServletRequest request){
//        try {
//            HSSFWorkbook wb =null;
//            if (dto.getParams().getAgentId()!=null){
//                wb = reportService.findUserAgentExcel(dto.getParams());
//            }else if (dto.getParams().getBrandId()!=null) {
//                wb = reportService.findUserBrandExcel(dto.getParams());
//            }else if (dto.getParams().getBrandId()==null&&dto.getParams().getAgentId()==null){
//                wb = reportService.findUserExcel(dto.getParams());
//            }
//            response.setContentType("application/vnd.ms-excel");
//            response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("用户注册统计报表", "UTF-8")+".xls");
//            OutputStream outputStream = response.getOutputStream();
//            wb.write(outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception e) {
//            throw Exceptions.unchecked(e);
//        }
//    }*/
}
