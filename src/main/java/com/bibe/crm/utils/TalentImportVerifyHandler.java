package com.bibe.crm.utils;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.bibe.crm.dao.CustomerMapper;
import com.bibe.crm.dao.TransferMapper;
import com.bibe.crm.entity.dto.ImportDTO;
import com.bibe.crm.entity.vo.VerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;

@Component
@Slf4j
public class TalentImportVerifyHandler implements IExcelVerifyHandler<ImportDTO> {

    @Resource
    private TransferMapper transferMapper;

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ImportDTO inputEntity) {
        StringJoiner joiner = new StringJoiner(",");
        // 根据姓名与手机号判断数据是否重复
        String name = inputEntity.getName();
        String phone = inputEntity.getPhone();
        boolean b = checkForDuplicates(name, phone);
        if (b) {
            joiner.add("[客户名称]或[联系人手机号]重复，请检查后再录入");
        }
        if (joiner.length() != 0) {
            return new ExcelVerifyHandlerResult(false, joiner.toString());
        }
        return new ExcelVerifyHandlerResult(true);
    }

    /**
     * 校验是否重复
     */
    public boolean checkForDuplicates(String name, String phone) {
        List<Integer> list = transferMapper.count(name,phone);
        //个数大于1则为重复
        if (list.size()>1){
            return true;
        }else{
            return false;
        }
        //return list.stream().anyMatch(e -> e.getName().equals(name) && e.getPhone().equals(phone));
    }
}
