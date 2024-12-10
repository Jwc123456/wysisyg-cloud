package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.gateway.service.SecurityUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


        @Autowired
        private SecurityUserDetailService userDetailService;

        @Override
        public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, ServerWebExchange exchange) {
            return userDetailService
                    .findPathAuthorities(exchange.getRequest().getURI().getPath()) // 获取路径权限 Flux
                    .map(GrantedAuthority::getAuthority)
                    .collectList() // 收集为 List
                    .flatMap(pathAuthorities -> {
                        if (pathAuthorities.isEmpty()) {
                            // 若路径权限为空，直接授权通过
                            return Mono.just(new AuthorizationDecision(true));
                        }

                        // 检查用户是否认证，并逐一对比权限
                        return authentication.filter(Authentication::isAuthenticated)
                                .flatMapMany(auth -> Flux.fromIterable(auth.getAuthorities()))
                                .map(GrantedAuthority::getAuthority)
                                .any(pathAuthorities::contains)
                                .map(AuthorizationDecision::new);
                    })
                    .defaultIfEmpty(new AuthorizationDecision(false))
                    .doOnError(err-> log.error(err.getMessage(),err));
        }

    }

}
