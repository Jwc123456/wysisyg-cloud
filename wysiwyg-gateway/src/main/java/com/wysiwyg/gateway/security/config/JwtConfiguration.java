package com.wysiwyg.gateway.security.config;


import com.wysiwyg.gateway.security.jwt.JwtProperties;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt.config", name = "enabled")
@Configuration
public class JwtConfiguration {


    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtProperties jwtProperties) {
        return new JwtTokenGenerator(jwtProperties);
    }

}
