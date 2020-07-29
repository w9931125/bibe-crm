package com.bibe.crm.api;

import com.bibe.crm.entity.vo.RespVO;
import com.bibe.crm.utils.ImportExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
}
