package com.bibe.crm.common.base;


import com.bibe.crm.common.enums.ExceptionTypeEnum;
import lombok.Data;


@Data
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;
    private Object data;

    public BaseException(ExceptionTypeEnum exceptionTypeEnum) {
        super(exceptionTypeEnum.getMessage());
        this.code = exceptionTypeEnum.getCode();
        this.message = exceptionTypeEnum.getMessage();
    }

    public BaseException(ExceptionTypeEnum exceptionTypeEnum, Object data) {
        this(exceptionTypeEnum);
        this.data = data;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Object data) {
        this(code, message);
        this.data = data;
    }
}
