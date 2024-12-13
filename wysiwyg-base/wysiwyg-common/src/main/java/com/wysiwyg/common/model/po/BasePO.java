package com.wysiwyg.common.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;

/**
 * @author wwcc
 * @date 2024/12/12 20:28:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePO {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateBy;
    private String createBy;
    private Integer isDelete;
}
