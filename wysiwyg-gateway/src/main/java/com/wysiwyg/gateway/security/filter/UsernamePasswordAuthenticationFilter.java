package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.common.entity.ContextUserInfo;
import com.wysiwyg.common.response.ResponseEnum;
import com.wysiwyg.gateway.constant.AuthConstant;
import com.wysiwyg.gateway.security.converter.CustomAuthenticationConverter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationSuccessHandler;
import com.wysiwyg.gateway.service.SecurityUserDetailService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        private final SecurityUserDetailService securityUserDetailService;

        public UsernamePasswordAuthenticationManager(SecurityUserDetailService securityUserDetailService) {
            this.securityUserDetailService = securityUserDetailService;
        }



        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            ContextUserInfo contextUserInfo = (ContextUserInfo) authentication.getPrincipal();
            String mobile = contextUserInfo.getMobile();
            String presentedPassword = (String) authentication.getCredentials();
            return retrieveUser(mobile)
                    .doOnNext(this::preAuthenticationChecks)
                    .publishOn(this.scheduler)
                    .filter((userDetails) -> this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()))
                    .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException(ResponseEnum.LOGIN_FAILED.getMsg()))))
                    .map(this::createUsernamePasswordAuthenticationToken);
        }




        private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserDetails userDetails) {
            ContextUserInfo contextUserInfo = (ContextUserInfo) userDetails;
            contextUserInfo.setPassword(null);
            return UsernamePasswordAuthenticationToken.authenticated(contextUserInfo, null,
                    userDetails.getAuthorities());
        }


        @Override
        protected Mono<UserDetails> retrieveUser(String mobile) {
            return securityUserDetailService.findByUsername(mobile);
        }


        private void preAuthenticationChecks(UserDetails user) {
            if (!((ContextUserInfo)user).getStatus().equals(1)) {
                throw new LockedException(ResponseEnum.LOCALED_USER.getMsg());
            }

        }
    }


}
