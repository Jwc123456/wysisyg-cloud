package com.wysiwyg.gateway.model.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wwcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("wysiwyg_admin_user")
public class AdminUserPO implements UserDetails {

    private String userId;

    private String mobile;

    private String verificationCode;

    private String host;

    private Set<String> roles;

    private String username;

    private String mail;

    private String password;

    private Integer status;



    public AdminUserPO(String mobile, String verificationCode, String host) {
        this.mobile = mobile;
        this.verificationCode = verificationCode;
        this.host = host;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(roles)
                .orElse(Collections.emptySet())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
