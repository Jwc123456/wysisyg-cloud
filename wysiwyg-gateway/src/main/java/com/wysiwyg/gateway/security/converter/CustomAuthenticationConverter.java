package com.wysiwyg.gateway.security.converter;

import com.wysiwyg.common.entity.ContextUserInfo;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.util.WebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author: wwcc
 * @date: 2024/11/14 20:39:09
 */
@Component
public class CustomAuthenticationConverter extends ServerFormLoginAuthenticationConverter {


    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String host = Objects.requireNonNull(headers.getHost()).getHostName();

        return WebExchangeUtils.parseRequestBodyToJson(exchange)
                .flatMap(jsonNode -> {

                    String phoneNo = jsonNode.has(AuthConstant.PHONE_NO_PARAMETER) ? jsonNode.get(AuthConstant.PHONE_NO_PARAMETER).asText() : null;
                    String password = jsonNode.has(AuthConstant.PASSWORD_PARAMETER) ? jsonNode.get(AuthConstant.PASSWORD_PARAMETER).asText() : null;
                    String verificationCode = jsonNode.has(AuthConstant.VERIFICATION_PARAMETER) ? jsonNode.get(AuthConstant.VERIFICATION_PARAMETER).asText() : null;

                    // 创建Authentication对象
                    if (phoneNo != null && password != null) {
                        ContextUserInfo contextUserInfo = new ContextUserInfo(phoneNo, verificationCode, host);
                        return Mono.just(new UsernamePasswordAuthenticationToken(contextUserInfo, password));
                    } else {
                        return Mono.error(new BadCredentialsException("请输入用户名或密码!"));
                    }

                });

    }

}

