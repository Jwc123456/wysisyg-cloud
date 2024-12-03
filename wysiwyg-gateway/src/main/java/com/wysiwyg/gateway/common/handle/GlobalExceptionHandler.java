package com.wysiwyg.gateway.common.handle;

import com.wysiwyg.common.response.ResponseEnum;
import com.wysiwyg.common.response.ServerResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ServerResponseEntity handleException(Exception ex) {
        log.error(ex.getMessage(),ex);
        // 返回统一的错误响应格式
        return ServerResponseEntity.fail(ResponseEnum.INTERNAL_SERVER_ERROR);
    }




}
