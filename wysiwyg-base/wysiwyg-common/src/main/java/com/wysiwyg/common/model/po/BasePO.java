package com.wysiwyg.common.model.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wwcc
 * @date 2024/12/12 20:28:54
 */
public interface BasePO {
    LocalDateTime getCreateTime();
    void setCreateTime(LocalDateTime createTime);

    LocalDateTime getUpdateTime();
    void setUpdateTime(LocalDateTime updateTime);

    String getUpdateBy();
    void setUpdateBy(String updateBy);

    String getCreateBy();
    void setCreateBy(String createBy);

    Integer getIsDelete();
    void setIsDelete(Integer isDelete);
}
