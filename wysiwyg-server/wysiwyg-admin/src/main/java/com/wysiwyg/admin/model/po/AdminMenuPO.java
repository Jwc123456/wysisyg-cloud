package com.wysiwyg.admin.model.po;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wysiwyg.common.model.po.BasePO;



/**
 * @author wwcc
 * @date 2025-02-12 19:23:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wysiwyg_admin_menu")
public class AdminMenuPO implements BasePO,Serializable{
    private static final long serialVersionUID = -48227029547942824L;
    /**
     * 菜单ID
     */
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

