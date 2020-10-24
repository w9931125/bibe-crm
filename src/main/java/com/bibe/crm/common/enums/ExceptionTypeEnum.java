package com.bibe.crm.common.enums;

import lombok.Getter;


/**
 *
 */
@Getter
public enum ExceptionTypeEnum{
    /**
     * 操作成功！
     */
    SUCCESS(200, "操作成功！"),

    /**
     * 操作异常！
     */
    ERROR(500, "操作异常！"),

    /**
     * 退出成功！
     */
    LOGOUT(200, "退出成功！"),

    /**
     * 请先登录！
     */
    UNAUTHORIZED(401, "请先登录！"),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(403, "权限不足！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(404, "请求不存在！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD(405, "请求方式不支持！"),

    /**
     * 请求异常！
     */
    BAD_REQUEST(400, "请求异常！"),

    /**
     * 参数不匹配！
     */
    PARAM_NOT_MATCH(400, "参数不匹配！"),

    /**
     * 参数不能为空！
     */
    PARAM_NOT_NULL(400, "参数不能为空！"),


    /**
     * 用户名或密码错误！
     */
    USERNAME_PASSWORD_ERROR(5001, "用户名或密码错误！"),


    /**
     * 该账号已存在
     */
    USERNAME_ALREADY_EXISTS(6000, "该账号已存在"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(-1, "系统异常"),
    EXCEPTION(2, "网络异常"),
    ILLEGAL_OPERATION(3, "非法操作"),
    LOGIN_SUCCESS(4, "登录成功"),
    NOT_LOGIN(5, "请先登录"),
    USERNAME_NOT_EXISTS(8, "账号不存在"),
    USERNAME_LOCK(9, "账号已被禁用，请联系管理员"),

    PASSWORD_ERROR(10000, "两次密码不一致，请检查"),
    USER_CUSTOMER_ERROR(10001, "无法删除，请先转移该成员名下客户！"),
    USER_COUNT_ERROR(10002,"该员工已经存在"),
    DEPT_COUNT_ERROR(10003,"无法删除,请先删除子部门"),
    DEPT_USER_COUNT_ERROR(10004,"无法删除,请先将部门下成员移出部门"),
    CHECK_PASSWORD_ERROR(10005,"密码不正确"),
    ROLES_COUNT_ERROR(10006,"无法删除，该职务下存在员工"),
    SELECT_DEPT_BAN(10007,"所属职务没有查看权限,请联系管理员"),
    PERMISSION_SYSN_ERROR(10008,"客户资料未设定查看权限,无法同步客户资料权限"),
    USER_NUMBER_ERROR(10009,"录入失败，客户录入次数不足"),
    SELECT_CUSTOMER_ERROR(10010,"请选择查询范围"),
    PERMISSION_ROSE_ERROR(10011,"该职务权限已被禁用，请联系管理员"),
    DELETE_CUSTOMER_GROUP_ERROR(10012,"无法删除，该分组下存在部门"),
    DELETE_COMMENT_ERROR(10013,"无法删除其他人评论"),
    INSTALL_CONTACT_ERROR(10014,"该客户已经存在主要联系人"),
    EXCEL_ERROR(10015,"系统错误，请检查excel模板"),
    CUSTOMER_BY_NAME(10016,"该客户名称已经存在，请检查"),
    CUSTOMER_BY_PHONE(10017,"该客户联系人手机号已经存在，请检查"),
    USER_BY_PHONE(10018,"用户手机号已经注册，请检查"),
    ROLES_UPDATE_NAME_ERROR(10019,"该职位已经存在，请检查"),
    DEPT_NAME_ERROR(10020,"该部门已经存在，请检查")
    ;
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 返回信息
     */
    private final String message;

    ExceptionTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
