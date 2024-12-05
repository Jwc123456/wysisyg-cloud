package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.common.model.ContextUserInfo;
import com.wysiwyg.gateway.security.converter.JwtAuthenticationConverter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationEntryPoint;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author wwcc
 * @date 2020/8/17 下午12:53
 * @description 不能注入容器中，否则会被注入到默认的DefaultWebFilterChain过滤器链，导致过滤器执行多次
 */
@Slf4j
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager,
                                   JwtAuthenticationConverter jwtAuthenticationConverter,
                                   CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint) {
        super(jwtAuthenticationManager);
        this.setServerAuthenticationConverter(jwtAuthenticationConverter);
        this.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(customServerAuthenticationEntryPoint));

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            // 如果没有 token，直接跳过认证
            return chain.filter(exchange);
        }

        // 继续认证流程
        return super.filter(exchange, chain);
    }


    @Component
    public static class JwtAuthenticationManager implements ReactiveAuthenticationManager {

        private final JwtTokenGenerator jwtTokenGenerator;

        public JwtAuthenticationManager(JwtTokenGenerator jwtTokenGenerator) {
            this.jwtTokenGenerator = jwtTokenGenerator;
        }

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            return Mono.justOrEmpty((String) authentication.getCredentials())
                    .flatMap(token -> Mono.just(jwtTokenGenerator.decodeAndVerify(token)))
                    .filter(Objects::nonNull)
                    .map(jsonObject -> jsonObject.toBean(ContextUserInfo.class))
                    .map(contextUserInfo -> UsernamePasswordAuthenticationToken.authenticated(contextUserInfo, null, contextUserInfo.getAuthorities()));
        }
    }





}