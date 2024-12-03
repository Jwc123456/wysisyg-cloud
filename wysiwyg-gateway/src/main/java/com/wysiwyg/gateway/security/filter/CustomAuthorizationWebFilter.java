//package com.wysiwyg.gateway.security.filter;
//
//import com.wysiwyg.common.model.ContextUserInfo;
//import com.wysiwyg.common.response.ResponseEnum;
//import com.wysiwyg.gateway.util.WebExchangeUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.log.LogMessage;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.Collection;
//
///**
// * @author: wwcc
// * @date: 2024/11/26 21:25:05
// */
//@Slf4j
//@Component
//public class CustomAuthorizationWebFilter extends AuthorizationWebFilter {
//
//    public CustomAuthorizationWebFilter() {
//        super(null);
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//
//        return ReactiveSecurityContextHolder.getContext()
//                .filter(c -> c.getAuthentication() != null)
//                .map(SecurityContext::getAuthentication)
//                .as((authentication) -> {
//                    return authentication.filter(a->null != a.getAuthorities())
//                            .switchIfEmpty(Mono.error(new AccessDeniedException("Unauthorized")));
//                })
//                .doOnSuccess((it) -> log.debug("Authorization successful"))
//                .switchIfEmpty(chain.filter(exchange))
//                .doOnError(AccessDeniedException.class,a->WebExchangeUtils.writeJsonResponse(exchange,HttpStatus.UNAUTHORIZED, ResponseEnum.FORBIDDEN.getMsg())).then()
//    }
//
//
//
//}
