package com.wysiwyg.admin.model.dto.system;

import com.wysiwyg.admin.model.po.AdminMenuPO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wwcc
 * @date 2024/12/12 16:26:59
 */
@AutoMapper(target = AdminMenuPO.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenuDTO {

    private Long id;
}
