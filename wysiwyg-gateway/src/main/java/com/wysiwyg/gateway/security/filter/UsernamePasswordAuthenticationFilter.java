package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.common.entity.ContextUserInfo;
import com.wysiwyg.common.response.ResponseEnum;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.security.converter.CustomAuthenticationConverter;
import com.wysiwyg.gateway.security.crypto.CustomPasswordEncoder;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationSuccessHandler;
import com.wysiwyg.gateway.service.SecurityUserDetailService;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author: wwcc
 * @date: 2024/11/16 20:29:06
 */
@Component
public class UsernamePasswordAuthenticationFilter extends AuthenticationWebFilter {


    /**
     * @param usernamePasswordAuthenticationManager    用户信息认证管理器
     * @param customServerAuthenticationSuccessHandler 认证成功处理逻辑
     * @param customServerAuthenticationFailureHandler 认证失败处理逻辑
     * @param customAuthenticationConverter            认证信息转换器
     * @return
     */
    public UsernamePasswordAuthenticationFilter(AbstractUserDetailsReactiveAuthenticationManager usernamePasswordAuthenticationManager,
                                                CustomServerAuthenticationSuccessHandler customServerAuthenticationSuccessHandler,
                                                CustomServerAuthenticationFailureHandler customServerAuthenticationFailureHandler,
                                                CustomAuthenticationConverter customAuthenticationConverter) {
        super(usernamePasswordAuthenticationManager);
        this.setServerAuthenticationConverter(customAuthenticationConverter);
        this.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(AuthConstant.LOGIN_URL));
        this.setAuthenticationFailureHandler(customServerAuthenticationFailureHandler);
        this.setAuthenticationSuccessHandler(customServerAuthenticationSuccessHandler);
    }

    @Component
    public static class UsernamePasswordAuthenticationManager extends AbstractUserDetailsReactiveAuthenticationManager {
        private final Scheduler scheduler = Schedulers.boundedElastic();
        private final CustomPasswordEncoder customPasswordEncoder;

        private final SecurityUserDetailService securityUserDetailService;

        public UsernamePasswordAuthenticationManager(CustomPasswordEncoder customPasswordEncoder, SecurityUserDetailService securityUserDetailService) {
            this.customPasswordEncoder = customPasswordEncoder;
            this.securityUserDetailService = securityUserDetailService;
        }

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            ContextUserInfo contextUserInfo = (ContextUserInfo) authentication.getPrincipal();
            String phoneNo = contextUserInfo.getPhoneNo();
            String presentedPassword = (String) authentication.getCredentials();
            return retrieveUser(phoneNo)
                    // TODO 校验用户过期 锁定等操作
                    // .doOnNext(super.preAuthenticationChecks::check)
                    .publishOn(this.scheduler)
                    .filter((userDetails) -> customPasswordEncoder.matches(presentedPassword, userDetails.getPassword()))
                    .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException(ResponseEnum.LOGIN_FAILED.getMessage()))))
                    .map(this::createUsernamePasswordAuthenticationToken);
        }


        private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserDetails userDetails) {
            ContextUserInfo contextUserInfo = (ContextUserInfo) userDetails;
            contextUserInfo.setPassword(null);
            return UsernamePasswordAuthenticationToken.authenticated(contextUserInfo, null,
                    userDetails.getAuthorities());
        }


        @Override
        protected Mono<UserDetails> retrieveUser(String phoneNo) {
            return securityUserDetailService.findByUsername(phoneNo);

        }
    }


}
