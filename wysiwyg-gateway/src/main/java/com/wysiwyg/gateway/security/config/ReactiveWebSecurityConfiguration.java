package com.wysiwyg.gateway.security.config;

//import com.wysiwyg.gateway.security.filter.CustomAuthorizationWebFilter;

import com.wysiwyg.gateway.security.converter.JwtAuthenticationConverter;
import com.wysiwyg.gateway.security.converter.UsernamePasswordAuthenticationConverter;
import com.wysiwyg.gateway.security.filter.JwtAuthenticationFilter;
import com.wysiwyg.gateway.security.filter.UsernamePasswordAuthenticationFilter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationEntryPoint;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
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
     * @param jwtAuthenticationManager jwt认证管理器
     * @param jwtAuthenticationConverter jwt认证转换器
     *
     * @param usernamePasswordAuthenticationManager 用户名密码认证管理器
     * @param customServerAuthenticationSuccessHandler 自定义认证成功处理
     * @param customServerAuthenticationFailureHandler 自定义认证失败处理
     * @param customAuthenticationConverter 用户名密码认证转换器
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,

                                                            JwtAuthenticationFilter.JwtAuthenticationManager jwtAuthenticationManager,
                                                            JwtAuthenticationConverter jwtAuthenticationConverter,
//                                                            CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint,

                                                            AbstractUserDetailsReactiveAuthenticationManager usernamePasswordAuthenticationManager,
                                                            UsernamePasswordAuthenticationConverter customAuthenticationConverter,
                                                            CustomServerAuthenticationSuccessHandler customServerAuthenticationSuccessHandler,
                                                            CustomServerAuthenticationFailureHandler customServerAuthenticationFailureHandler


    ) {

        // 不可以将过滤器注入到容器中，否则会默认注册到DefaultWebFilterChain，和security过滤链重复
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtAuthenticationManager, jwtAuthenticationConverter, customServerAuthenticationFailureHandler);
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(usernamePasswordAuthenticationManager, customServerAuthenticationSuccessHandler, customServerAuthenticationFailureHandler, customAuthenticationConverter);

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
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }




}
