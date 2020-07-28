package com.bibe.crm.entity.vo;

import com.bibe.crm.common.enums.ExceptionTypeEnum;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * 接口统一返回实体类
 */
@Getter
@ToString
public class RespVO<T> {
    /**
     * 处理结果code
     */
    private Integer code;
    /**
     * 处理结果描述信息
     */
    private String message;
    /**
     * 处理结果数据信息
     */
    private T result;

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param message 返回内容
     * @param result    返回数据
     */
    public RespVO(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public RespVO(ExceptionTypeEnum type, T result) {
        this.code = type.getCode();
        this.message = type.getMessage();
        this.result = result;
    }

    public RespVO(ExceptionTypeEnum type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }

    public RespVO() {
    }

    /**
     * 构造一个成功且带数据的API返回
     *
     * @param data 返回数据
     * @return ResultVO
     */
    public static RespVO ofSuccess(Object data) {
        return ofStatus(ExceptionTypeEnum.SUCCESS, data);
    }

    public static RespVO ofSuccess() {
        return ofStatus(ExceptionTypeEnum.SUCCESS);
    }

    /**
     * 构造一个有状态且带数据的API返回
     *
     * @param exceptionTypeEnum 状态 {@link ExceptionTypeEnum}
     * @param data              返回数据
     * @return ResultVO
     */
    public static RespVO ofStatus(ExceptionTypeEnum exceptionTypeEnum, Object data) {
        return of(exceptionTypeEnum.getCode(), exceptionTypeEnum.getMessage(), data);
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     * @return ApiResponse
     */
    public static RespVO of(Integer code, String message, Object data) {
        return new RespVO(code, message, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param exceptionTypeEnum
     * @return Result
     */
    public static RespVO fail(ExceptionTypeEnum exceptionTypeEnum) {
        return RespVO.fail(exceptionTypeEnum, null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static RespVO fail() {
        return new RespVO(ExceptionTypeEnum.SYSTEM_ERROR);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param exceptionTypeEnum
     * @param result
     * @return Result
     */
    public static RespVO fail(ExceptionTypeEnum exceptionTypeEnum, Object result) {
        return new RespVO<>(exceptionTypeEnum, result);
    }

    /**
     * 构造一个有状态的API返回
     *
     * @param exceptionTypeEnum 状态 {@link ExceptionTypeEnum}
     * @return Result
     */
    public static RespVO ofStatus(ExceptionTypeEnum exceptionTypeEnum) {
        return ofStatus(exceptionTypeEnum, null);
    }
}
