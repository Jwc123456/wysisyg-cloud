package com.wysiwyg.gateway.security.handle;

import com.wysiwyg.gateway.util.WebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author wwcc
 * @description 自定义认证失败处理
 */
@Component
public class CustomServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return WebExchangeUtils.writeErrorResponse(webFilterExchange.getExchange(), HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}
