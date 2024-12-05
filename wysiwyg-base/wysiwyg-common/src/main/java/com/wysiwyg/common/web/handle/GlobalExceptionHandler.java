package com.wysiwyg.common.web.handle;

import com.wysiwyg.common.web.response.ResponseEnum;
import com.wysiwyg.common.web.response.ServerResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author wwcc
 * @date 2024/11/14 20:52:48
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ServerResponseEntity<ResponseEnum> handleException(Throwable ex) {
        log.error(ex.getMessage(),ex);
        // 返回统一的错误响应格式
        return ServerResponseEntity.fail(ResponseEnum.INTERNAL_SERVER_ERROR);
    }




}
