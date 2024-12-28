package com.wysiwyg.admin.model.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wwcc
 * @date 2024/12/11 08:39:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenuVO {

    private Long id;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 是否是超链接
     */
    private Integer isLink;
    /**
     * 代码
     */
    private String code;
    /**
     * URL
     */
    private String path;
    /**
     * 图标
     */
    private String icon;
    /**
     * 顺序
     */
    private Integer no;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 是否删除：0-否，1-是
     */
    private Integer isDelete;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改者
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
