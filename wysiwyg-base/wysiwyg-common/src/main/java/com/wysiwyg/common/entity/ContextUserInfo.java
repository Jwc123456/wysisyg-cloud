package com.wysiwyg.common.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextUserInfo implements UserDetails {

    private String userId;

    private String phoneNo;

    private String verificationCode;

    private String host;

    private Set<String> roles;

    private String username;

    private String email;

    private String password;



    public ContextUserInfo(String phoneNo, String verificationCode, String host) {
        this.phoneNo = phoneNo;
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
