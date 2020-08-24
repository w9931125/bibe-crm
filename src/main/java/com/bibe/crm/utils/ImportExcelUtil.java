package com.bibe.crm.utils;

import com.bibe.crm.entity.vo.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具
 */
@Component
@Slf4j
public class ImportExcelUtil {
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        int maxRowNum;
        list = new ArrayList<List<Object>>();
        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                maxRowNum = sheet.getRow(0).getLastCellNum();
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                // 遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = 0; y < maxRowNum; y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                /*
                 * for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) { cell = row.getCell(y); li.add(this.getCellValue(cell)); }
                 */
                if (li.size() > 0) {
                    list.add(li);
                }
            }
        }
        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
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
