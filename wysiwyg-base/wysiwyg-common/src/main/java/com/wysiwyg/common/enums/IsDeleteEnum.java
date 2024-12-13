package com.wysiwyg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wwcc
 */
@Getter
@AllArgsConstructor
public enum IsDeleteEnum {

    NO(0, "未删除、记录生效"),
    YES(1, "已逻辑删除、记录无效");

    private final Integer code;
    private final String message;


}