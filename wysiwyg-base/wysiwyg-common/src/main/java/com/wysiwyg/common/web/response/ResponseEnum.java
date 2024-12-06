package com.wysiwyg.common.web.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;



/**
 * @author wwcc
 * @date 2024/11/15 18:55:24
 */
@Getter
public enum ResponseEnum {


    // 正常请求响应码
    OK(10000, "成功", HttpStatus.OK),

    // 通用异常
    INTERNAL_SERVER_ERROR(-10001, "未知错误！",HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_ARGUMENT(-10002, "非法参数！",HttpStatus.BAD_REQUEST),
    NOT_FOUND(-10003, "资源未找到！",HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(-10004, "方法不允许！",HttpStatus.METHOD_NOT_ALLOWED),
    NOT_ACCEPTABLE(-10005, "不接受的媒体类型！",HttpStatus.ACCEPTED),

    // 用户相关异常
    USER_NOT_FOUND(-20001, "用户不存在！",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(-20002, "未授权！",HttpStatus.UNAUTHORIZED),
    FORBIDDEN(-20003, "禁止访问！",HttpStatus.FORBIDDEN),
    LOGIN_FAILED(-20004, "登录失败！",HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED(-20005, "认证失败！",HttpStatus.UNAUTHORIZED),
    LOCALED_USER(-20006, "用户已锁定！",HttpStatus.UNAUTHORIZED);

    
    
    

    private final int code;
    private final String msg;
    private final HttpStatus httpStatus;

    ResponseEnum(int code, String msg,HttpStatus httpStatus) {
        this.code = code;
        this.msg = msg;
        this.httpStatus = httpStatus;
    }


}
