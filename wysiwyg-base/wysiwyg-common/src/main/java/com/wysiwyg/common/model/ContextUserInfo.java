package com.wysiwyg.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("wysiwyg_admin_user")
public class ContextUserInfo implements UserDetails {

    private String userId;

    private String mobile;

    private String verificationCode;

    private String host;

    private Set<String> roles;

    private String username;

    private String mail;

    private String password;

    private Integer status;



    public ContextUserInfo(String mobile, String verificationCode, String host) {
        this.mobile = mobile;
        this.verificationCode = verificationCode;
        this.host = host;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(roles)
                .orElse( Collections.emptySet())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
