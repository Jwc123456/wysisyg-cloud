package com.wysiwyg.gateway.security.converter;

import com.wysiwyg.common.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wwcc
 * @date 2024/11/14 20:39:09
 */
@Component
@Slf4j
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {



    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(token -> token != null && token.startsWith(AuthConstant.BEARER_PREFIX))
                .map(token -> token.substring(AuthConstant.BEARER_PREFIX.length()))
                .map(jwt -> new UsernamePasswordAuthenticationToken(null, jwt));
    }

}

