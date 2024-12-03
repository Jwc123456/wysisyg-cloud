package com.wysiwyg.gateway.security.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: wwcc
 * @date: 2024/11/14 20:39:09
 */
@Component
@Slf4j
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String BEARER_PREFIX = "Bearer ";


    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        log.error(exchange.getRequest().getURI().toString());
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(token -> token != null && token.startsWith(BEARER_PREFIX))
                .map(token -> token.substring(BEARER_PREFIX.length()))
                .map(jwt -> new UsernamePasswordAuthenticationToken(null, jwt));
    }

}

