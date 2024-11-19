package com.wysiwyg.gateway.security.crypto;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return (String) rawPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || StrUtil.isEmpty(encodedPassword)) {
            throw new BadCredentialsException("密码错误!");
        }
        // TODO 自定义密码校验
//        BCrypt.checkpw(rawPassword.toString(), encodedPassword)
        return Objects.equals(rawPassword,encodedPassword);
    }
}
