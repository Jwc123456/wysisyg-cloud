package com.wysiwyg.gateway.security.handle;

import com.wysiwyg.common.entity.ContextUserInfo;
import com.wysiwyg.common.response.ServerResponseEntity;
import com.wysiwyg.gateway.security.jwt.JwtTokenGenerator;
import com.wysiwyg.gateway.security.jwt.JwtTokenPair;
import com.wysiwyg.gateway.util.WebExchangeUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wwcc
 * @description  自定义认证成功处理器
 */
@Component
public class CustomServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {


    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Override
    @SneakyThrows
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ContextUserInfo contextUserInfo = (ContextUserInfo) authentication.getPrincipal();

        Map<String, String> additional = new HashMap<>();
        additional.put("name",contextUserInfo.getUsername());
        additional.put("phoneNo", contextUserInfo.getPhoneNo());
        additional.put("email",contextUserInfo.getEmail());

        JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair(contextUserInfo.getUserId(), contextUserInfo.getRoles(), additional);

        return WebExchangeUtils.writeJsonResponse(webFilterExchange.getExchange(), HttpStatus.OK, ServerResponseEntity.success(jwtTokenPair));

    }
}
