package com.bibe.crm.common;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import com.bibe.crm.entity.vo.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常统一处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public RespVO handlerException(UnauthorizedException e) {
        return RespVO.ofSuccess(ExceptionTypeEnum.ACCESS_DENIED);
    }
}
