package com.wysiwyg.gateway.security.filter;

import com.alibaba.fastjson2.JSON;
import com.wysiwyg.common.constant.AuthConstant;
import com.wysiwyg.gateway.model.po.AdminUserPO;
import io.micrometer.common.lang.NonNull;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author wwcc
 * @description 将解析完的上下文用户信息转发给各个业务服务
 */
public class UserInfoEnrichmentFilter implements org.springframework.web.server.WebFilter {

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .flatMap(authentication -> {
                    if (authentication.getPrincipal() instanceof AdminUserPO adminUser) {
                        return enrichRequestWithUserInfo(exchange, chain, adminUser);
                    } else {
                        // If the principal is not an AdminUserPO, proceed without modifying the request.
                        return chain.filter(exchange);
                    }
                });
    }

    private Mono<Void> enrichRequestWithUserInfo(ServerWebExchange exchange, WebFilterChain chain, AdminUserPO adminUser) {
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(AuthConstant.HEAD_USER_NAME, adminUser.getUsername())
                .header(AuthConstant.HEAD_USER_ID, adminUser.getUserId())
                .header(AuthConstant.HEAD_USER_NAME_ROLES, JSON.toJSONString(adminUser.getRoles()))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
}
