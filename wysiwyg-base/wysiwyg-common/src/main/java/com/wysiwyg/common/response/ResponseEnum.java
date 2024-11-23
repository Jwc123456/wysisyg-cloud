package com.wysiwyg.common.response;

/**
 * @author: wwcc
 * @date: 2024/11/15 18:55:24
 */
public enum ResponseEnum {


    // 正常请求响应码
    OK(1000, "成功"),


    // 异常请求响应码
    INTERNAL_SERVER_ERROR(-1001, "内部服务器错误！"),
    LOGIN_FAILED(-1002, "登录失败！"),
    UNAUTHORIZED(-1003, "未授权！"),
    FORBIDDEN(-1004, "禁止访问！"),
    NOT_FOUND(-1005, "资源未找到！"),
    METHOD_NOT_ALLOWED(-1006, "方法不允许！"),
    NOT_ACCEPTABLE(-1007, "不接受的媒体类型！"),



    // 其他未定义的状态码
    UNDEFINED(-9999, "未定义的状态码");

    private final int code;
    private final String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }



    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
