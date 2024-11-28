package com.wysiwyg.gateway.security.converter;

import com.wysiwyg.common.model.ContextUserInfo;
import com.wysiwyg.common.response.ResponseEnum;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.util.WebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author: wwcc
 * @date: 2024/11/14 20:39:09
 */
@Component
public class JwtAuthenticationConverter extends ServerFormLoginAuthenticationConverter {

    private static final String BEARER_PREFIX = "Bearer ";


    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith(BEARER_PREFIX)) {
            return Mono.empty();
        }
        String jwt = token.substring(BEARER_PREFIX.length());
        return Mono.just(new UsernamePasswordAuthenticationToken(jwt, null));

    }

}

