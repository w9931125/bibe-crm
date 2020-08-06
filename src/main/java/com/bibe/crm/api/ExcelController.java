package com.bibe.crm.api;

import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.ImportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 程序入口
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {


    @Resource
    private ImportExcelUtil importExcelUtil;


    /**
     * 下载模版
     * @return
     */
    @GetMapping("/download")
    public RespVO download(HttpServletResponse resp) {
        return importExcelUtil.downloadExcel(resp);
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
