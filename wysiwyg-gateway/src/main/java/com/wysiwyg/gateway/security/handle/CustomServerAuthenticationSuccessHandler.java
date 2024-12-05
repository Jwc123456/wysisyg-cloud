package com.wysiwyg.gateway.security.handle;

import com.wysiwyg.common.model.po.AdminUserPO;
import com.wysiwyg.common.web.response.ServerResponseEntity;
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
        AdminUserPO adminUserPo = (AdminUserPO) authentication.getPrincipal();

        Map<String, String> additional = new HashMap<>();
        additional.put("name", adminUserPo.getUsername());
        additional.put("mobile", adminUserPo.getMobile());
        additional.put("mail", adminUserPo.getMail());

        JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair(adminUserPo.getUserId(), adminUserPo.getRoles(), additional);

        return WebExchangeUtils.writeJsonResponse(webFilterExchange.getExchange(), HttpStatus.OK, ServerResponseEntity.success(jwtTokenPair));

    }
}
