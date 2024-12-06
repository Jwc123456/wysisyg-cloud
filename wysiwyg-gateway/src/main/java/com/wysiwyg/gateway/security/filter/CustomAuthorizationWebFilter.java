package com.wysiwyg.gateway.security.filter;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author wwcc
 * @date 2024/11/26 21:25:05
 */
@Slf4j
public class CustomAuthorizationWebFilter extends AuthorizationWebFilter {

    public CustomAuthorizationWebFilter(ReactiveAuthorizationManager<ServerWebExchange> authorizationManager) {
        super(authorizationManager);
    }


    @Component
    public static class PathBasedReactiveAuthorizationManager implements ReactiveAuthorizationManager<ServerWebExchange> {

        @Override
        public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, ServerWebExchange exchange) {
            String path = exchange.getRequest().getURI().getPath();
            // 获取path所需的权限
            List<GrantedAuthority> authorities = getPathAuthorities(path);
            if(CollUtil.isEmpty(authorities)){
                return Mono.just(new AuthorityAuthorizationDecision(true, authorities));
            }

            return authentication.filter(Authentication::isAuthenticated)
                    .flatMapIterable(Authentication::getAuthorities)
                    .map(GrantedAuthority::getAuthority)
                    .any((grantedAuthority) -> authorities.stream().anyMatch((authority) -> authority.getAuthority().equals(grantedAuthority)))
                    .map((granted) -> ((AuthorizationDecision) new AuthorityAuthorizationDecision(granted, authorities)))
                    .defaultIfEmpty(new AuthorityAuthorizationDecision(false, authorities));
        }

        private List<GrantedAuthority> getPathAuthorities(String path) {
            // TODO: 根据path获取所需权限
            return Collections.emptyList();
        }
    }




}
