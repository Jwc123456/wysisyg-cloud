package com.wysiwyg.gateway.security.filter;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import com.wysiwyg.gateway.util.WebExchangeUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: wwcc
 * @Date: 2020/8/17 下午12:53
 */

@Component
@Slf4j
public class JwtTokenAuthenticationFilter extends AuthenticationWebFilter {

    private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();


    private JwtTokenGenerator jwtTokenGenerator;


    public JwtTokenAuthenticationFilter(ReactiveAuthenticationManager authenticationManager,
                                        JwtTokenGenerator jwtTokenGenerator) {
        super(authenticationManager);
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getPath().value();
        if (path.contains(AuthConstant.LOGIN_URL)) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return WebExchangeUtils.writeErrorResponse(exchange, HttpStatus.NOT_ACCEPTABLE, "没有携带token");
        }
        String token = resolveToken(request);
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(token);


//        return this.requiresAuthenticationMatcher.matches(exchange)
//                .filter((matchResult) -> matchResult.isMatch())
//                .flatMap((matchResult) -> this.authenticationConverter.convert(exchange))
//                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
//                .flatMap((token) -> authenticate(exchange, chain, token))
//                .onErrorResume(AuthenticationException.class, (ex) -> this.authenticationFailureHandler
//                .onAuthenticationFailure(new WebFilterExchange(exchange, chain), ex));

        return Mono.empty();
    }
 

    private String resolveToken(ServerHttpRequest request) {
        return Objects.requireNonNull(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).replaceFirst("Bearer ", "");
    }
}