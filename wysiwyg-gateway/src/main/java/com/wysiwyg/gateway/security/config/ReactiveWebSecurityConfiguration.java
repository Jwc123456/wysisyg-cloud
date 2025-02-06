package com.wysiwyg.gateway.security.config;


import com.wysiwyg.gateway.security.converter.JwtAuthenticationConverter;
import com.wysiwyg.gateway.security.converter.UsernamePasswordAuthenticationConverter;
import com.wysiwyg.gateway.security.filter.CustomAuthorizationWebFilter;
import com.wysiwyg.gateway.security.filter.JwtAuthenticationFilter;
import com.wysiwyg.gateway.security.filter.UserInfoEnrichmentFilter;
import com.wysiwyg.gateway.security.filter.UsernamePasswordAuthenticationFilter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

                                                            AbstractUserDetailsReactiveAuthenticationManager usernamePasswordAuthenticationManager,
                                                            UsernamePasswordAuthenticationConverter customAuthenticationConverter,

                                                            CustomServerAuthenticationSuccessHandler customServerAuthenticationSuccessHandler,
                                                            CustomServerAuthenticationFailureHandler customServerAuthenticationFailureHandler,

                                                            CustomAuthorizationWebFilter.PathBasedReactiveAuthorizationManager pathBasedReactiveAuthorizationManager

    ) {

        // 不可以将过滤器注入到容器中，否则会默认注册到DefaultWebFilterChain，和security过滤链重复
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtAuthenticationManager, jwtAuthenticationConverter, customServerAuthenticationFailureHandler);
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(usernamePasswordAuthenticationManager, customServerAuthenticationSuccessHandler, customServerAuthenticationFailureHandler, customAuthenticationConverter);
        CustomAuthorizationWebFilter customAuthorizationWebFilter = new CustomAuthorizationWebFilter(pathBasedReactiveAuthorizationManager);
        // 将用户信息填充到请求头中，转发给各个微服务
        UserInfoEnrichmentFilter userInfoEnrichmentFilter = new UserInfoEnrichmentFilter();

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(usernamePasswordAuthenticationFilter, SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(customAuthorizationWebFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .addFilterAt(userInfoEnrichmentFilter, SecurityWebFiltersOrder.LAST);

        return http.build();
    }




}
