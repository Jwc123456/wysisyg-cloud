package com.wysiwyg.gateway.security.filter;

import cn.hutool.json.JSONObject;
import com.wysiwyg.common.model.ContextUserInfo;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.security.converter.JwtAuthenticationConverter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationEntryPoint;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author: wwcc
 * @Date: 2020/8/17 下午12:53
 */

@Component
@Slf4j
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private final ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.anyExchange();

    private ServerAuthenticationFailureHandler authenticationFailureHandler;

    private JwtTokenGenerator jwtTokenGenerator;

    private ServerAuthenticationConverter authenticationConverter;


    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager,
                                   JwtTokenGenerator jwtTokenGenerator,
                                   JwtAuthenticationConverter jwtAuthenticationConverter,
                                    CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint) {
        super(authenticationManager);
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationConverter = jwtAuthenticationConverter;
        this.authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(customServerAuthenticationEntryPoint);

    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().value();
        if (path.contains(AuthConstant.LOGIN_URL)) {
            return chain.filter(exchange);
        }

        return this.requiresAuthenticationMatcher.matches(exchange)
                .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
                .flatMap((matchResult) -> this.authenticationConverter.convert(exchange))
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .flatMap((token) -> authenticate(exchange, chain, token))
                .onErrorResume(AuthenticationException.class, (ex) -> this.authenticationFailureHandler.onAuthenticationFailure(new WebFilterExchange(exchange, chain), ex));

    }




    private Mono<Void> authenticate(ServerWebExchange exchange, WebFilterChain chain, Authentication token) {
        return Mono.defer(()->Mono.just(exchange))
                .flatMap((authenticationManager) -> authenticate(token))
                .switchIfEmpty(Mono
                        .defer(() -> Mono.error(new IllegalStateException("No provider found for " + token.getClass()))))
                .flatMap(
                        (authentication) -> onAuthenticationSuccess(authentication, new WebFilterExchange(exchange, chain)))
                .doOnError(AuthenticationException.class,e->log.error(e.getMessage(),e));
    }

    private Mono<Authentication> authenticate(Authentication token) {
        String jwt = (String) token.getPrincipal();
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(jwt);
        ContextUserInfo contextUserInfo = jsonObject.toBean(ContextUserInfo.class);
        return  Mono.just(UsernamePasswordAuthenticationToken.authenticated(contextUserInfo, null,contextUserInfo.getAuthorities()));
    }




}