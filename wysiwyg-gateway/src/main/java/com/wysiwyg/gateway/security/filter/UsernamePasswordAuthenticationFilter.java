package com.wysiwyg.gateway.security.filter;

import com.wysiwyg.common.model.po.AdminUserPO;
import com.wysiwyg.common.web.response.ResponseEnum;
import com.wysiwyg.common.constant.AuthConstant;
import com.wysiwyg.gateway.security.converter.UsernamePasswordAuthenticationConverter;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationFailureHandler;
import com.wysiwyg.gateway.security.handle.CustomServerAuthenticationSuccessHandler;
import com.wysiwyg.gateway.service.SecurityUserDetailService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author wwcc
 * @date 2024/11/16 20:29:06
 * @description 自定义用户名密码认证过滤器,不能注入容器中，否则会被注入到默认的DefaultWebFilterChain过滤器链，导致过滤器执行多次
 */
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
                                                UsernamePasswordAuthenticationConverter customAuthenticationConverter) {
        super(usernamePasswordAuthenticationManager);
        this.setServerAuthenticationConverter(customAuthenticationConverter);
        this.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(AuthConstant.LOGIN_URL));
        this.setAuthenticationFailureHandler(customServerAuthenticationFailureHandler);
        this.setAuthenticationSuccessHandler(customServerAuthenticationSuccessHandler);
    }

    @Component
    @Primary
    public static class UsernamePasswordAuthenticationManager extends AbstractUserDetailsReactiveAuthenticationManager {
        private final Scheduler scheduler = Schedulers.boundedElastic();

        private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        private final SecurityUserDetailService securityUserDetailService;

        public UsernamePasswordAuthenticationManager(SecurityUserDetailService securityUserDetailService) {
            this.securityUserDetailService = securityUserDetailService;
        }



        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            AdminUserPO adminUserPo = (AdminUserPO) authentication.getPrincipal();
            String mobile = adminUserPo.getMobile();
            String presentedPassword = (String) authentication.getCredentials();
            return retrieveUser(mobile)
                    .doOnNext(this::preAuthenticationChecks)
                    .publishOn(this.scheduler)
                    .filter((userDetails) -> this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()))
                    .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException(ResponseEnum.LOGIN_FAILED.getMsg()))))
                    .map(this::createUsernamePasswordAuthenticationToken);
        }




        private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserDetails userDetails) {
            AdminUserPO adminUserPo = (AdminUserPO) userDetails;
            adminUserPo.setPassword(null);
            return UsernamePasswordAuthenticationToken.authenticated(adminUserPo, null,userDetails.getAuthorities());
        }


        @Override
        protected Mono<UserDetails> retrieveUser(String mobile) {
            return securityUserDetailService.findByUsername(mobile);
        }


        private void preAuthenticationChecks(UserDetails user) {
            if (!((AdminUserPO)user).getStatus().equals(1)) {
                throw new LockedException(ResponseEnum.LOCALED_USER.getMsg());
            }

        }
    }


}
