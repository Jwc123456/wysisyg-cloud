package com.wysiwyg.gateway.security.config;

import com.wysiwyg.gateway.security.filter.JwtTokenAuthenticationFilter;
import com.wysiwyg.gateway.security.filter.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


/**
 * @author wwcc
 */
@Configuration
@EnableWebFluxSecurity
public class ReactiveWebSecurityConfiguration {



    /**
     *
     * @param http
     * @param usernamePasswordAuthenticationFilter 用户名密码认证过滤器
     * @param jwtTokenAuthenticationFilter jwt鉴权过滤器
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
                                                            JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(usernamePasswordAuthenticationFilter, SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterAfter(jwtTokenAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();

    }


}
