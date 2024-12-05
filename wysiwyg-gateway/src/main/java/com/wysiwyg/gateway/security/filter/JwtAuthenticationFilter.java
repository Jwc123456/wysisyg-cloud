package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.common.constant.AuthConstant;
import com.wysiwyg.common.model.po.AdminUserPO;
import com.wysiwyg.common.web.response.ResponseEnum;
import com.wysiwyg.gateway.security.converter.JwtAuthenticationConverter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;

/**
 * @author wwcc
 * @date 2020/8/17 下午12:53
 * @description 不能注入容器中，否则会被注入到默认的DefaultWebFilterChain过滤器链，导致过滤器执行多次
 */
@Slf4j
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager,
                                   JwtAuthenticationConverter jwtAuthenticationConverter,
                                   CustomServerAuthenticationFailureHandler customServerAuthenticationFailureHandler) {
        super(jwtAuthenticationManager);
        this.setRequiresAuthenticationMatcher(exchange ->
                                    Mono.justOrEmpty(exchange.getRequest().getHeaders())
                                        .mapNotNull((headers) -> headers.getFirst(HttpHeaders.AUTHORIZATION))
                                        .filter(Objects::nonNull)
                                        .flatMap((token) -> null != token  && token.startsWith(AuthConstant.BEARER_PREFIX) ? MatchResult.match() : MatchResult.notMatch())
                                        .switchIfEmpty(MatchResult.notMatch()));
        this.setServerAuthenticationConverter(jwtAuthenticationConverter);
        this.setAuthenticationFailureHandler(customServerAuthenticationFailureHandler);

    }


    @Component
    public static class JwtAuthenticationManager implements ReactiveAuthenticationManager {

        private final JwtTokenGenerator jwtTokenGenerator;

        public JwtAuthenticationManager(JwtTokenGenerator jwtTokenGenerator) {
            this.jwtTokenGenerator = jwtTokenGenerator;
        }

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            return Mono.justOrEmpty((String) authentication.getCredentials())
                    .flatMap(token -> Mono.just(jwtTokenGenerator.decodeAndVerify(token)))
                    .filter(Objects::nonNull)
                    .map(jsonObject -> jsonObject.toBean(AdminUserPO.class))
                    .onErrorMap(Exception.class, e -> new BadCredentialsException(ResponseEnum.AUTHENTICATION_FAILED.getMsg(),e))
                    .map(contextUserInfo -> UsernamePasswordAuthenticationToken.authenticated(contextUserInfo, null, contextUserInfo.getAuthorities()));
        }
    }





}