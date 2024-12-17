package com.wysiwyg.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author wwcc
 * @date 2024/12/17 18:38:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;

    private String mobile;

    private Set<String> roles;

    private String username;

    private String mail;

    public UserDTO(String userId, String mobile, Set<String> roles) {
        this.userId = userId;
        this.mobile = mobile;
        this.roles = roles;
    }
}
