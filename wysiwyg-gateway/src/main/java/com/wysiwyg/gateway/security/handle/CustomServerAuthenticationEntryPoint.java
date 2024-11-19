package com.wysiwyg.gateway.security.handle;

import com.wysiwyg.gateway.util.WebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wwcc
 * @description 自定义未授权处理
 */
@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
      return WebExchangeUtils.writeErrorResponse(exchange, HttpStatus.FORBIDDEN,ex.getMessage());
    }
}
